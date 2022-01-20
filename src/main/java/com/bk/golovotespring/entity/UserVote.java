package com.bk.golovotespring.entity;

import javax.persistence.*;

@Entity
@Table(name = "uservote")
public class UserVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne()
    @JoinColumn(name="id_account", nullable=false)
    private Account account;

    @ManyToOne()
    @JoinColumn(name="id_position", nullable=false)
    private Position position;

    @ManyToOne()
    @JoinColumn(name="id_candidate", nullable=false)
    private Candidate candidate;


    public UserVote() {
    }

    public UserVote(Account account, Position position, Candidate candidate) {
        this.account = account;
        this.position = position;
        this.candidate = candidate;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
