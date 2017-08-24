/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keycloak.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

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
    , @NamedQuery(name = "logUser.findByUserId", query = "SELECT t FROM logUser t WHERE t.user_id = :user_id")
    , @NamedQuery(name = "logUser.findByUsername", query = "SELECT t FROM logUser t WHERE t.username = :username")})
public class logUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_users_log_id_seq")
    @SequenceGenerator(name = "t_users_log_id_seq", sequenceName = "t_users_log_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long user_id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "flag", nullable = false, columnDefinition = "boolean default false")
    private boolean flag;
    @Column(name = "oper_type", length = 1)
    private String oper_type;
    @Column(name = "date_oper", nullable = false, columnDefinition = "timestamp without time zone DEFAULT CURRENT_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date_oper;
    @Column(name = "send_count", nullable = false, columnDefinition = "integer DEFAULT 0")
    private Integer send_count;

    /**
     *
     */
    public logUser() {
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
    public Long getUser_id() {
        return user_id;
    }

    /**
     *
     * @param user_id
     */
    public void setUser_id(Long user_id) {
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
        return "logUser{" + "id=" + id + ", user_id=" + user_id + ", username=" + username + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate_oper() {
        return date_oper;
    }

    public void setDate_oper(Date date_oper) {
        this.date_oper = date_oper;
    }

    public Integer getSend_count() {
        return send_count;
    }

    public void setSend_count(Integer send_count) {
        this.send_count = send_count;
    }
}
