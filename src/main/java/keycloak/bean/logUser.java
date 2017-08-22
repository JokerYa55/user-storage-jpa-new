/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keycloak.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author vasil
 */
@Entity
@Table(name = "t_users_log")
@NamedQueries({
    @NamedQuery(name = "logUser.findAll", query = "SELECT t FROM logUser t")
    , @NamedQuery(name = "logUser.findById", query = "SELECT t FROM logUser t WHERE t.id = :id")
    , @NamedQuery(name = "logUser.findByFlag", query = "SELECT t FROM logUser t WHERE t.flag = :flag")
    , @NamedQuery(name = "logUser.findByPassword", query = "SELECT t FROM logUser t WHERE t.password = :password")
    , @NamedQuery(name = "logUser.findByUserId", query = "SELECT t FROM logUser t WHERE t.user_id = :user_id")
    , @NamedQuery(name = "logUser.findByUsername", query = "SELECT t FROM logUser t WHERE t.username = :username")})
public class logUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_users_log_id_seq")
    @SequenceGenerator(name = "t_users_log_id_seq", sequenceName = "t_users_log_id_seq", allocationSize = 1)
    int id;
    @Column(name = "user_id", nullable = false)
    private String user_id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "flag", nullable = false, columnDefinition = "boolean default false")
    private boolean flag;
    @Column(name = "oper_type", length = 1)
    private String oper_type;

    /**
     *
     */
    public logUser() {
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
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
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     *
     * @param user_id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     *
     * @return
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     *
     * @param flag
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     *
     * @return
     */
    public String getOper_type() {
        return oper_type;
    }

    /**
     *
     * @param oper_type
     */
    public void setOper_type(String oper_type) {
        this.oper_type = oper_type;
    }

    @Override
    public String toString() {
        return "logUser{" + "id=" + id + ", user_id=" + user_id + ", username=" + username + ", password=" + password + '}';
    }
}
