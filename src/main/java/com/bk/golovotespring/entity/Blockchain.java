package com.bk.golovotespring.entity;

import javax.persistence.*;

@Entity
@Table(name = "blockchain")
public class Blockchain {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "block", columnDefinition = "TEXT")
    private String block;

    @OneToOne(mappedBy = "blockchain")
    private UserBlock userBlock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

}
