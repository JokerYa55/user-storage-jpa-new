package keycloak.storage.user;

import java.util.Iterator;
import org.jboss.logging.Logger;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static keycloak.storage.util.hashUtil.sha1;
import org.keycloak.models.GroupModel;

/**
 * Класс для представления пользователя внутри Keycloak
 *
 * @version 1
 * @author Vasiliy Andritsov
 *
 */
public class UserAdapter extends AbstractUserAdapterFederatedStorage {

    private static final Logger log = Logger.getLogger(UserAdapter.class);
    protected UserEntity entity;
    protected String keycloakId;

    /**
     *
     * @param session
     * @param realm
     * @param model
     * @param entity
     */
    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UserEntity entity) {
        super(session, realm, model);
        log.debug("UserAdapter CONSTRUCTOR");
        this.entity = entity;
        // внутренний ID
        keycloakId = StorageId.keycloakId(model, entity.getId().toString());
    }

    /**
     * Возвращает строку записаную в БД в поле password
     *
     * @return - возвращает пароль сохраненный в БД
     */
    public String getPassword() {
        log.debug("getPassword => " + entity.getPassword());
        return entity.getPassword();
    }

    /**
     * Записывает hash пароля и незашифрованный пароль в БД
     *
     * @param password - пароль пользователя в незашифрованом виде
     */
    public void setPassword(String password) {
        log.debug("UserAdapter  setPassword => " + password);
        entity.setPassword(sha1(password));
        entity.setHesh_type("sha1");
        entity.setPassword_not_hash(password);
    }

    /**
     * Возвращает строку с данными из поля username БД
     *
     * @return
     */
    @Override
    public String getUsername() {
        return entity.getUsername();
    }

    /**
     *
     * @param username
     */
    @Override
    public void setUsername(String username) {
        entity.setUsername(username);

    }

    /**
     *
     * @param email
     */
    @Override
    public void setEmail(String email) {
        entity.setEmail(email);
    }

    /**
     *
     * @return
     */
    @Override
    public String getEmail() {
        return entity.getEmail();
    }

    /**
     *
     * @return
     */
    @Override
    public String getId() {
        return keycloakId;
    }

    /**
     *
     * @param name
     * @param value
     */
    @Override
    public void setSingleAttribute(String name, String value) {
        log.debug("setSingleAttribute");
        if (name.equals("phone")) {
            entity.setPhone(value);
        } else {
            super.setSingleAttribute(name, value);
        }
    }

    /**
     *
     * @param name
     */
    @Override
    public void removeAttribute(String name) {
        log.debug("removeAttribute");

        switch (name) {
            case "phone":
                entity.setPhone(null);
                break;
            case "address":
                entity.setAddress(null);
                break;
            case "hash":
                entity.setHash(null);
                break;
            case "hash_type":
                entity.setHesh_type(null);
                break;
            case "password_not_hash":
                entity.setPassword_not_hash(null);
                break;

            default:
                super.removeAttribute(name);
                break;
        }
        /*
        if (name.equals("phone")) {
            entity.setPhone(null);
        } else {
            super.removeAttribute(name);
        }*/
    }

    /**
     * Обновляет аттрибуты из интерфейса Keycloak
     *
     * @param name имя аттрибута
     * @param values Значения аттрибута (берется из локальной базы + из внешней
     * базы в случае федерации)
     */
    @Override
    public void setAttribute(String name, List<String> values) {
        log.debug("******* setAttribute => " + values.get(0) + " ******");
        if ((values.get(0) != null) && (values.get(0).length() > 0)) {
            switch (name) {
                case "phone":
                    entity.setPhone(values.get(0));
                    break;
                case "address":
                    entity.setAddress(values.get(0));
                    break;
                case "hash":
                    entity.setHash(values.get(0));
                    break;
                case "hash_type":
                    entity.setHesh_type(values.get(0));
                    break;
                case "password_not_hash":
                    entity.setPassword_not_hash(values.get(0));
                    break;
                case "id_app_1":
                    entity.setId_app_1(values.get(0));
                    break;
                case "id_app_2":
                    entity.setId_app_2(values.get(0));
                    break;
                case "id_app_3":
                    entity.setId_app_3(values.get(0));
                    break;
                default:
                    super.setAttribute(name, values);
                    break;
            }
        }
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public String getFirstAttribute(String name) {
        log.debug("getFirstAttribute => " + name);
        switch (name) {
            case "phone":
                return entity.getPhone();
            case "address":
                return entity.getAddress();
            case "hash":
                return entity.getHash();
            case "hash_type":
                return entity.getHesh_type();
            case "password_not_hash":
                return entity.getPassword_not_hash();

            default:
                return super.getFirstAttribute(name);
        }
    }

    /**
     * Метод позволяет добавлять аттрибуты из внешней базы в интерфейс Keycloak
     *
     * @return
     */
    @Override
    public Map<String, List<String>> getAttributes() {
        log.debug("getAttributes");
        Map<String, List<String>> attrs = super.getAttributes();

        Iterator it = attrs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
            //log.info("key = "+ pair.getKey() + " val = " + pair.getValue());
            log.info("pair = " + pair.toString());
        }

        MultivaluedHashMap<String, String> all = new MultivaluedHashMap<>();
        all.putAll(attrs);
        // Добавляем доп. аттрибуты в Keycloak
        log.info("************ Add user attibutes **************");

        if ((entity.getPhone() != null) && (entity.getPhone().length() > 0)) {
            log.info("Add phone => " + entity.getPhone());
            all.add("phone", entity.getPhone());
        } else {
            all.add("phone", null);
        }

        if ((entity.getAddress() != null) && (entity.getAddress().length() > 0)) {
            log.info("Add address");
            all.add("address", entity.getAddress());
        } else {
            all.add("address", null);
        }

        if ((entity.getHash() != null) && (entity.getHash().length() > 0)) {
            log.info("Add hash");
            all.add("hash", entity.getHash());
        } else {
            all.add("hash", null);
        }

        if ((entity.getHesh_type() != null) && (entity.getHesh_type().length() > 0)) {
            log.info("Add hash_type");
            all.add("hash_type", entity.getHesh_type());
        } else {
            all.add("hash_type", null);
        }

        if ((entity.getId_app_1() != null) && (entity.getId_app_1().length() > 0)) {
            log.info("Add getId_app_1");
            all.add("id_app_1", entity.getId_app_1());
        } else {
            all.add("id_app_1", null);
        }

        if ((entity.getId_app_2() != null) && (entity.getId_app_2().length() > 0)) {
            log.info("Add getId_app_2");
            all.add("id_app_2", entity.getId_app_2());
        } else {
            all.add("id_app_2", null);
        }

        if ((entity.getId_app_3() != null) && (entity.getId_app_3().length() > 0)) {
            log.info("Add getId_app_3");
            all.add("id_app_3", entity.getId_app_3());
        } else {
            all.add("id_app_3", null);
        }

        return all;
    }

    /**
     *
     * @param name Имя параметра
     * @return
     */
    @Override
    public List<String> getAttribute(String name) {
        log.debug("getAttribute");
        if (name.equals("phone")) {
            List<String> phone = new LinkedList<>();
            phone.add(entity.getPhone());
            return phone;
        } else {
            return super.getAttribute(name);
        }
    }

    /**
     * Метод возвращает группы пользователя. Позволяет добавлять группы.
     *
     * @return возвращает списое групп пользователя
     */
    @Override
    public Set<GroupModel> getGroups() {
        log.info("getGroups()");
        return super.getGroups(); //To change body of generated methods, choose Tools | Templates.
    }

}
