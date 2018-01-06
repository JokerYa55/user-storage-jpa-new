/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keycloak.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vasil
 */
@Entity
@Table(name = "t_users_auth_sms_code")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersAuthSmsCode.findAll", query = "SELECT t FROM UsersAuthSmsCode t")
    , @NamedQuery(name = "UsersAuthSmsCode.findById", query = "SELECT t FROM UsersAuthSmsCode t WHERE t.id = :id")
    , @NamedQuery(name = "UsersAuthSmsCode.findByDateCode", query = "SELECT t FROM UsersAuthSmsCode t WHERE t.dateCode = :dateCode")
    , @NamedQuery(name = "UsersAuthSmsCode.findByCode", query = "SELECT t FROM UsersAuthSmsCode t WHERE t.code = :code")
    , @NamedQuery(name = "UsersAuthSmsCode.findByStatus", query = "SELECT t FROM UsersAuthSmsCode t WHERE t.status = :status")})
public class UsersAuthSmsCode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "date_code")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCode;
    @Column(name = "code")
    private Integer code;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private UserEntity userId;

    public UsersAuthSmsCode() {
    }

    public UsersAuthSmsCode(Long id) {
        this.id = id;
    }

    public UsersAuthSmsCode(Long id, Date dateCode, boolean status) {
        this.id = id;
        this.dateCode = dateCode;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCode() {
        return dateCode;
    }

    public void setDateCode(Date dateCode) {
        this.dateCode = dateCode;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersAuthSmsCode)) {
            return false;
        }
        UsersAuthSmsCode other = (UsersAuthSmsCode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.keycloak.sms.beans.TUsersAuthSmsCode[ id=" + id + " ]";
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

}
