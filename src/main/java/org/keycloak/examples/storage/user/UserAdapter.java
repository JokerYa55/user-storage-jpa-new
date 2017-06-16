package org.keycloak.examples.storage.user;

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

    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UserEntity entity) {
        super(session, realm, model);
        log.debug("UserAdapter");
        this.entity = entity;
        // внутренний ID
        keycloakId = StorageId.keycloakId(model, entity.getId());
    }

    public String getPassword() {
        return entity.getPassword();
    }

    public void setPassword(String password) {
        entity.setPassword(password);
    }

    @Override
    public String getUsername() {
        return entity.getUsername();
    }

    @Override
    public void setUsername(String username) {
        entity.setUsername(username);

    }

    @Override
    public void setEmail(String email) {
        entity.setEmail(email);
    }

    @Override
    public String getEmail() {
        return entity.getEmail();
    }

    @Override
    public String getId() {
        return keycloakId;
    }

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
     * @param name имя аттрибута
     * @param values Значения аттрибута (берется из локальной базы + из внешней базы в случае федерации)
     */
    @Override
    public void setAttribute(String name, List<String> values) {
        log.debug("setAttribute");
        if (name.equals("phone")) {
            entity.setPhone(values.get(0));
        } else {
            super.setAttribute(name, values);
        }
    }

    @Override
    public String getFirstAttribute(String name) {
        log.debug("getFirstAttribute");
        if (name.equals("phone")) {
            return entity.getPhone();
        } else {
            return super.getFirstAttribute(name);
        }
    }

    /**
     * Метод позволяет добавлять аттрибуты из внешней базы в интерфейс Keycloak
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
        all.add("phone", entity.getPhone());
        all.add("address", entity.getAddress());
        return all;
    }

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
}
