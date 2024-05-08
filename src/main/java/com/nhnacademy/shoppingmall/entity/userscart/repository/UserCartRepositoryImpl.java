package com.nhnacademy.shoppingmall.entity.userscart.repository;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.entity.userscart.domain.UserCart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCartRepositoryImpl implements UserCartRepository {

    @Override
    public void save(UserCart userCart) {
        String sql = "INSERT INTO users_cart (cart_id, user_id) VALUES (?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, userCart.getCartId());
            pstm.setString(2, userCart.getUserId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserCart findById(int cartId) {
        String sql = "SELECT * FROM users_cart WHERE cart_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, cartId);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                UserCart userCart = new UserCart(
                        rs.getInt("cart_id"),
                        rs.getString("user_id")
                );
                return userCart;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(UserCart userCart) {
        String sql = "UPDATE users_cart SET user_id = ? WHERE cart_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userCart.getUserId());
            pstm.setInt(2, userCart.getCartId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int cartId) {
        String sql = "DELETE FROM users_cart WHERE cart_id = ?";
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
    public boolean existsCartByCartId(int cartId) {
        String sql = "SELECT COUNT(*) FROM users_cart WHERE cart_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, cartId);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // 장바구니가 존재하면 true, 아니면 false 반환
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean existsCartByUserId(String userId) {
        String sql = "SELECT COUNT(*) FROM users_cart WHERE user_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // 장바구니가 존재하면 true, 아니면 false 반환
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Integer findCartIdByUserId(String userId) {
        String sql = "SELECT cart_id FROM users_cart WHERE user_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
