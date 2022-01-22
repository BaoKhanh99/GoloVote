package com.bk.golovotespring.service.impl;

import com.bk.golovotespring.entity.TotalVote;
import com.bk.golovotespring.entity.UserVote;
import com.bk.golovotespring.repository.UserVoteRepository;
import com.bk.golovotespring.service.UserVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserVoteServiceImpl implements UserVoteService {

    @Autowired
    UserVoteRepository userVoteRepository;

    @Override
    public void save(UserVote userVote) {
        userVoteRepository.save(userVote);
    }

    public List<UserVote> findUserVoteByAccount_IdAccount(int idAccount){

        return userVoteRepository.findUserVoteByAccount_IdAccount(idAccount);
    }

    @Override
    public List<TotalVote> findAllTotalVoteByPosition(int idPosition) {
        return userVoteRepository.findAllTotalVoteByPosition(idPosition);
    }

    @Override
    public UserVote findTopByAccount_IdAccountAndPositionId(int idUser, int idPosition) {
        return userVoteRepository.findTopByAccount_IdAccountAndPositionId(idUser, idPosition);
    }

    @Override
    public List<TotalVote> findAllTotalVote() {
        return userVoteRepository.findAllTotalVote();
    }

    @Override
    public void delete(UserVote userVote) {
        userVoteRepository.delete(userVote);
    }


}
