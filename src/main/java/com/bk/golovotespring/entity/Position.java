package com.bk.golovotespring.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "position_name")
    private String PositionName;

    @OneToMany(mappedBy = "position")
    private Set<UserVote> userVotes;

    @OneToOne(mappedBy = "position")
    private Candidate candidate;

    public Position() {
    }

    public Position(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }

    public Set<UserVote> getUserVotes() {
        return userVotes;
    }

    public void setUserVotes(Set<UserVote> userVotes) {
        this.userVotes = userVotes;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
