package com.nhnacademy.shoppingmall.domain.order.service;

import com.nhnacademy.shoppingmall.domain.cart.dto.CartViewDTO;
import com.nhnacademy.shoppingmall.domain.cart.repository.UserCartRepository;
import com.nhnacademy.shoppingmall.domain.cart.service.CartService;
import com.nhnacademy.shoppingmall.domain.order.domain.Order;
import com.nhnacademy.shoppingmall.domain.order.domain.OrderDetail;
import com.nhnacademy.shoppingmall.domain.order.domain.OrderForm;
import com.nhnacademy.shoppingmall.domain.order.dto.OrderCompleteViewDTO;
import com.nhnacademy.shoppingmall.domain.order.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.domain.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.domain.user.service.UserService;
import com.nhnacademy.shoppingmall.global.thread.channel.RequestChannel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

class OrderServiceImplTest {

    OrderDetailRepository orderDetailRepository = Mockito.mock(OrderDetailRepository.class);
    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    UserService userService = Mockito.mock(UserService.class);
    CartService cartService = Mockito.mock(CartService.class);
    UserCartRepository userCartRepository = Mockito.mock(UserCartRepository.class);
    OrderService orderService = new OrderServiceImpl(
                orderRepository
            , orderDetailRepository
            , cartService, productRepository
            , userService, userCartRepository);

    Product testProduct = new Product(1, "testName", 10000, "test", 10, LocalDateTime.now());
    OrderForm testOrderForm = new OrderForm("testAddress"
            , "testAddressee", "testPhone", "testComment", LocalDateTime.now());
    RequestChannel channel = Mockito.mock(RequestChannel.class);

    @Test
    @DisplayName("주문 저장 정상 로직")
    void saveOrder() {
        // given
        List<CartViewDTO.ProductQuantity> testProducts = new ArrayList<>();
        testProducts.add(new CartViewDTO.ProductQuantity(testProduct, 1));
        CartViewDTO cartViewDTO = new CartViewDTO(testProducts, 1);

        Mockito.when(cartService.getCartView(Optional.of("test"), Optional.empty()))
                .thenReturn(cartViewDTO);
        Mockito.when(orderDetailRepository.save(any())).thenReturn(1);

        // when
        orderService.saveOrder(Optional.of("test"), testOrderForm, channel);


        // then
        // 주문 저장
        Mockito.verify(orderRepository, Mockito.times(1)).save(any());
        // 재고 차감
        Mockito.verify(productRepository, Mockito.times(1)).decreaseProductQuantity(testProduct.getId(), cartViewDTO.getProducts().get(0).getSelectQuantity());
        // 유저 포인트 감소
        Mockito.verify(userService, Mockito.times(1)).decreasePoint("test", 10000);
        // 장바구니 삭제
        Mockito.verify(cartService, Mockito.times(1)).deleteCart(anyInt());
        // 포인트 충전 쓰레드 시작
        try {
            Mockito.verify(channel, Mockito.times(1)).addRequest(any());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("재고 부족할 때, 주문 시 예외 발생")
    void saveOrder_out_of_stock() {
        // given
        List<CartViewDTO.ProductQuantity> testProducts = new ArrayList<>();

        //10개 존재하는데 , 11개 구매
        testProducts.add(new CartViewDTO.ProductQuantity(testProduct, 11));
        CartViewDTO cartViewDTO = new CartViewDTO(testProducts, 1);

        Mockito.when(cartService.getCartView(Optional.of("test"), Optional.empty()))
                .thenReturn(cartViewDTO);
        Mockito.when(orderDetailRepository.save(any())).thenReturn(1);

        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(Optional.of("test"), testOrderForm, channel));
    }

    @Test
    @DisplayName("주문 진행 중, 존재하는 장바구니 정보가 없을때 예외 발생")
    void saveOrder_notFound_cartId() {
        // given
        List<CartViewDTO.ProductQuantity> testProducts = new ArrayList<>();
        testProducts.add(new CartViewDTO.ProductQuantity(testProduct, 1));
        CartViewDTO cartViewDTO = new CartViewDTO(testProducts, 1);

        Mockito.when(cartService.getCartView(Optional.of("test"), Optional.empty()))
                .thenReturn(cartViewDTO);
        Mockito.when(orderDetailRepository.save(any())).thenReturn(1);

        //존재하는 장바구니 사라졌을 때
        Mockito.when(userCartRepository.findCartIdByUserId(any())).thenReturn(null);

        // when
        // then
        Assertions.assertThrows(RuntimeException.class, () -> orderService.saveOrder(Optional.of("test"), testOrderForm, channel));
    }

    @Test
    @DisplayName("주문 내역 가져오기 정상 로직")
    void getOrder() {
        // given
        String userId = "testUserId";

        //테스트 객체 생성
        OrderDetail testOrderDetail = new OrderDetail(1, 2, 20000, "testAddress", "testAddressee", "testPhone", "testComment", LocalDateTime.now());
        Product testProduct = new Product(1, "testProduct", 10000, "test", 10, LocalDateTime.now());
        List<Order> sampleOrders = new ArrayList<>();
        sampleOrders.add(new Order(1, userId, 1)); // Assuming order detail id is 1

        //위 테스트 객체를 반환하도록 설정
        Mockito.when(orderRepository.findByUserId(userId)).thenReturn(sampleOrders);
        Mockito.when(orderDetailRepository.findById(1)).thenReturn(testOrderDetail);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));

        // when
        OrderCompleteViewDTO orderCompleteViewDTO = orderService.getOrder(Optional.of(userId));

        // then
        Assertions.assertNotNull(orderCompleteViewDTO);
        Assertions.assertEquals(testOrderDetail, orderCompleteViewDTO.getOrderDetail());
        Assertions.assertEquals(1, orderCompleteViewDTO.getProducts().size());
        Assertions.assertEquals(testProduct, orderCompleteViewDTO.getProducts().get(0).getProduct());
        Assertions.assertEquals(testOrderDetail.getQuantity(), orderCompleteViewDTO.getProducts().get(0).getSelectQuantity());
        Assertions.assertEquals(testOrderDetail.getTotalPrice(), orderCompleteViewDTO.getTotalPrice());
    }

}