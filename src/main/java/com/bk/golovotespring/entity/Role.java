package com.bk.golovotespring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRole;
    private String name;
    @OneToMany(mappedBy = "role")
    @JsonBackReference
    private Set<AccountRole> accountRoleList;

    public Role() {
    }

    public Role(Integer idRole, String name, Set<AccountRole> accountRoleList) {
        this.idRole = idRole;
        this.name = name;
        this.accountRoleList = accountRoleList;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AccountRole> getAccountRoleList() {
        return accountRoleList;
    }

    public void setAccountRoleList(Set<AccountRole> accountRoleList) {
        this.accountRoleList = accountRoleList;
    }
}