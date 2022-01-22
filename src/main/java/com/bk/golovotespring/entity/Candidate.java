package com.bk.golovotespring.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "link")
    private String link;


    @OneToMany(mappedBy = "candidate")
    private Set<UserVote> userVotes;

    public Candidate(int id) {
        this.id = id;

    }
    public Candidate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<UserVote> getUserVotes() {
        return userVotes;
    }

    public void setUserVotes(Set<UserVote> userVotes) {
        this.userVotes = userVotes;
    }

}

