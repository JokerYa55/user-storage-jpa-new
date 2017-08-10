package org.keycloak.examples.storage.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
        @NamedQuery(name = "getAllUsers", query = "select u from UserEntity u")
    ,
        @NamedQuery(name = "searchForUser", query = "select u from UserEntity u where "
            + "( lower(u.username) like :search or u.email like :search ) order by u.username"),})
@Entity
@Table(name = "t_users")
public class UserEntity {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "elk_id")
    private String elk_id;
    @Column(name = "elk_b2b_id")
    private String elk_b2b_id;
    @Column(name = "hash_type")
    private String hesh_type;
    @Column(name = "hash")
    private String hash;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        this.password = password;
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
        this.hash = hash;
    }

}
