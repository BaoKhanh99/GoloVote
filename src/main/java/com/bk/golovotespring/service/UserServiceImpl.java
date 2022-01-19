package com.bk.golovotespring.service;

import com.bk.golovotespring.entity.User;
import com.bk.golovotespring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements  UserService{

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        repository = userRepository;
    }

    @Override
    public User findUserByAccount(String account) {
        return repository.findUserByAccount(account);
    }
}
