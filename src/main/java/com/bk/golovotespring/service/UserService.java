package com.bk.golovotespring.service;

import com.bk.golovotespring.entity.User;

public interface UserService {

    User findUserByAccount(String account);

}
