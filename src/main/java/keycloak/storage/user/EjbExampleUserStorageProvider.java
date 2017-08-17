package keycloak.storage.user;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.models.cache.OnUserCache;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import keycloak.bean.logUser;
import org.keycloak.common.util.MultivaluedHashMap;
import static org.keycloak.examples.storage.HTTPUtil.Util.doGet;
import org.keycloak.models.ClientModel;
import org.keycloak.models.FederatedIdentityModel;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserConsentModel;
import org.keycloak.storage.federated.UserFederatedStorageProvider;

/**
 * @version 1
 */
@Stateful
@Local(EjbExampleUserStorageProvider.class)
public class EjbExampleUserStorageProvider implements UserStorageProvider,
        UserLookupProvider,
        UserRegistrationProvider,
        UserQueryProvider,
        CredentialInputUpdater,
        CredentialInputValidator,
        OnUserCache,
        UserFederatedStorageProvider {

    private static final Logger log = Logger.getLogger(EjbExampleUserStorageProvider.class);
    public static final String PASSWORD_CACHE_KEY = UserAdapter.class.getName() + ".password";

    @PersistenceContext(unitName = "user-storage-jpa-example")
    protected EntityManager em;
    protected ComponentModel model;
    protected KeycloakSession session;

    /**
     *
     * @param model
     */
    public void setModel(ComponentModel model) {
        this.model = model;
    }

    /**
     *
     * @param session
     */
    public void setSession(KeycloakSession session) {
        this.session = session;
    }

    /**
     *
     * @param realm
     */
    @Override
    public void preRemove(RealmModel realm) {

    }

    @Override
    public void preRemove(RealmModel realm, GroupModel group) {

    }

    @Override
    public void preRemove(RealmModel realm, RoleModel role) {

    }

    @Remove
    @Override
    public void close() {
        log.debug("close");
    }

    /**
     *
     * @param id
     * @param realm
     * @return
     */
    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        log.info("getUserById\n\n\tid = " + id + "\n\t realm = " + realm.getName());
        log.debug("Get EXT ID = >");
        log.debug("Get EXT ID = > " + StorageId.externalId(id));

        Long persistenceId = new Long(StorageId.externalId(id));

        log.debug("persistenceId => " + persistenceId);

        UserEntity entity = em.find(UserEntity.class, persistenceId);
        if (entity == null) {
            log.info("could not find user by id: " + id);
            return null;
        } else {
            log.debug("ID => " + entity.getId().toString());
        }
        return new UserAdapter(session, realm, model, entity);
    }

    /**
     *
     * @param username
     * @param realm
     * @return
     */
    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        log.info("getUserByUsername: " + username);
        TypedQuery<UserEntity> query = em.createNamedQuery("getUserByUsername", UserEntity.class);
        query.setParameter("username", username);
        List<UserEntity> result = query.getResultList();
        if (result.isEmpty()) {
            log.info("could not find username: " + username);
            return null;
        }

        return new UserAdapter(session, realm, model, result.get(0));
    }

    /**
     *
     * @param email
     * @param realm
     * @return
     */
    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        log.info("getUserByEmail");
        TypedQuery<UserEntity> query = em.createNamedQuery("getUserByEmail", UserEntity.class);
        query.setParameter("email", email);
        List<UserEntity> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return new UserAdapter(session, realm, model, result.get(0));
    }

    /**
     *
     * @param realm
     * @param username
     * @return
     */
    @Override
    public UserModel addUser(RealmModel realm, String username) {
        log.info("addUser");
        UserEntity entity = new UserEntity();
        //entity.setId(UUID.randomUUID().toString());
        entity.setUsername(username);

        em.persist(entity);
        logUser lUser = new logUser();
        lUser.setUsername(username);
        lUser.setUser_id(entity.getId().toString());
        em.persist(lUser);

        log.info("added user: " + username);

        try {            
            //String httpGet = doGet("http://192.168.1.240/helpdesk/service.php?command=getinclist", null);
            String httpGet = doGet("http://192.168.1.150:8080/testRest/admusers/hello/1500", null);
            log.info(httpGet);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return new UserAdapter(session, realm, model, entity);
    }

    /**
     *
     * @param realm
     * @param user
     * @return
     */
    @Override
    public boolean removeUser(RealmModel realm, UserModel user) {
        log.info("removeUser");
        String persistenceId = StorageId.externalId(user.getId());
        UserEntity entity = em.find(UserEntity.class, persistenceId);
        if (entity == null) {
            return false;
        }
        em.remove(entity);
        return true;
    }

    /**
     *
     * @param realm
     * @param user
     * @param delegate
     */
    @Override
    public void onCache(RealmModel realm, CachedUserModel user, UserModel delegate) {
        log.info("onCache\n\n\trealm = " + realm.getName() + "\n\tuser = " + user.getUsername() + "\n\tdelegate = " + delegate.getUsername());
        String password = ((UserAdapter) delegate).getPassword();
        log.info("password = " + password);
        log.info("PASSWORD_CACHE_KEY = " + PASSWORD_CACHE_KEY);
        if (password != null) {
            log.info("Add password in CACHE password = " + password);
            user.getCachedWith().put(PASSWORD_CACHE_KEY, password);
        }
    }

    /**
     *
     * @param credentialType
     * @return
     */
    @Override
    public boolean supportsCredentialType(String credentialType) {
        log.info("supportsCredentialType\n\n\tcredentialType = " + credentialType);
        return CredentialModel.PASSWORD.equals(credentialType);
    }

    /**
     * Вызывается при редактировании учетных данных пользователя
     *
     * @param realm
     * @param user
     * @param input
     * @return
     */
    @Override
    public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
        log.info("updateCredential \n\n\trealm = " + realm.getName() + "\n\tuser = " + user.getUsername() + "\n\tinput = " + input.toString());
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
            return false;
        }
        UserCredentialModel cred = (UserCredentialModel) input;
        UserAdapter adapter = getUserAdapter(user);
        log.info("cred.getValue() = > " + cred.getValue());
        // Устанавливаем пароль
        //adapter.setPassword(hashUtil.sha1(cred.getValue()));
        adapter.setPassword(cred.getValue());
        return true;
    }

    /**
     * Получает пользоателя. Если пользователь есть в кеше то берет из кеша
     *
     * @param user
     * @return
     */
    public UserAdapter getUserAdapter(UserModel user) {
        log.info("getUserAdapter\n\tuser = " + user.getUsername());
        UserAdapter adapter = null;
        if (user instanceof CachedUserModel) {
            log.info("User in cache Keycloak");
            adapter = (UserAdapter) ((CachedUserModel) user).getDelegateForUpdate();
        } else {
            log.info("User not in cache Keycloak");
            adapter = (UserAdapter) user;
        }
        return adapter;
    }

    /**
     *
     * @param realm
     * @param user
     * @param credentialType
     */
    @Override
    public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
        log.info("disableCredentialType");
        if (!supportsCredentialType(credentialType)) {
            return;
        }
        getUserAdapter(user).setPassword(null);
    }

    /**
     *
     * @param realm
     * @param user
     * @return
     */
    @Override
    public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {
        log.info("getDisableableCredentialTypes\n\n\trealm = " + realm.getName() + "\n\tuser = " + user.getUsername());
        if (getUserAdapter(user).getPassword() != null) {
            log.info("\n\tpassword is not null : " + getUserAdapter(user).getPassword());
            Set<String> set = new HashSet<>();
            set.add(CredentialModel.PASSWORD);
            log.info("\n\treturn CredentialModel.PASSWORD = " + CredentialModel.PASSWORD);
            return set;
        } else {
            return Collections.emptySet();
        }
    }

    /**
     *
     * @param realm
     * @param user
     * @param credentialType
     * @return
     */
    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        log.info("isConfiguredFor");
        return supportsCredentialType(credentialType) && getPassword(user) != null;
    }

    /**
     * Функция проверяет пароль введенный пользователем и пароль сохраненный в
     * базе
     *
     * @param realm
     * @param user
     * @param input
     * @return
     */
    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
        log.info("isValid\n\trealm = " + realm.getName() + "\n\tuser = " + user.getUsername() + "\n\tinput = " + input.getType());
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
            return false;
        }
        UserCredentialModel cred = (UserCredentialModel) input;

        String password = getPassword(user);
        log.info("\n\tcred device= " + cred.getDevice() + "\n\tpassword = " + password + "\n\tuserpass = " + cred.getValue());
        return (password != null) && (password.equals(cred.getValue()));
    }

    /**
     * Функция получает значение пароля из БД или из Кеша в зависимости есть ли
     * пользователь в кеше
     *
     * @param user
     * @return
     */
    private String getPassword(UserModel user) {
        log.info("getPassword");
        log.info("Class type user = " + user.getClass().getName());
        String password = null;
        if (user instanceof CachedUserModel) {
            log.info("User in cache");
            password = (String) ((CachedUserModel) user).getCachedWith().get(PASSWORD_CACHE_KEY);
        } else if (user instanceof UserAdapter) {
            log.info("User not in cache");
            password = ((UserAdapter) user).getPassword();
        }
        log.info("password => " + password);
        return password;
    }

    /**
     *
     * @param realm
     * @return
     */
    @Override
    public int getUsersCount(RealmModel realm) {
        log.info("getUsersCount");
        Object count = em.createNamedQuery("getUserCount").getSingleResult();
        return ((Number) count).intValue();
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm) {
        log.info("getUsers");
        return getUsers(realm, -1, -1);
    }

    /**
     *
     * @param realm
     * @param firstResult
     * @param maxResults
     * @return
     */
    @Override
    public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
        log.info("getUsers_1\n\n\trealm = " + realm.getName() + "\n\tfirstResult = " + firstResult + "\n\tmaxResults = " + maxResults);
        TypedQuery<UserEntity> query = em.createNamedQuery("getAllUsers", UserEntity.class);
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<UserEntity> results = query.getResultList();
        List<UserModel> users = new LinkedList<>();
        for (UserEntity entity : results) {
            users.add(new UserAdapter(session, realm, model, entity));
        }
        return users;
    }

    /**
     *
     * @param search
     * @param realm
     * @return
     */
    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm) {
        log.info("searchForUser");
        return searchForUser(search, realm, -1, -1);
    }

    /**
     *
     * @param search
     * @param realm
     * @param firstResult
     * @param maxResults
     * @return
     */
    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
        log.info("searchForUser");
        TypedQuery<UserEntity> query = em.createNamedQuery("searchForUser", UserEntity.class);
        query.setParameter("search", "%" + search.toLowerCase() + "%");
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<UserEntity> results = query.getResultList();
        List<UserModel> users = new LinkedList<>();
        for (UserEntity entity : results) {
            users.add(new UserAdapter(session, realm, model, entity));
        }
        return users;
    }

    /**
     *
     * @param params
     * @param realm
     * @return
     */
    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
        log.info("searchForUser_1");
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult, int maxResults) {
        log.info("searchForUser_2");
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
        log.info("getGroupMembers");
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
        log.info("getGroupMembers_1");
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
        log.info("searchForUserByUserAttribute");
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> getStoredUsers(RealmModel rm, int i, int i1) {
        log.info("getStoredUsers");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getStoredUsersCount(RealmModel rm) {
        log.info("getStoredUsersCount");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preRemove(RealmModel rm, ClientModel cm) {
        log.info("preRemove_1");
    }

    @Override
    public void preRemove(ProtocolMapperModel pmm) {
        log.info("preRemove_2");
    }

    @Override
    public void preRemove(RealmModel rm, UserModel um) {
        log.info("preRemove_3");
    }

    @Override
    public void preRemove(RealmModel rm, ComponentModel cm) {
        log.info("preRemove_4");
    }

    @Override
    public void setSingleAttribute(RealmModel rm, String string, String string1, String string2) {
        log.info("setSingleAttribute");
    }

    @Override
    public void setAttribute(RealmModel rm, String string, String string1, List<String> list) {
        log.info("setAttribute_1");
    }

    @Override
    public void removeAttribute(RealmModel rm, String string, String string1) {
        log.info("setAttribute_2");
    }

    @Override
    public MultivaluedHashMap<String, String> getAttributes(RealmModel rm, String string) {
        log.info("getAttributes");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getUsersByUserAttribute(RealmModel rm, String string, String string1) {
        log.info("getUsersByUserAttribute");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUserByFederatedIdentity(FederatedIdentityModel fim, RealmModel rm) {
        log.info("getUserByFederatedIdentity");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addFederatedIdentity(RealmModel rm, String string, FederatedIdentityModel fim) {
        log.info("addFederatedIdentity");
    }

    @Override
    public boolean removeFederatedIdentity(RealmModel rm, String string, String string1) {
        log.info("removeFederatedIdentity");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateFederatedIdentity(RealmModel rm, String string, FederatedIdentityModel fim) {
        log.info("updateFederatedIdentity");
    }

    @Override
    public Set<FederatedIdentityModel> getFederatedIdentities(String string, RealmModel rm) {
        log.info("");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FederatedIdentityModel getFederatedIdentity(String string, String string1, RealmModel rm) {
        log.info("");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addConsent(RealmModel rm, String string, UserConsentModel ucm) {
        log.info("");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserConsentModel getConsentByClient(RealmModel rm, String string, String string1) {
        log.info("");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserConsentModel> getConsents(RealmModel rm, String string) {
        log.info("");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateConsent(RealmModel rm, String string, UserConsentModel ucm) {
        log.info("updateConsent");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean revokeConsentForClient(RealmModel rm, String string, String string1) {
        log.info("revokeConsentForClient");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<GroupModel> getGroups(RealmModel rm, String string) {
        log.info("getGroups");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void joinGroup(RealmModel rm, String string, GroupModel gm) {
        log.info("joinGroup");
    }

    @Override
    public void leaveGroup(RealmModel rm, String string, GroupModel gm) {
        log.info("");
    }

    @Override
    public List<String> getMembership(RealmModel rm, GroupModel gm, int i, int i1) {
        log.info("getMembership");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> getRequiredActions(RealmModel rm, String string) {
        log.info("");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addRequiredAction(RealmModel rm, String string, String string1) {
        log.info("addRequiredAction");
    }

    @Override
    public void removeRequiredAction(RealmModel rm, String string, String string1) {
        log.info("");
    }

    @Override
    public void grantRole(RealmModel rm, String string, RoleModel rm1) {
        log.info("grantRole");
    }

    @Override
    public Set<RoleModel> getRoleMappings(RealmModel rm, String string) {
        log.info("getRoleMappings");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRoleMapping(RealmModel rm, String string, RoleModel rm1) {
        log.info("deleteRoleMapping");
    }

    @Override
    public void updateCredential(RealmModel rm, String string, CredentialModel cm) {
        log.info("updateCredential");
    }

    @Override
    public CredentialModel createCredential(RealmModel rm, String string, CredentialModel cm) {
        log.info("createCredential");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeStoredCredential(RealmModel rm, String string, String string1) {
        log.info("removeStoredCredential");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CredentialModel getStoredCredentialById(RealmModel rm, String string, String string1) {
        log.info("getStoredCredentialById");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CredentialModel> getStoredCredentials(RealmModel rm, String string) {
        log.info("getStoredCredentials");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CredentialModel> getStoredCredentialsByType(RealmModel rm, String string, String string1) {
        log.info("getStoredCredentialsByType");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CredentialModel getStoredCredentialByNameAndType(RealmModel rm, String string, String string1, String string2) {
        log.info("getStoredCredentialByNameAndType");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
