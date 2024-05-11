package com.nhnacademy.shoppingmall.domain.order.service;

import com.nhnacademy.shoppingmall.domain.order.domain.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    OrderDetail getOrderDetail(int orderDetailId);

    List<OrderDetail> getAllOrderDetails();

    void saveOrderDetail(OrderDetail orderDetail);

    void updateOrderDetail(OrderDetail orderDetail);

    void deleteOrderDetail(int orderDetailId);

}
