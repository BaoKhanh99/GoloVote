package com.bk.golovotespring.repository;

import com.bk.golovotespring.entity.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBlockRepository extends JpaRepository<UserBlock, Integer> {

    UserBlock findUserBlockByAccount_IdAccount(int idUser);

    UserBlock findFirstByOrderByIdAsc();

}