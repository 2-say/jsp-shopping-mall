package com.nhnacademy.shoppingmall.domain.cart.service;

import com.nhnacademy.shoppingmall.domain.cart.domain.Cart;
import com.nhnacademy.shoppingmall.domain.cart.dto.CartViewDTO;
import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.domain.cart.repository.UserCartRepository;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    HttpSession session = Mockito.mock(HttpSession.class);
    CartRepository cartRepository = Mockito.mock(CartRepository.class);
    UserCartRepository userCartRepository = Mockito.mock(UserCartRepository.class);
    ProductRepository productRepository = Mockito.mock(ProductRepository.class);

    Cart testCart = new Cart(1, 1, 1, LocalDateTime.now());
    Product testProduct = new Product(1, "test", 1, "test", 1, LocalDateTime.now());
    CartService cartService = new CartServiceImpl(cartRepository
            , userCartRepository
            , productRepository);


    @Test
    @DisplayName("유저 장바구니 가져오기 - 정상 로직 1")
    void getUserCartView() {
        // given
        List<Cart> testCarts = new ArrayList<>();
        testCarts.add(testCart);

        Mockito.when(userCartRepository.isExistsCartByUserId(anyString())).thenReturn(true);
        Mockito.when(userCartRepository.findCartIdByUserId(anyString())).thenReturn(1);
        Mockito.when(cartRepository.findById(1)).thenReturn(testCarts);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.ofNullable(testProduct));

        // when
        CartViewDTO cartView = cartService.getCartView(Optional.of("test"), Optional.empty());
        // then
        Assertions.assertEquals(testCart.getCartId(), cartView.getCartId());
        Assertions.assertEquals(testProduct, cartView.getProducts().get(0).getProduct());
    }

    @Test
    @DisplayName("비회원 장바구니 가져오기 - 정상 로직 2")
    void getCartView_NonMember() {
        // given
        List<Cart> testCarts = new ArrayList<>();
        testCarts.add(testCart);
        Mockito.when(cartRepository.findById(anyInt())).thenReturn(testCarts);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.ofNullable(testProduct));
        // when
        CartViewDTO cartView = cartService.getCartView(Optional.empty(), Optional.of(1));
        // then
        Assertions.assertEquals(testCart.getCartId(), cartView.getCartId());
        Assertions.assertEquals(testProduct, cartView.getProducts().get(0).getProduct());
    }

    @Test
    @DisplayName("회원 ID , 비회원 카트 정보 둘다 없으면 빈 장바구니 return")
    void getCartView_emptyUserId_AND_NonMemberCartId() {
        // given
        // when
        CartViewDTO cartView = cartService.getCartView(Optional.empty(), Optional.empty());
        // then
        Assertions.assertTrue(cartView.getProducts().isEmpty());
        Assertions.assertNull(cartView.getCartId());
    }

    @Test
    @DisplayName("회원 장바구니 저장, 이미 부여한 장바구니 ID가 있고 넣으려는 상품이 중복 되지 않았을 때 추가 로직 - 정상 로직1")
    void saveUserCart_ExistCartId_NotDuplicateProduct() {
        // given
        Mockito.when(userCartRepository.findCartIdByUserId("test")).thenReturn(1);
        Mockito.when(cartRepository.findDuplicate(anyInt(), anyInt())).thenReturn(false);
        // when
        cartService.saveCart(testProduct.getId(), 1, Optional.of("test"), Optional.empty(), session);
        // then
        verify(cartRepository, Mockito.times(1))
                .save(new Cart(1, 1,1, any()));
    }

    @Test
    @DisplayName("비회원 장바구니 저장, 이미 부여한 장바구니 ID가 있고 넣으려는 상품이 중복 되지 않았을 때 추가 로직 - 정상 로직2")
    void saveNonMemberCart_ExistCartId_NotDuplicateProduct() {
        // given
        // when
        cartService.saveCart(testProduct.getId(), 1,Optional.empty(), Optional.of(1), session);
        // then
        verify(cartRepository, Mockito.times(1))
                .save(new Cart(1, 1,1, any()));
    }

    @Test
    @DisplayName("회원 장바구니 저장, 이미 부여한 장바구니 ID가 있고 넣으려는 상품이 중복 될때 상품 수량 업데이트 로직 - 정상 로직3")
    void saveUserCart_ExistCartId_DuplicateProduct() {
        // given
        Mockito.when(cartRepository.findDuplicate(anyInt(), anyInt())).thenReturn(true);
        // when
        cartService.saveCart(testProduct.getId(), 1, Optional.of("test"), Optional.empty(), session);
        // then
        verify(cartRepository, Mockito.times(1))
                .updateQuantity(anyInt(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("비회원 장바구니 저장, 이미 부여한 장바구니 ID가 있고 넣으려는 상품이 중복 될때 상품 수량 업데이트 로직 - 정상 로직4")
    void saveNonMemberCart_ExistCartId_DuplicateProduct() {
        // given
        HttpSession session = null;
        Mockito.when(cartRepository.findDuplicate(anyInt(), anyInt())).thenReturn(true);
        // when
        cartService.saveCart(testProduct.getId(), 1, Optional.empty(), Optional.of(1), session);
        // then
        verify(cartRepository, Mockito.times(1))
                .updateQuantity(1, 1, 1);
    }

    @Test
    @DisplayName("비회원ID, 회원ID 둘다 없다면->  비회원이 처음 장바구니 생성 - 정상 로직5")
    void saveCart_NotExistUserIdAndNonMemberCartId_CreateNewNonMemberCartId() {
        // given
        Mockito.when(cartRepository.findDuplicate(anyInt(), anyInt())).thenReturn(true);
        Mockito.when(cartRepository.saveNotHaveCartId(new Cart(null, 1, 1, LocalDateTime.now()))).thenReturn(1);
        // when
        cartService.saveCart(testProduct.getId(), 1, Optional.empty(), Optional.empty(), session);
        // then
        verify(cartRepository, Mockito.times(1))
                .saveNotHaveCartId(any());
    }

    @Test
    @DisplayName("장바구니 삭제 - cartId가 null이면 로그 출력")
    void deleteCart_cartIdIsNull_logsError() {
        // Given
        // When
        cartService.deleteCart(null);
        // Then
        verify(cartRepository, never()).delete(any());
        verify(userCartRepository, never()).delete(any());
    }

    @Test
    @DisplayName("장바구니 수량 업데이트 - 모든 파라미터가 null이면 예외 발생")
    void updateCartProductQuantity_allParametersNull_throwsIllegalArgumentException() {
        // Given
        Integer cartId = null;
        Integer productId = null;
        Integer quantity = 1;

        // When, Then
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            cartService.updateCartProductQuantity(cartId, productId, quantity)
        );
    }
}