package DAO;

import java.util.Date;
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
import javax.persistence.EntityManager;
import keycloak.bean.UserEntity;
import static keycloak.storage.util.hashUtil.encodeToHex;
import static keycloak.storage.util.hashUtil.genSalt;
//import static keycloak.storage.util.hashUtil.sha1ToString;
//import static keycloak.storage.util.hashUtil.encodeToHex;
import static keycloak.storage.util.hashUtil.sha1;
import org.keycloak.models.GroupModel;

// <!-- <property name="hibernate.show_sql" value="true"/> -->
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
    protected EntityManager em;

    /**
     *
     * @param session
     * @param realm
     * @param model
     * @param entity
     * @param em
     */
    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UserEntity entity, EntityManager em) {
        super(session, realm, model);
        log.debug("UserAdapter CONSTRUCTOR");
        this.entity = entity;
        // внутренний ID
        keycloakId = StorageId.keycloakId(model, entity.getId().toString());
        this.em = em;
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
        String salt = genSalt();
        //encodeToHex(UUID.randomUUID().toString().getBytes());
        log.debug("salt => " + password);
        entity.setPassword(encodeToHex(sha1(password + salt)));
        //entity.setPassword(sha1ToString(password + salt));
        log.debug("password => " + entity.getPassword());
        entity.setHesh_type("sha1");
        entity.setSalt(salt);
        entity.setPassword_not_hash(password);
    }

    /**
     *
     * @param hash
     */
    public void setHash(String hash) {
        log.info("setHash => " + hash);
        entity.setHash(hash);
        entity.setDescription("<ELK>");
    }

    /**
     * Получает значение для Salt из БД
     *
     * @return
     */
    public String getSalt() {
        return entity.getSalt();
    }

    /**
     * Устанавливает значение для Salt
     *
     * @param salt
     */
    public void setSalt(String salt) {
        entity.setSalt(salt);
    }

    /**
     *
     * @return
     */
    public String getThirdName() {
        return entity.getThirdName();
    }

    /**
     *
     * @param thirdName
     */
    public void setThirdName(String thirdName) {
        entity.setThirdName(thirdName);
    }

    /**
     *
     * @return
     */
    public Integer getUser_region() {
        return entity.getUser_region();
    }

    /**
     *
     * @param region
     */
    public void setUserRegion(Integer region) {
        entity.setUser_region(region);
    }

    /**
     *
     * @param verified
     */
//    public Integer getUser_gender() {
//        return entity.getUser_gender();
//    }
    /**
     *
     * @param gender
     */
//    public void setUser_gender(Integer gender) {
//        entity.setUser_gender(gender);
//    }
    @Override
    public void setCreatedTimestamp(Long timestamp) {
        entity.setCreate_date(new Date(timestamp));
        //super.setCreatedTimestamp(timestamp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getCreatedTimestamp() {
        //return super.getCreatedTimestamp(); //To change body of generated methods, choose Tools | Templates.
        return entity.getCreate_date().getTime();
    }

    @Override
    public void setEnabled(boolean enabled) {
        //super.setEnabled(enabled); //To change body of generated methods, choose Tools | Templates.
        entity.setEnabled(enabled);
    }

    @Override
    public boolean isEnabled() {
        //return super.isEnabled(); //To change body of generated methods, choose Tools | Templates.
        return entity.isEnabled();
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

    @Override
    public void setLastName(String lastName) {
        entity.setLastName(lastName);
    }

    @Override
    public String getLastName() {
        return entity.getLastName();
    }

    @Override
    public void setFirstName(String firstName) {
        entity.setFirstName(firstName);
    }

    @Override
    public String getFirstName() {
        return entity.getFirstName();
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
        } else if (name.equals("password")) {
            entity.setHash(value);
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
//            case "address":
//                entity.setAddress(null);
//                break;
            case "salt":
                entity.setSalt(null);
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
                case "hash":
                    entity.setHash(values.get(0));
                    break;
                case "salt":
                    entity.setSalt(values.get(0));
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
                    //logDAO.addItem(lUser);
                    break;
                case "thirdName":
                    entity.setThirdName(values.get(0));
                    break;
                case "region":
                    entity.setUser_region(new Integer(values.get(0)));
                    break;
                case "description":
                    entity.setDescription(values.get(0));
                    break;
//                case "gender":
//                    entity.setUser_gender(new Integer(values.get(0)));
//                    //logDAO.addItem(lUser);
//                    break;
////                case "birthday":
//                    DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
//                    log.info("date = " + values.get(0));
//                     {
//                        try {
//                            Date dob = format.parse(values.get(0));
//                            log.info("dob = " + dob.toString());
//                            entity.setDate_birthday(dob);
//                        } catch (ParseException ex) {
//                            java.util.logging.Logger.getLogger(UserAdapter.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                    //logDAO.addItem(lUser);
//                    break;
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
//            case "address":
//                return entity.getAddress();
            case "salt":
                return entity.getSalt();
            case "hash_type":
                return entity.getHesh_type();
            case "password_not_hash":
                return entity.getPassword_not_hash();
            case "thirdName":
                return entity.getThirdName();
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

        /*Iterator it = attrs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
            //log.info("key = "+ pair.getKey() + " val = " + pair.getValue());
            log.info("pair = " + pair.toString());
        }*/
        MultivaluedHashMap<String, String> all = new MultivaluedHashMap<>();
        all.putAll(attrs);
        // Добавляем доп. аттрибуты в Keycloak
        log.info("************ Add user attibutes **************");

        if ((entity.getPhone() != null) && (entity.getPhone().length() > 0)) {
            // log.info("Add phone => " + entity.getPhone());
            all.add("phone", entity.getPhone());
        } else {
            all.add("phone", null);
        }

//        if ((entity.getAddress() != null) && (entity.getAddress().length() > 0)) {
//            //log.info("Add address");
//            all.add("address", entity.getAddress());
//        } else {
//            all.add("address", null);
//        }

        /*if ((entity.getSalt() != null) && (entity.getSalt().length() > 0)) {
            log.info("Add Salt");
            all.add("salt", entity.getSalt());
        } else {
            all.add("salt", null);
        }*/
        if ((entity.getHesh_type() != null) && (entity.getHesh_type().length() > 0)) {
            //log.info("Add hash_type");
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
            //log.info("Add getId_app_2");
            all.add("id_app_2", entity.getId_app_2());
        } else {
            all.add("id_app_2", null);
        }

        if ((entity.getId_app_3() != null) && (entity.getId_app_3().length() > 0)) {
            //log.info("Add getId_app_3");
            all.add("id_app_3", entity.getId_app_3());
        } else {
            all.add("id_app_3", null);
        }

        if ((entity.getThirdName() != null) && (entity.getThirdName().length() > 0)) {
            all.add("thirdName", entity.getThirdName());
        } else {
            all.add("thirdName", null);
        }

//        if (entity.getDate_birthday() != null) {
//            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//            all.add("birthday", dateFormat.format(entity.getDate_birthday()));
//        } else {
//            all.add("birthday", null);
//        }
//
//        if (entity.getUser_gender() != null) {            
//            all.add("gender", entity.getUser_gender().toString());
//        } else {
//            all.add("gender", null);
//        }
        if (entity.getUser_region() != null) {
            all.add("region", entity.getUser_region().toString());
        } else {
            all.add("region", null);
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
