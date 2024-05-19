package com.nhnacademy.shoppingmall.domain.order.dto;

import com.nhnacademy.shoppingmall.domain.cart.dto.CartViewDTO;
import com.nhnacademy.shoppingmall.domain.order.domain.OrderDetail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderCompleteViewDTO {
    private final OrderDetail orderDetail;
    private final List<CartViewDTO.ProductQuantity> products;
    private final Integer totalPrice;
}
