package com.gmail.evanloafakahaitao.store.services;

import com.gmail.evanloafakahaitao.store.dao.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByEmail(String email);

    int update(User newUser);
}
