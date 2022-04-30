package com.gmail.evanloafakahaitao.store.services;

import com.gmail.evanloafakahaitao.store.dao.model.Order;

import java.util.List;

public interface OrderService {

    int save(Order order);

    List<Order> findByUserId(Long id);

    int delete(String orderCode);
}
