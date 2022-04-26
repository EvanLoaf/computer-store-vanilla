package com.gmail.evanloafakahaitao.store.dao;

import com.gmail.evanloafakahaitao.store.dao.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemDao {

    int save(Connection connection, Item item);

    List<Item> findAll(Connection connection);

    Item findByVendorCode(Connection connection, Long vendorCode);
}
