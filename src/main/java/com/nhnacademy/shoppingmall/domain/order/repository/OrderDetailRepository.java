package com.nhnacademy.shoppingmall.domain.order.repository;

import com.nhnacademy.shoppingmall.domain.order.domain.OrderDetail;

import java.util.List;

public interface OrderDetailRepository {
    int save(OrderDetail orderDetail);

    OrderDetail findById(int orderDetailId);

    void update(OrderDetail orderDetail);

    void delete(int orderDetailId);
}
