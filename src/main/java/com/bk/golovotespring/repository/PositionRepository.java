package com.bk.golovotespring.repository;

import com.bk.golovotespring.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    public Position findPositionById(int id);
}