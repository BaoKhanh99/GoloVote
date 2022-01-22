package com.bk.golovotespring.entity;

public class Ballot {
    private int idUser;
    private int idCandidate;
    private int idPosition;

    public Ballot(int idUser, int idCandidate, int idPosition) {
        this.idUser = idUser;
        this.idCandidate = idCandidate;
        this.idPosition = idPosition;
    }

    public Ballot() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCandidate() {
        return idCandidate;
    }

    public void setIdCandidate(int idCandidate) {
        this.idCandidate = idCandidate;
    }

    public int getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }
}
