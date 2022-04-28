package com.gmail.evanloafakahaitao.store.dao;

import com.gmail.evanloafakahaitao.store.dao.model.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    int save(Connection connection, Order order) throws SQLException;

    List<Order> findByUserId(Connection connection, Long id);

    int delete(Connection connection, String orderCode) throws SQLException;
}
