package com.bk.golovotespring.service;

import com.bk.golovotespring.entity.UserVote;

import java.util.List;

public interface UserVoteService {

    void save(UserVote userVote);

    List<UserVote> findUserVoteByAccount_IdAccount(int idAccount);
}
