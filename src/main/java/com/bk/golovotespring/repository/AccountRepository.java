package com.bk.golovotespring.repository;

import com.bk.golovotespring.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByUsername(String username);
}
