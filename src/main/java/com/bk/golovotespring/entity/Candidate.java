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

    @Column(name = "birthday")
    private String birthDay;

    @Column(name = "id_student")
    private String idStudent;

    @Column(name = "grade")
    private String grade;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Column(name = "achievement")
    private String achievement;

    @Column(name = "activity")
    private String activity;

    @Column(name = "image")
    private String image;

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

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Set<UserVote> getUserVotes() {
        return userVotes;
    }

    public void setUserVotes(Set<UserVote> userVotes) {
        this.userVotes = userVotes;
    }

}

