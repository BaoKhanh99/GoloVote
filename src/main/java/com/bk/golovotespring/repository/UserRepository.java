package com.bk.golovotespring.repository;

import com.bk.golovotespring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByAccount(String account);
}