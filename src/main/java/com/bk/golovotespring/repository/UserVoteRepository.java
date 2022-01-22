package com.bk.golovotespring.repository;

import com.bk.golovotespring.entity.TotalVote;
import com.bk.golovotespring.entity.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVote, Integer> {

    List<UserVote> findUserVoteByAccount_IdAccount(int idAccount);

    @Query(value = "SELECT *, COUNT(*) AS total from (select id_candidate as idCandidate , id_position as idPosition, name as candidateName, position_name as positionName from uservote\n" +
            "inner join candidates on uservote.id_candidate = candidates.id inner join position on uservote.id_position = position.id" +
            " where id_position =?1) as a\n" +
            "GROUP BY idCandidate", nativeQuery = true)
    List<TotalVote> findAllTotalVoteByPosition(int id_position);
}