package com.nhnacademy.shoppingmall.domain.cart.service;

import com.nhnacademy.shoppingmall.domain.cart.domain.Cart;
import com.nhnacademy.shoppingmall.domain.cart.dto.CartViewDTO;
import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.domain.cart.domain.UserCart;
import com.nhnacademy.shoppingmall.domain.cart.repository.UserCartRepository;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.global.common.util.SessionConst;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private UserCartRepository userCartRepository;
    private ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, UserCartRepository userCartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userCartRepository = userCartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartViewDTO getCartView(Optional<String> userId, Optional<Integer> nonMemberCartId) {
        List<Cart> cart = getCart(userId, nonMemberCartId);
        List<CartViewDTO.ProductQuantity> products = new ArrayList<>();
        Integer cartId = null;

        for (Cart c : cart) {
            cartId = c.getCartId();
            Optional<Product> product = productRepository.findById(c.getProductId());
            products.add(new CartViewDTO.ProductQuantity(product.get(), c.getCartProductQuantity()));
        }

        return new CartViewDTO(products, cartId);
    }


    @Override
    public List<Cart> getCart(Optional<String> userId, Optional<Integer> nonMemberCartId) {
        List<Cart> cartList = null;
        if (userId.isEmpty() && nonMemberCartId.isEmpty()) return new ArrayList<>();

        //회원이 있을 경우
        if (!userId.isEmpty()) {
            if (userCartRepository.isExistsCartByUserId(userId.get())) {
                return new ArrayList<>();
            }
            Integer cartId = userCartRepository.findCartIdByUserId(userId.get());
            cartList = cartRepository.findById(cartId);
        } else {
            if (nonMemberCartId.isEmpty()) {
                return new ArrayList<>();
            }
            cartList = cartRepository.findById(nonMemberCartId.get());
        }
        return cartList;
    }


    /**
     * 테이블 정보 예시
     * <cart>
     * 1번장바구니 - 2번상품
     * 1번장바구니 - 1번상품
     *
     * <user_cart>
     * user 정보와 장바구니id 1:1
     * 1번 장바구니 - 유저아이디
     * 2번 장바구니 - null(비회원)
     */
    @Override
    public void saveCart(Integer productId, Integer selectQuantity, Optional<String> userId, Optional<Integer> nonMemberCartId, HttpSession session) {
        if (userId.isPresent()) {
            saveUserCart(productId, selectQuantity, userId.get());
        } else if (nonMemberCartId.isPresent()) {
            saveNonMemberCart(productId, selectQuantity, nonMemberCartId.get());
        } else {
            saveNonMemberNotExistCart(productId, selectQuantity, session);
        }
    }

    private void saveNonMemberNotExistCart(Integer productId, Integer selectQuantity, HttpSession session) {
        int insertCartId = cartRepository.saveNotHaveCartId(new Cart(null, productId, selectQuantity, LocalDateTime.now()));
        userCartRepository.save(new UserCart(insertCartId, null));
        session.setAttribute(SessionConst.NON_MEMBER_CART_KEY, insertCartId);
    }

    private void saveUserCart(Integer productId, Integer selectQuantity, String userId) {
        //처음 장바구니 만들었을 경우
        if (userCartRepository.isExistsCartByUserId(userId)) {
            int newCartId = cartRepository.saveNotHaveCartId(new Cart(null, productId, selectQuantity, LocalDateTime.now()));
            userCartRepository.save(new UserCart(newCartId, userId));
        } else {
            //존재하는 장바구니 찾고
            Integer cartId = userCartRepository.findCartIdByUserId(userId);
            if (cartRepository.findDuplicate(cartId, productId)) {
                cartRepository.updateQuantity(cartId, productId, selectQuantity);
            } else {
                cartRepository.save(new Cart(cartId, productId, selectQuantity, LocalDateTime.now()));
            }
        }
    }

    private void saveNonMemberCart(Integer productId, Integer selectQuantity, Integer nonMemberCartId) {
        if (findDuplicate(productId, nonMemberCartId)) {
            cartRepository.updateQuantity(nonMemberCartId, productId, selectQuantity);
        } else {
            cartRepository.save(new Cart(nonMemberCartId, productId, selectQuantity, LocalDateTime.now()));
        }
    }

    private boolean findDuplicate(Integer productId, Integer cartId) {
        return cartRepository.findDuplicate(cartId, productId);
    }

    @Override
    public void deleteCart(int cartId) {
        userCartRepository.delete(cartId);
        cartRepository.delete(cartId);
    }

    @Override
    public void updateCartProductQuantity(int cartId, int productId, int quantity) {
        cartRepository.updateQuantity(cartId, productId, quantity);
    }


}
