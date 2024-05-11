package com.nhnacademy.shoppingmall.domain.cart.repository;

import com.nhnacademy.shoppingmall.domain.cart.domain.Cart;
import com.nhnacademy.shoppingmall.global.common.mvc.transaction.DbConnectionThreadLocal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartRepositoryImpl implements CartRepository {

    @Override
    public int saveNotHaveCartId(Cart cart) {
        String sql = "INSERT INTO cart (cart_product_quantity, product_id, cart_rdate) VALUES (?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();
        int generatedKey = -1; // 기본적으로 에러를 나타내는 값

        try {
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, cart.getCartProductQuantity());
            pstm.setInt(2, cart.getProductId());
            pstm.setTimestamp(3, Timestamp.valueOf(cart.getRDate()));
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1); // 첫 번째 칼럼의 값, 즉 cart_id 값을 가져옴
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return generatedKey;
    }

    @Override
    public void save(Cart cart) {
        String sql = "INSERT INTO cart (cart_id, cart_product_quantity, product_id, cart_rdate) VALUES (? ,?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, cart.getCartId());
            pstm.setInt(2, cart.getCartProductQuantity());
            pstm.setInt(3, cart.getProductId());
            pstm.setTimestamp(4, Timestamp.valueOf(cart.getRDate()));
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int cartId) {
        String sql = "DELETE FROM cart WHERE cart_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, cartId);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cart> findById(int cartId) {
        String sql = "SELECT * FROM cart WHERE cart_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<Cart> cartList = new ArrayList<>();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, cartId);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart(rs.getInt("cart_id")
                        , rs.getInt("product_id")
                        , rs.getInt("cart_product_quantity")
                        , rs.getTimestamp("cart_rdate").toLocalDateTime());
                cartList.add(cart);
            }
            return cartList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateQuantity(int cartId, int productId, int newQuantity) {
        String sql = "UPDATE cart SET cart_product_quantity = ? WHERE cart_id = ? AND product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, newQuantity);
            pstm.setInt(2, cartId);
            pstm.setInt(3, productId);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean findDuplicate(int cartId, int productId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM cart WHERE cart_id = ? AND product_id = ?) as cnt";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, cartId);
            pstm.setInt(2, productId);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("cnt") == 1;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByProductId(int productId) {
        String sql = "DELETE FROM cart WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
