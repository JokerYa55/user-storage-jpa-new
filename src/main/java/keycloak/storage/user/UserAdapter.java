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
        keycloakId = StorageId.keycloakId(model, entity.getId());
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return entity.getPassword();
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        entity.setPassword(password);
    }

    /**
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

    @Override
    public void removeAttribute(String name) {
        log.debug("removeAttribute");
        if (name.equals("phone")) {
            entity.setPhone(null);
        } else {
            super.removeAttribute(name);
        }
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
        log.debug("setAttribute");
        switch (name) {
            case "phone":
                entity.setPhone(values.get(0));
                break;
            case "address":
                entity.setAddress(values.get(0));
                break;
            case "elk_id":
                entity.setElk_id(values.get(0));
                break;
            case "hash":
                entity.setHash(values.get(0));
                break;
            case "hash_type":
                entity.setHesh_type(values.get(0));
                break;
            case "elk_b2b_id":
                entity.setElk_b2b_id(values.get(0));
                break;
            default:
                super.setAttribute(name, values);
                break;
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
            case "elk_id":
                return entity.getElk_id();
            case "hash":
                return entity.getHash();
            default:
                return super.getFirstAttribute(name);
        }
    }

    /**
     * Метод позволяет добавлять аттрибуты из внешней базы в интерфейс Keycloak
     *
     * @return списое аттрибутов пользователя
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
        log.info("Add user attibutes");
        log.info("Add phone");
        all.add("phone", entity.getPhone());
        log.info("Add address");
        all.add("address", entity.getAddress());
        log.info("Add hash");
        all.add("hash", entity.getHash());
        log.info("Add elk_id");
        all.add("elk_id", entity.getElk_id());
        log.info("Add hash_type");
        all.add("hash_type", entity.getHesh_type());
        log.info("Add elk_b2b_id");
        all.add("elk_b2b_id", entity.getElk_b2b_id());
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
