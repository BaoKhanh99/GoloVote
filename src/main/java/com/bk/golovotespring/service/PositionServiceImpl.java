package com.bk.golovotespring.service;

import com.bk.golovotespring.entity.Position;
import com.bk.golovotespring.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService{

    @Autowired
    PositionRepository positionRepository;

    @Override
    public Position findPositionById(int id) {
        return positionRepository.findPositionById(id);
    }
}
