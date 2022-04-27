package com.gmail.evanloafakahaitao.store.dao.impl;

import com.gmail.evanloafakahaitao.store.dao.OrderDao;
import com.gmail.evanloafakahaitao.store.dao.model.Order;
import com.gmail.evanloafakahaitao.store.dao.util.OrderConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private OrderConverter orderConverter = new OrderConverter();

    @Override
    public int save(Connection connection, Order order) {
        int changedRows = 0;
        String sql =
                "INSERT INTO \n" +
                "   `order` (order_code, user_id, item_id, quantity) \n" +
                "VALUES \n" +
                "   (?, ?, ?, ?);\n";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, order.getOrderCode());
            preparedStatement.setLong(2, order.getUser().getId());
            preparedStatement.setLong(3, order.getItem().getId());
            preparedStatement.setInt(4, order.getQuantity());
            changedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving Order into DB");
            e.printStackTrace();
        }
        return changedRows;
    }

    @Override
    public List<Order> findByUserId(Connection connection, Long id) {
        List<Order> orders = new ArrayList<>();
        String sql =
                "SELECT \n" +
                "   * \n" +
                "FROM \n" +
                "   `order` \n" +
                "WHERE \n" +
                "   `order`.id = ?;\n";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = orderConverter.toOrder(resultSet);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Order by User id");
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public int delete(Connection connection, String orderCode) {
        int changedRows = 0;
        String sql =
                "DELETE FROM \n" +
                "   `order` \n" +
                "WHERE \n" +
                "   `order`.order_code = ?;\n";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, orderCode);
            changedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting Order from DB");
            e.printStackTrace();
        }
        return changedRows;
    }
}
