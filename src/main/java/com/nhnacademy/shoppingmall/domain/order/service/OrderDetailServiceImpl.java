package com.nhnacademy.shoppingmall.domain.order.service;

import com.nhnacademy.shoppingmall.domain.order.domain.OrderDetail;
import com.nhnacademy.shoppingmall.domain.order.repository.OrderDetailRepository;
import java.util.List;

public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public OrderDetail getOrderDetail(int orderDetailId) {
        return orderDetailRepository.findById(orderDetailId);
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public void updateOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.update(orderDetail);
    }

    @Override
    public void deleteOrderDetail(int orderDetailId) {
        orderDetailRepository.delete(orderDetailId);
    }


}
