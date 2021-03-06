package com.bk.golovotespring.repository;

import com.bk.golovotespring.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

    List<Candidate> findAll();

    Candidate findCandidateById(int id);
}