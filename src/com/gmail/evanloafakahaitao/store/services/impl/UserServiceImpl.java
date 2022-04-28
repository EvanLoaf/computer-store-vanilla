package com.gmail.evanloafakahaitao.store.services.impl;

import com.gmail.evanloafakahaitao.store.dao.UserDao;
import com.gmail.evanloafakahaitao.store.dao.connection.ConnectionService;
import com.gmail.evanloafakahaitao.store.dao.impl.UserDaoImpl;
import com.gmail.evanloafakahaitao.store.dao.model.User;
import com.gmail.evanloafakahaitao.store.services.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public int update(User user) {
        int updatedColumns = 0;
        Connection connection = ConnectionService.getInstance().getConnection();
        System.out.println("Updating user");
        try {
            connection.setAutoCommit(false);
            updatedColumns = userDao.update(connection, user);
            connection.commit();
            //TODO make sure updatedColumns counter is accurate
            System.out.printf("Updated %d columns in User%n", updatedColumns);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exc) {
                System.out.println("Error rolling back transaction");
                exc.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error setting connection auto commit to true");
                e.printStackTrace();
            }
        }
        return updatedColumns;
    }

    @Override
    public List<User> findAll() {
        List<User> users;
        Connection connection = ConnectionService.getInstance().getConnection();
        System.out.println("Retrieving all users");
        users = userDao.findAll(connection);
        System.out.printf("Retrieved users : %d%n", users.size());
        return users;
    }

    @Override
    public User findByEmail(String email) {
        User user;
        Connection connection = ConnectionService.getInstance().getConnection();
        System.out.println("Retrieving user by email");
        user = userDao.findByEmail(connection, email);
        System.out.printf("User with email %s is found : %s", email, user != null);
        return user;
    }
}
