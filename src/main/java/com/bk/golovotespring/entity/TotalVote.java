package com.bk.golovotespring.entity;

public interface TotalVote {
    int getIdPosition();
    int getIdCandidate();
    String getCandidateName();
    String getPositionName();
    int getTotal();
}
