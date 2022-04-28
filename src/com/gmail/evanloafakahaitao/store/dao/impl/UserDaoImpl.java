package com.gmail.evanloafakahaitao.store.dao.impl;

import com.gmail.evanloafakahaitao.store.dao.UserDao;
import com.gmail.evanloafakahaitao.store.dao.model.User;
import com.gmail.evanloafakahaitao.store.dao.util.UserConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private UserConverter userConverter = new UserConverter();

    @Override
    public List<User> findAll(Connection connection) {
        List<User> users = new ArrayList<>();
        String sql =
                "SELECT \n" +
                "   u.id, \n" +
                "   u.first_name, \n" +
                "   u.last_name, \n" +
                "   u.email, \n" +
                "   u.password, \n" +
                "   u.additional_info, \n" +
                "   u.phone_number, \n" +
                "   r.name AS role\n" +
                "FROM \n" +
                "   user AS u \n" +
                "JOIN \n" +
                "   role AS r \n" +
                "ON \n" +
                "   u.role_id = r.id;\n";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                User user = userConverter.toUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all users from DB");
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findByEmail(Connection connection, String email) {
        User user = null;
        String sql =
                "SELECT \n" +
                "   u.id, \n" +
                "   u.first_name, \n" +
                "   u.last_name, \n" +
                "   u.email, \n" +
                "   u.password, \n" +
                "   u.additional_info, \n" +
                "   u.phone_number, \n" +
                "   r.name AS role\n" +
                "FROM \n" +
                "   user AS u \n" +
                "JOIN \n" +
                "   role AS r \n" +
                "ON \n" +
                "   u.role_id = r.id \n" +
                "WHERE \n" +
                "   u.email = ?;\n";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = userConverter.toUser(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by email from DB");
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int update(Connection connection, User user) throws SQLException {
        int updatedRows = 0;
        String sql =
                "UPDATE \n" +
                "   user \n" +
                "SET \n" +
                "   ? = ? \n" +
                "WHERE \n" +
                "   user.id = ?;\n";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (user.getFirstName() != null) {
                preparedStatement.setString(1, "first_name");
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setLong(3, user.getId());
                preparedStatement.addBatch();
            }
            if (user.getLastName() != null) {
                preparedStatement.setString(1, "last_name");
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setLong(3, user.getId());
                preparedStatement.addBatch();
            }
            if (user.getEmail() != null) {
                preparedStatement.setString(1, "email");
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setLong(3, user.getId());
                preparedStatement.addBatch();
            }
            if (user.getAdditionalInfo() != null) {
                preparedStatement.setString(1, "additional_info");
                preparedStatement.setString(2, user.getAdditionalInfo());
                preparedStatement.setLong(3, user.getId());
                preparedStatement.addBatch();
            }
            if (user.getPhoneNumber() != null) {
                preparedStatement.setString(1, "phone_number");
                preparedStatement.setString(2, user.getPhoneNumber());
                preparedStatement.setLong(3, user.getId());
                preparedStatement.addBatch();
            }
            int[] rows = preparedStatement.executeBatch();
            for (int row : rows) {
                updatedRows += row;
            }
        } catch (SQLException e) {
            System.out.println("Error updating User");
            e.printStackTrace();
            throw new SQLException(e);
        }
        return updatedRows;
    }

    @Override
    public User findById(Connection connection, Long id) {
        User user = null;
        String sql =
                "SELECT \n" +
                "   u.id, \n" +
                "   u.first_name, \n" +
                "   u.last_name, \n" +
                "   u.email, \n" +
                "   u.password, \n" +
                "   u.additional_info, \n" +
                "   u.phone_number, \n" +
                "   r.name AS role\n" +
                "FROM \n" +
                "   user AS u \n" +
                "JOIN \n" +
                "   role AS r \n" +
                "ON \n" +
                "   u.role_id = r.id \n" +
                "WHERE \n" +
                "   u.id = ?;\n";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = userConverter.toUser(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by id from DB");
            e.printStackTrace();
        }
        return user;
    }
}
