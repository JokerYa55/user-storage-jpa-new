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
    @Column(name = "elk_id", unique = true, nullable = true)
    private String elk_id;
    @Column(name = "elk_b2b_id", unique = true, nullable = true)
    private String elk_b2b_id;
    @Column(name = "hash_type", nullable = true)
    private String hesh_type;
    @Column(name = "hash", nullable = true)
    private String hash;
    

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

    public String getElk_id() {
        return elk_id;
    }

    public void setElk_id(String elk_id) {
        this.elk_id = elk_id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        //this.hash = keycloak.storage.util.hashUtil.sha1(this.password);
    }

    public String getElk_b2b_id() {
        return elk_b2b_id;
    }

    public void setElk_b2b_id(String elk_b2b_id) {
        this.elk_b2b_id = elk_b2b_id;
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

}
