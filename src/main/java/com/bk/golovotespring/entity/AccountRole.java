package com.bk.golovotespring.entity;

import javax.persistence.*;


@Entity(name = "accountrole")
public class AccountRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountRoleId;
    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;

    public AccountRole() {
    }

    public AccountRole(Integer accountRoleId, Account account, Role role) {
        this.accountRoleId = accountRoleId;
        this.account = account;
        this.role = role;
    }

    public Integer getAccountRoleId() {
        return accountRoleId;
    }

    public void setAccountRoleId(Integer accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}