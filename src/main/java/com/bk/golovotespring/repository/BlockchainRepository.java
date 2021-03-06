package com.bk.golovotespring.repository;

import com.bk.golovotespring.entity.Blockchain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockchainRepository extends JpaRepository<Blockchain, Integer> {

    Blockchain findByBlock(String block);
}