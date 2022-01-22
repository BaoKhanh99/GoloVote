package com.bk.golovotespring.service;

import com.bk.golovotespring.entity.TotalVote;
import com.bk.golovotespring.entity.UserVote;

import java.util.List;

public interface UserVoteService {

    void save(UserVote userVote);

    List<UserVote> findUserVoteByAccount_IdAccount(int idAccount);

    List<TotalVote> findAllTotalVoteByPosition(int idPosition);

    List<TotalVote> findAllTotalVote();

    UserVote findTopByAccount_IdAccountAndPositionId(int idUser, int idPosition);

    void delete(UserVote userVote);
}
