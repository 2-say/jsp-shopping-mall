package com.nhnacademy.shoppingmall.entity.cart.service;

import com.nhnacademy.shoppingmall.entity.cart.domain.Cart;
import com.nhnacademy.shoppingmall.entity.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.entity.userscart.repository.UserCartRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CartServiceImpl implements CartService {
    //TODO : 한번 정리 필요
    private CartRepository cartRepository;
    private UserCartRepository userCartRepository;

    public CartServiceImpl(CartRepository cartRepository, UserCartRepository userCartRepository) {
        this.cartRepository = cartRepository;
        this.userCartRepository = userCartRepository;
    }

    @Override
    public int saveNonMemberCart(Cart cart) {
        Integer cartId = null;

        //장바구니 이미 존재하는 경우
        if (cart.getCartId() != null) {
            //넣으려는 장바구니가 중복된다면
            if (findDuplicate(cart.getCartId(), cart.getProductId())) {
                cartRepository.update(cart);
            } else if (cart.getCartId() != null) {
                cartRepository.save(cart);
            }
            cartId = cart.getCartId();
        } else {
            cartId = cartRepository.saveNotHaveCartId(cart);
        }
        return cartId;
    }

    @Override
    public void saveUserCart(String userId, Cart cart) {
        Integer cartId = null;

        //넣으려는 장바구니가 중복된다면
        if (cart.getCartId() != null && cartRepository.findDuplicate(cartId, cart.getProductId())) {
            cartRepository.update(cart);
        } else if (cart.getCartId() != null) {
            cartRepository.save(cart);
        } else {
            //장바구니 처음 생성
            cartRepository.saveNotHaveCartId(cart);
        }
    }

    @Override
    public List<Cart> findByCartId(int cartId) {

        if (!cartRepository.existsById(cartId)) {
            throw new RuntimeException("Not Found Cart Id" + cartId);
        }

        List<Cart> cartList = cartRepository.findById(cartId);
        return cartList;
    }


    @Override
    public List<Cart> findByUserId(String userId) {
        if (!userCartRepository.existsCartByUserId(userId)) {
            return new ArrayList<>();
        }
        int cartId = userCartRepository.findCartIdByUserId(userId);

        if (!cartRepository.existsById(cartId)) {
            return new ArrayList<>();
        }

        List<Cart> cartList = cartRepository.findById(cartId);
        return cartList;
    }

    @Override
    public void updateCart(Cart cart) {
        if (!cartRepository.existsById(cart.getCartId())) {
            throw new RuntimeException("Not Found Cart Id" + cart.getCartId());
        }

        cartRepository.update(cart);
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


    @Override
    public boolean findDuplicate(int cartId, int productId) {
        return cartRepository.findDuplicate(cartId, productId);
    }


    @Override
    public boolean existsCartByUserId(String userId) {
        return userCartRepository.existsCartByUserId(userId);
    }

    @Override
    public int findCartIdByUserid(String userId) {
        return userCartRepository.findCartIdByUserId(userId);
    }
}
