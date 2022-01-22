package com.bk.golovotespring.service;

import com.bk.golovotespring.entity.Candidate;

import java.util.List;

public interface CandidateService {

    List<Candidate> findAll();

    Candidate findCandidateById(int id);
}
