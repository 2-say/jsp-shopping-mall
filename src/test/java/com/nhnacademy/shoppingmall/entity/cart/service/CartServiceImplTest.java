package com.nhnacademy.shoppingmall.entity.cart.service;

import com.nhnacademy.shoppingmall.entity.cart.domain.Cart;
import com.nhnacademy.shoppingmall.entity.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.entity.userscart.repository.UserCartRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    CartRepository cartRepository = Mockito.mock(CartRepository.class);
    UserCartRepository userCartRepository = Mockito.mock(UserCartRepository.class);
    CartService cartService = new CartServiceImpl(cartRepository, userCartRepository);

    Cart testCart = new Cart(1, 1, 10, LocalDateTime.now());
    Cart testNothaveCartIdCart = new Cart(null, 1, 1, LocalDateTime.now());


    @Test
    @DisplayName("장바구니 ID로 장바구니(cart) 찾기")
    void getCart() {
        // given
        List<Cart> cartList = new ArrayList<>();
        cartList.add(testCart);
        Mockito.when(cartRepository.findById(anyInt())).thenReturn(cartList);
        Mockito.when(cartRepository.existsById(anyInt())).thenReturn(true);
        // when
        cartService.findByCartId(testCart.getCartId());
        // then
        Mockito.verify(cartRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verify(cartRepository, Mockito.times(1)).existsById(anyInt());
    }

    @Test
    @DisplayName("존재하지 않는 장바구니(cart) 찾기")
    void getCart_not_found() {
        // given
        Mockito.when(cartRepository.existsById(anyInt())).thenReturn(false);
        // when
        // then
        Assertions.assertThrows(RuntimeException.class, () -> cartService.findByCartId(testCart.getCartId()));
    }


    @Test
    @DisplayName("중복 X, 장바구니가 없는 경우, 회원 장바구니 생성 테스트")
    void saveUserCart() {
        // given
        Mockito.when(cartRepository.findDuplicate(anyInt(), anyInt())).thenReturn(false);
        Cart testNothaveCartIdCart = new Cart(null, 1, 1, LocalDateTime.now());
        // when
        cartService.saveUserCart("testUser", testNothaveCartIdCart);
        // then
        Mockito.verify(cartRepository, Mockito.times(1)).saveNotHaveCartId(testNothaveCartIdCart);
    }

    @Test
    @DisplayName("중복 X, 장바구니가 존재하는 경우 회원 장바구니 추가 테스트")
    void saveUserCart_existCartId() {
        // given
        Mockito.when(cartRepository.findDuplicate(anyInt(), anyInt())).thenReturn(false);
        // when
        cartService.saveUserCart("testUser", testCart);
        // then
        Mockito.verify(cartRepository, Mockito.times(1)).save(testCart);
    }

    @Test
    @DisplayName("중복 O, 업데이트(덮어쓰기)")
    void saveUserCart_duplicateProductIdAndCartId() {
        // given
        Mockito.when(cartRepository.findDuplicate(anyInt(), anyInt())).thenReturn(true);
        // when
        cartService.saveUserCart("testUser", testCart);
        // then
        Mockito.verify(cartRepository, Mockito.times(1)).update(testCart);
    }

    @Test
    @DisplayName("중복 X, 이미 존재하는 장바구니,  비회원 장바구니 저장")
    void saveNonMemberCart_existCartId() {
        // given
        Mockito.when(cartRepository.findDuplicate(anyInt(), anyInt())).thenReturn(false);
        // when
        cartService.saveNonMemberCart(testCart);
        // then
        Mockito.verify(cartRepository, Mockito.times(1)).save(testCart);
    }

    @Test
    @DisplayName("중복 X, 장바구니 없을 때, 비회원 저장")
    void saveNonMemberCart_notExistCartId() {
        // given
        // when
        cartService.saveNonMemberCart(testNothaveCartIdCart);
        // then
        Mockito.verify(cartRepository, Mockito.times(1)).saveNotHaveCartId(testNothaveCartIdCart);
    }

    @Test
    @DisplayName("중복 O, 비회원 저장")
    void saveNonMemberCart_duplicateProductIdAndCartId() {
        // given
        Mockito.when(cartRepository.findDuplicate(anyInt(), anyInt())).thenReturn(true);
        // when
        cartService.saveNonMemberCart(testCart);
        // then
        Mockito.verify(cartRepository, Mockito.times(1)).update(testCart);
    }

    @Test
    @DisplayName("업데이트 장바구니(cart)")
    void updateCart() {
        // given
        Mockito.when(cartRepository.existsById(anyInt())).thenReturn(true);
        // when
        cartService.updateCart(testCart);
        // then
        Mockito.verify(cartRepository, Mockito.times(1)).existsById(anyInt());
    }
}