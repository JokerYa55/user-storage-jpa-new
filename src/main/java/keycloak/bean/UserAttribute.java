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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author vasil
 */
@Entity
@Table(name = "t_users_attributes", indexes = {
    @Index(name = "t_users_attributes_name_idx", columnList = "name")
    ,
    @Index(name = "t_users_attribute_user_id_idx", columnList = "user_id")})
public class UserAttribute implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_users_id_seq")
    @SequenceGenerator(name = "t_users_attributes_id_seq", sequenceName = "t_users_attributes_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name", unique = false, nullable = false)
    private String name;
    @Column(name = "value", unique = false, nullable = false)
    private String value;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserEntity userId;

    public UserAttribute() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserAttribute{" + "id=" + id + ", name=" + name + ", value=" + value + ", userId=" + userId + '}';
    }

}
