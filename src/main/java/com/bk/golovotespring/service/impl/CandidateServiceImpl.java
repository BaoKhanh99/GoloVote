package com.bk.golovotespring.service.impl;

import com.bk.golovotespring.entity.Candidate;
import com.bk.golovotespring.repository.CandidateRepository;
import com.bk.golovotespring.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    CandidateRepository repository;

    public List<Candidate> findAll(){
        return repository.findAll();
    }
}
