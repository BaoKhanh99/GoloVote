package com.bk.golovotespring.repository;

import com.bk.golovotespring.entity.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVote, Integer> {

    List<UserVote> findUserVoteByAccount_IdAccount(int idAccount);
}