package com.nhnacademy.shoppingmall.domain.order.service;

import com.nhnacademy.shoppingmall.domain.order.domain.OrderForm;
import com.nhnacademy.shoppingmall.domain.order.dto.OrderCompleteViewDTO;
import com.nhnacademy.shoppingmall.global.thread.channel.RequestChannel;

import java.util.Optional;

public interface OrderService {

    OrderCompleteViewDTO saveOrder(Optional<String> userId, OrderForm orderForm, RequestChannel requestChannel);

    OrderCompleteViewDTO getOrder(Optional<String> userId);
}
