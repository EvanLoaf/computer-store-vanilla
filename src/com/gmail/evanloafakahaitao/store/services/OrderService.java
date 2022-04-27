package com.gmail.evanloafakahaitao.store.services;

import com.gmail.evanloafakahaitao.store.dao.model.Order;

import java.util.List;

public interface OrderService {
    //TODO remember to generate UUID order code in service impl
    int save(Order order);

    List<Order> findByUserId(Long id);

    int delete(String orderCode);
}
