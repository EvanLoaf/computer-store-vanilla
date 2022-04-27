package com.gmail.evanloafakahaitao.store.dao;

import com.gmail.evanloafakahaitao.store.dao.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao {

    List<User> findAll(Connection connection);

    User findByEmail(Connection connection, String email);

    int update(Connection connection, User user);

    User findById(Connection connection, Long id);
}
