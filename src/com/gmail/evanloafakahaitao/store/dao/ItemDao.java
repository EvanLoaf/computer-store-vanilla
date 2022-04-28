package com.gmail.evanloafakahaitao.store.dao;

import com.gmail.evanloafakahaitao.store.dao.model.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemDao {

    int save(Connection connection, Item item) throws SQLException;

    List<Item> findAll(Connection connection);

    Item findByVendorCode(Connection connection, Long vendorCode);

    Item findById(Connection connection, Long id);
}
