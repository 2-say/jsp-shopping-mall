package com.nhnacademy.shoppingmall.domain.order.repository;

import com.nhnacademy.shoppingmall.domain.order.domain.Order;

import java.util.List;

public interface OrderRepository {
    void save(Order order);

    Order findById(int order_detail_id);

    void update(Order order);

    void delete(int order_detail_id);

    List<Order> findByUserId(String userId);
}
