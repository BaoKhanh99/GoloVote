package com.bk.golovotespring.entity;

import javax.persistence.*;

@Entity
@Table(name = "userblock")
public class UserBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "hashblock")
    private String hash;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_account", nullable=false)
    private Account account;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_block", nullable=false)
    private Blockchain blockchain;

    public UserBlock() {
    }

    public UserBlock(String hash, Account account, Blockchain blockchain) {
        this.hash = hash;
        this.account = account;
        this.blockchain = blockchain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
