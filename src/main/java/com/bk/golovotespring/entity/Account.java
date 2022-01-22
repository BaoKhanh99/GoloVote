package com.bk.golovotespring.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAccount;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @OneToMany(mappedBy = "account")
    private Set<UserVote> userVotes;

    @OneToOne(mappedBy = "account")
    private UserBlock userBlock;

    @OneToMany(mappedBy = "account")
    @JsonBackReference
    private Set<AccountRole> accountRoleList;

    public Account() {
    }

    public Account(Integer idAccount) {
        this.idAccount = idAccount;

    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AccountRole> getAccountRoleList() {
        return accountRoleList;
    }

    public void setAccountRoleList(Set<AccountRole> accountRoleList) {
        this.accountRoleList = accountRoleList;
    }

    public String getName() {
        return displayName;
    }

    public void setName(String name) {
        this.displayName = name;
    }

    public Set<UserVote> getUserVotes() {
        return userVotes;
    }

    public void setUserVotes(Set<UserVote> userVotes) {
        this.userVotes = userVotes;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
