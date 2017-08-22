package keycloak.storage.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.jboss.logging.Logger;

/**
 * @version 1
 */
@NamedQueries({
    @NamedQuery(name = "getUserByUsername", query = "select u from UserEntity u where u.username = :username")
    ,
        @NamedQuery(name = "getUserByEmail", query = "select u from UserEntity u where u.email = :email")
    ,
        @NamedQuery(name = "getUserCount", query = "select count(u) from UserEntity u")
    ,
        @NamedQuery(name = "getAllUsers", query = "select u from UserEntity u order by u.username")
    ,
        @NamedQuery(name = "searchForUser", query = "select u from UserEntity u where "
            + "( lower(u.username) like :search or u.email like :search ) order by u.username"),})
@Entity
@Table(name = "t_users")
public class UserEntity {

    private static final Logger log = Logger.getLogger(UserEntity.class);

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_users_id_seq")
    @SequenceGenerator(name = "t_users_id_seq", sequenceName = "t_users_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "email", unique = true, nullable = true)
    private String email;
    @Column(name = "password", nullable = true)
    private String password;
    @Column(name = "password_not_hash", nullable = true)
    private String password_not_hash;
    @Column(name = "phone", nullable = true)
    private String phone;
    @Column(name = "address", nullable = true)
    private String address;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        log.debug("setPassword => " + password);
        this.password = password;
        //this.hash = keycloak.storage.util.hashUtil.sha1(this.password);
        //this.hesh_type = keycloak.storage.util.hashUtil.md5(this.password); 
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHash(String hash) {
        //this.hash = keycloak.storage.util.hashUtil.sha1(this.password);
    }

    public String getHesh_type() {
        return hesh_type;
    }

    public void setHesh_type(String hesh_type) {
        this.hesh_type = hesh_type;
    }

    public String getPassword_not_hash() {
        return password_not_hash;
    }

    public void setPassword_not_hash(String password_not_hash) {
        this.password_not_hash = password_not_hash;
    }

    public String getId_app_1() {
        return id_app_1;
    }

    public void setId_app_1(String id_app_1) {
        this.id_app_1 = id_app_1;
    }

    public String getId_app_2() {
        return id_app_2;
    }

    public void setId_app_2(String id_app_2) {
        this.id_app_2 = id_app_2;
    }

    public String getId_app_3() {
        return id_app_3;
    }

    public void setId_app_3(String id_app_3) {
        this.id_app_3 = id_app_3;
    }

    public String getId_app_4() {
        return id_app_4;
    }

    public void setId_app_4(String id_app_4) {
        this.id_app_4 = id_app_4;
    }

    public String getId_app_5() {
        return id_app_5;
    }

    public void setId_app_5(String id_app_5) {
        this.id_app_5 = id_app_5;
    }

    public String getId_app_6() {
        return id_app_6;
    }

    public void setId_app_6(String id_app_6) {
        this.id_app_6 = id_app_6;
    }

    public String getId_app_7() {
        return id_app_7;
    }

    public void setId_app_7(String id_app_7) {
        this.id_app_7 = id_app_7;
    }

    public String getId_app_8() {
        return id_app_8;
    }

    public void setId_app_8(String id_app_8) {
        this.id_app_8 = id_app_8;
    }

    public String getId_app_9() {
        return id_app_9;
    }

    public void setId_app_9(String id_app_9) {
        this.id_app_9 = id_app_9;
    }

    public String getId_app_10() {
        return id_app_10;
    }

    public void setId_app_10(String id_app_10) {
        this.id_app_10 = id_app_10;
    }

    public String getId_app_11() {
        return id_app_11;
    }

    public void setId_app_11(String id_app_11) {
        this.id_app_11 = id_app_11;
    }

    public String getId_app_12() {
        return id_app_12;
    }

    public void setId_app_12(String id_app_12) {
        this.id_app_12 = id_app_12;
    }

    public String getId_app_13() {
        return id_app_13;
    }

    public void setId_app_13(String id_app_13) {
        this.id_app_13 = id_app_13;
    }

    public String getId_app_14() {
        return id_app_14;
    }

    public void setId_app_14(String id_app_14) {
        this.id_app_14 = id_app_14;
    }

    public String getId_app_15() {
        return id_app_15;
    }

    public void setId_app_15(String id_app_15) {
        this.id_app_15 = id_app_15;
    }

    public String getId_app_16() {
        return id_app_16;
    }

    public void setId_app_16(String id_app_16) {
        this.id_app_16 = id_app_16;
    }

    public String getId_app_17() {
        return id_app_17;
    }

    public void setId_app_17(String id_app_17) {
        this.id_app_17 = id_app_17;
    }

    public String getId_app_18() {
        return id_app_18;
    }

    public void setId_app_18(String id_app_18) {
        this.id_app_18 = id_app_18;
    }

    public String getId_app_19() {
        return id_app_19;
    }

    public void setId_app_19(String id_app_19) {
        this.id_app_19 = id_app_19;
    }

    public String getId_app_20() {
        return id_app_20;
    }

    public void setId_app_20(String id_app_20) {
        this.id_app_20 = id_app_20;
    }

    public String getId_app_21() {
        return id_app_21;
    }

    public void setId_app_21(String id_app_21) {
        this.id_app_21 = id_app_21;
    }

    public String getId_app_22() {
        return id_app_22;
    }

    public void setId_app_22(String id_app_22) {
        this.id_app_22 = id_app_22;
    }

    public String getId_app_23() {
        return id_app_23;
    }

    public void setId_app_23(String id_app_23) {
        this.id_app_23 = id_app_23;
    }

    public String getId_app_24() {
        return id_app_24;
    }

    public void setId_app_24(String id_app_24) {
        this.id_app_24 = id_app_24;
    }

    public String getId_app_25() {
        return id_app_25;
    }

    public void setId_app_25(String id_app_25) {
        this.id_app_25 = id_app_25;
    }

    public String getId_app_27() {
        return id_app_27;
    }

    public void setId_app_27(String id_app_27) {
        this.id_app_27 = id_app_27;
    }

    public String getId_app_28() {
        return id_app_28;
    }

    public void setId_app_28(String id_app_28) {
        this.id_app_28 = id_app_28;
    }

    public String getId_app_29() {
        return id_app_29;
    }

    public void setId_app_29(String id_app_29) {
        this.id_app_29 = id_app_29;
    }

    public String getId_app_30() {
        return id_app_30;
    }

    public void setId_app_30(String id_app_30) {
        this.id_app_30 = id_app_30;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
