package com.bk.golovotespring.entity;

import javax.persistence.*;

@Entity
@Table(name = "blockchain")
public class Blockchain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "block", columnDefinition = "TEXT")
    private String block;

    @OneToOne(mappedBy = "blockchain")
    private UserBlock userBlock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Blockchain(String block) {
        this.block = block;
    }

    public Blockchain() {
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public UserBlock getUserBlock() {
        return userBlock;
    }

    public void setUserBlock(UserBlock userBlock) {
        this.userBlock = userBlock;
    }
}
