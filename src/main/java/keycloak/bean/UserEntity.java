package keycloak.bean;

import DAO.UserAttributeDAO;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.jboss.logging.Logger;

/**
 * @version 1
 */
@NamedQueries({
    @NamedQuery(name = "getUserByUsername", query = "select u from UserEntity u where u.username = :username and u.user_status=0")
    ,
    @NamedQuery(name = "getUserByEmail", query = "select u from UserEntity u where u.email = :email and u.user_status=0")
    ,
    @NamedQuery(name = "getUserCount", query = "select count(u) from UserEntity u where u.user_status=0")
    ,
    @NamedQuery(name = "getAllUsers", query = "select u from UserEntity u where u.user_status=0 order by u.username")
    ,
    @NamedQuery(name = "searchForUser", query = "select u from UserEntity u where "
            + "( lower(u.username) like :search or u.email like :search ) and  u.user_status=0 order by u.username"),})
@Entity
@Table(name = "t_users", indexes = {
    @Index(name = "t_users_status_idx", columnList = "user_status")
    ,
    @Index(name = "t_users_username_idx", columnList = "username")})
public class UserEntity implements Serializable {

    private static final Logger log = Logger.getLogger(UserEntity.class);

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_users_id_seq")
    @SequenceGenerator(name = "t_users_id_seq", sequenceName = "t_users_id_seq", allocationSize = 1)
    private Long id;
    // Логин
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    // Фамилия    
    @Column(name = "firstName", unique = false, nullable = true)
    private String firstName;
    // Имя
    @Column(name = "lastName", unique = false, nullable = true)
    private String lastName;
    // Отчество
    @Column(name = "thirdName", unique = false, nullable = true)
    private String thirdName;
    // e-mail
    @Column(name = "email", unique = true, nullable = true)
    private String email;
    // Пароль
    @Column(name = "password", nullable = true)
    private String password;
    // Незашифрованный пароль
    @Column(name = "password_not_hash", nullable = true)
    private String password_not_hash;
    // Телефон
    @Column(name = "phone", nullable = true)
    private String phone;
    // Адрес
    //@Column(name = "address", nullable = true)
    //private String address;
    @Column(name = "hash_type", nullable = true)
    private String hesh_type;
    @Column(name = "salt", nullable = true)
    private String salt;
    @Column(name = "user_status", unique = false, nullable = false, columnDefinition = "integer DEFAULT 0")
    private Integer user_status;
    @Column(name = "create_date", unique = false, nullable = false, columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date create_date;
    @Column(name = "update_date", unique = false, nullable = true, columnDefinition = "timestamp")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date update_date;
//    @Column(name = "date_birthday", unique = false, nullable = true)
//    @Temporal(javax.persistence.TemporalType.DATE)
//    private Date date_birthday;
//    @Column(name = "user_gender", unique = false, nullable = true)
//    private Integer user_gender;
    @Column(name = "user_region", unique = false, nullable = true)
    private Integer user_region;
    @Column(name = "enabled", unique = false, nullable = false, columnDefinition = "boolean DEFAULT true")
    private boolean enabled;
//    @Column(name = "email_verified", unique = false, nullable = false, columnDefinition = "boolean DEFAULT false")
//    private boolean email_verified;
    @Column(name = "description", unique = false, nullable = true)
    private String description;
//    @Column(name = "federation_link", unique = false, nullable = true)
//    private String federation_link;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", orphanRemoval=true)
    private Collection<UserAttribute> userAttributeCollection;

    public UserEntity() {
    }

    public UserEntity(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        log.debug("setPassword => " + password);
        this.password = password;
        //this.hash = keycloak.storage.util.hashUtil.sha1(this.password);
        //this.hesh_type = keycloak.storage.util.hashUtil.md5(this.password); 
    }

    /**
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @param hash
     */
    public void setHash(String hash) {
        //this.hash = keycloak.storage.util.hashUtil.sha1(this.password);
        this.password = hash;
    }

    /**
     *
     * @return
     */
    public String getHesh_type() {
        return hesh_type;
    }

    /**
     *
     * @param hesh_type
     */
    public void setHesh_type(String hesh_type) {
        this.hesh_type = hesh_type;
    }

    /**
     *
     * @return
     */
    public String getPassword_not_hash() {
        return password_not_hash;
    }

    /**
     *
     * @param password_not_hash
     */
    public void setPassword_not_hash(String password_not_hash) {
        this.password_not_hash = password_not_hash;
    }

    /**
     *
     * @return
     */
    public String getSalt() {
        return salt;
    }

    /**
     *
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getUser_region() {
        return user_region;
    }

    public void setUser_region(Integer user_region) {
        this.user_region = user_region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public Collection<UserAttribute> getUserAttributeCollection() {
        return userAttributeCollection;
    }

    public void setUserAttributeCollection(Collection<UserAttribute> userAttributeCollection) {
        this.userAttributeCollection = userAttributeCollection;
    }

    public void addUserAttribute(UserAttribute attr) {
        log.info("addUserAttribute => " + attr);
        log.info("addUserAttribute => " + this);
        // Проверяем есть ли такой аттрибут в БД
        boolean flag = false;
        if (this.userAttributeCollection != null) {
            for (UserAttribute t : this.userAttributeCollection) {
                if (t.getName().equals(attr.getName())) {
                    t.setValue(attr.getValue());
                    flag = true;
                }
            }
        } else {
            this.userAttributeCollection = new LinkedList<>();
        }
        UserAttribute attrTemp = attr;
        log.info("this.userAttributeCollection => " + this.userAttributeCollection);
        if (flag == false) {
            if (attrTemp.getId() == null) {
                attrTemp = new UserAttribute(attr.getName(), attr.getValue(), this, true);
            }
            this.getUserAttributeCollection().add(attrTemp);
        }
    }

    public void deleteAttribute(String attrName) {
        /*log.info("deleteAttribute => " + attrName);
        Object temp = null;
        for (UserAttribute t : this.userAttributeCollection) {
            log.info("t => " + t);
            if (t.getName().equals(attrName)) {
                temp = t;
            }
        }
        log.info("temp = " + temp);
        if (temp != null)this.userAttributeCollection.remove(temp);     */

    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", thirdName=" + thirdName + ", email=" + email + '}';
    }

}
