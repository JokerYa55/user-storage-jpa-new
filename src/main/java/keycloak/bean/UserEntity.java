package keycloak.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @Column(name = "id_app_1", unique = true, nullable = true)
    private String id_app_1;
    @Column(name = "id_app_2", unique = true, nullable = true)
    private String id_app_2;
    @Column(name = "id_app_3", unique = true, nullable = true)
    private String id_app_3;
    @Column(name = "id_app_4", unique = true, nullable = true)
    private String id_app_4;
    @Column(name = "id_app_5", unique = true, nullable = true)
    private String id_app_5;
    @Column(name = "id_app_6", unique = true, nullable = true)
    private String id_app_6;
    @Column(name = "id_app_7", unique = true, nullable = true)
    private String id_app_7;
    @Column(name = "id_app_8", unique = true, nullable = true)
    private String id_app_8;
    @Column(name = "id_app_9", unique = true, nullable = true)
    private String id_app_9;
    @Column(name = "id_app_10", unique = true, nullable = true)
    private String id_app_10;
    @Column(name = "id_app_11", unique = true, nullable = true)
    private String id_app_11;
    @Column(name = "id_app_12", unique = true, nullable = true)
    private String id_app_12;
    @Column(name = "id_app_13", unique = true, nullable = true)
    private String id_app_13;
    @Column(name = "id_app_14", unique = true, nullable = true)
    private String id_app_14;
    @Column(name = "id_app_15", unique = true, nullable = true)
    private String id_app_15;
    @Column(name = "id_app_16", unique = true, nullable = true)
    private String id_app_16;
    @Column(name = "id_app_17", unique = true, nullable = true)
    private String id_app_17;
    @Column(name = "id_app_18", unique = true, nullable = true)
    private String id_app_18;
    @Column(name = "id_app_19", unique = true, nullable = true)
    private String id_app_19;
    @Column(name = "id_app_20", unique = true, nullable = true)
    private String id_app_20;
    @Column(name = "id_app_21", unique = true, nullable = true)
    private String id_app_21;
    @Column(name = "id_app_22", unique = true, nullable = true)
    private String id_app_22;
    @Column(name = "id_app_23", unique = true, nullable = true)
    private String id_app_23;
    @Column(name = "id_app_24", unique = true, nullable = true)
    private String id_app_24;
    @Column(name = "id_app_25", unique = true, nullable = true)
    private String id_app_25;
    @Column(name = "id_app_27", unique = true, nullable = true)
    private String id_app_27;
    @Column(name = "id_app_28", unique = true, nullable = true)
    private String id_app_28;
    @Column(name = "id_app_29", unique = true, nullable = true)
    private String id_app_29;
    @Column(name = "id_app_30", unique = true, nullable = true)
    private String id_app_30;
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
    @Column(name = "description", unique = false, nullable = true)
    private String description;
//    @Column(name = "federation_link", unique = false, nullable = true)
//    private String federation_link;

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
    public String getId_app_1() {
        return id_app_1;
    }

    /**
     *
     * @param id_app_1
     */
    public void setId_app_1(String id_app_1) {
        this.id_app_1 = id_app_1;
    }

    /**
     *
     * @return
     */
    public String getId_app_2() {
        return id_app_2;
    }

    /**
     *
     * @param id_app_2
     */
    public void setId_app_2(String id_app_2) {
        this.id_app_2 = id_app_2;
    }

    /**
     *
     * @return
     */
    public String getId_app_3() {
        return id_app_3;
    }

    /**
     *
     * @param id_app_3
     */
    public void setId_app_3(String id_app_3) {
        this.id_app_3 = id_app_3;
    }

    /**
     *
     * @return
     */
    public String getId_app_4() {
        return id_app_4;
    }

    /**
     *
     * @param id_app_4
     */
    public void setId_app_4(String id_app_4) {
        this.id_app_4 = id_app_4;
    }

    /**
     *
     * @return
     */
    public String getId_app_5() {
        return id_app_5;
    }

    /**
     *
     * @param id_app_5
     */
    public void setId_app_5(String id_app_5) {
        this.id_app_5 = id_app_5;
    }

    /**
     *
     * @return
     */
    public String getId_app_6() {
        return id_app_6;
    }

    /**
     *
     * @param id_app_6
     */
    public void setId_app_6(String id_app_6) {
        this.id_app_6 = id_app_6;
    }

    /**
     *
     * @return
     */
    public String getId_app_7() {
        return id_app_7;
    }

    /**
     *
     * @param id_app_7
     */
    public void setId_app_7(String id_app_7) {
        this.id_app_7 = id_app_7;
    }

    /**
     *
     * @return
     */
    public String getId_app_8() {
        return id_app_8;
    }

    /**
     *
     * @param id_app_8
     */
    public void setId_app_8(String id_app_8) {
        this.id_app_8 = id_app_8;
    }

    /**
     *
     * @return
     */
    public String getId_app_9() {
        return id_app_9;
    }

    /**
     *
     * @param id_app_9
     */
    public void setId_app_9(String id_app_9) {
        this.id_app_9 = id_app_9;
    }

    /**
     *
     * @return
     */
    public String getId_app_10() {
        return id_app_10;
    }

    /**
     *
     * @param id_app_10
     */
    public void setId_app_10(String id_app_10) {
        this.id_app_10 = id_app_10;
    }

    /**
     *
     * @return
     */
    public String getId_app_11() {
        return id_app_11;
    }

    /**
     *
     * @param id_app_11
     */
    public void setId_app_11(String id_app_11) {
        this.id_app_11 = id_app_11;
    }

    /**
     *
     * @return
     */
    public String getId_app_12() {
        return id_app_12;
    }

    /**
     *
     * @param id_app_12
     */
    public void setId_app_12(String id_app_12) {
        this.id_app_12 = id_app_12;
    }

    /**
     *
     * @return
     */
    public String getId_app_13() {
        return id_app_13;
    }

    /**
     *
     * @param id_app_13
     */
    public void setId_app_13(String id_app_13) {
        this.id_app_13 = id_app_13;
    }

    /**
     *
     * @return
     */
    public String getId_app_14() {
        return id_app_14;
    }

    /**
     *
     * @param id_app_14
     */
    public void setId_app_14(String id_app_14) {
        this.id_app_14 = id_app_14;
    }

    /**
     *
     * @return
     */
    public String getId_app_15() {
        return id_app_15;
    }

    /**
     *
     * @param id_app_15
     */
    public void setId_app_15(String id_app_15) {
        this.id_app_15 = id_app_15;
    }

    /**
     *
     * @return
     */
    public String getId_app_16() {
        return id_app_16;
    }

    /**
     *
     * @param id_app_16
     */
    public void setId_app_16(String id_app_16) {
        this.id_app_16 = id_app_16;
    }

    /**
     *
     * @return
     */
    public String getId_app_17() {
        return id_app_17;
    }

    /**
     *
     * @param id_app_17
     */
    public void setId_app_17(String id_app_17) {
        this.id_app_17 = id_app_17;
    }

    /**
     *
     * @return
     */
    public String getId_app_18() {
        return id_app_18;
    }

    /**
     *
     * @param id_app_18
     */
    public void setId_app_18(String id_app_18) {
        this.id_app_18 = id_app_18;
    }

    /**
     *
     * @return
     */
    public String getId_app_19() {
        return id_app_19;
    }

    /**
     *
     * @param id_app_19
     */
    public void setId_app_19(String id_app_19) {
        this.id_app_19 = id_app_19;
    }

    /**
     *
     * @return
     */
    public String getId_app_20() {
        return id_app_20;
    }

    /**
     *
     * @param id_app_20
     */
    public void setId_app_20(String id_app_20) {
        this.id_app_20 = id_app_20;
    }

    /**
     *
     * @return
     */
    public String getId_app_21() {
        return id_app_21;
    }

    /**
     *
     * @param id_app_21
     */
    public void setId_app_21(String id_app_21) {
        this.id_app_21 = id_app_21;
    }

    /**
     *
     * @return
     */
    public String getId_app_22() {
        return id_app_22;
    }

    /**
     *
     * @param id_app_22
     */
    public void setId_app_22(String id_app_22) {
        this.id_app_22 = id_app_22;
    }

    /**
     *
     * @return
     */
    public String getId_app_23() {
        return id_app_23;
    }

    /**
     *
     * @param id_app_23
     */
    public void setId_app_23(String id_app_23) {
        this.id_app_23 = id_app_23;
    }

    /**
     *
     * @return
     */
    public String getId_app_24() {
        return id_app_24;
    }

    /**
     *
     * @param id_app_24
     */
    public void setId_app_24(String id_app_24) {
        this.id_app_24 = id_app_24;
    }

    /**
     *
     * @return
     */
    public String getId_app_25() {
        return id_app_25;
    }

    /**
     *
     * @param id_app_25
     */
    public void setId_app_25(String id_app_25) {
        this.id_app_25 = id_app_25;
    }

    /**
     *
     * @return
     */
    public String getId_app_27() {
        return id_app_27;
    }

    /**
     *
     * @param id_app_27
     */
    public void setId_app_27(String id_app_27) {
        this.id_app_27 = id_app_27;
    }

    /**
     *
     * @return
     */
    public String getId_app_28() {
        return id_app_28;
    }

    /**
     *
     * @param id_app_28
     */
    public void setId_app_28(String id_app_28) {
        this.id_app_28 = id_app_28;
    }

    /**
     *
     * @return
     */
    public String getId_app_29() {
        return id_app_29;
    }

    /**
     *
     * @param id_app_29
     */
    public void setId_app_29(String id_app_29) {
        this.id_app_29 = id_app_29;
    }

    /**
     *
     * @return
     */
    public String getId_app_30() {
        return id_app_30;
    }

    /**
     *
     * @param id_app_30
     */
    public void setId_app_30(String id_app_30) {
        this.id_app_30 = id_app_30;
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

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", thirdName=" + thirdName + ", email=" + email + ", password=" + password + ", password_not_hash=" + password_not_hash + ", phone=" + phone + ", hesh_type=" + hesh_type + ", salt=" + salt + ", id_app_1=" + id_app_1 + ", id_app_2=" + id_app_2 + ", id_app_3=" + id_app_3 + ", id_app_4=" + id_app_4 + ", id_app_5=" + id_app_5 + ", id_app_6=" + id_app_6 + ", id_app_7=" + id_app_7 + ", id_app_8=" + id_app_8 + ", id_app_9=" + id_app_9 + ", id_app_10=" + id_app_10 + ", id_app_11=" + id_app_11 + ", id_app_12=" + id_app_12 + ", id_app_13=" + id_app_13 + ", id_app_14=" + id_app_14 + ", id_app_15=" + id_app_15 + ", id_app_16=" + id_app_16 + ", id_app_17=" + id_app_17 + ", id_app_18=" + id_app_18 + ", id_app_19=" + id_app_19 + ", id_app_20=" + id_app_20 + ", id_app_21=" + id_app_21 + ", id_app_22=" + id_app_22 + ", id_app_23=" + id_app_23 + ", id_app_24=" + id_app_24 + ", id_app_25=" + id_app_25 + ", id_app_27=" + id_app_27 + ", id_app_28=" + id_app_28 + ", id_app_29=" + id_app_29 + ", id_app_30=" + id_app_30 + ", user_status=" + user_status + ", create_date=" + create_date + ", update_date=" + update_date + ", user_region=" + user_region + ", enabled=" + enabled + ", description=" + description + '}';
    }

}
