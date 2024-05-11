package com.nhnacademy.shoppingmall.domain.order.repository;

import com.nhnacademy.shoppingmall.global.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.domain.order.domain.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public void save(Order order) {
        String sql = "INSERT INTO `order` (product_id, user_id, order_detail_id) VALUES (?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, order.getProductId());
            pstm.setString(2, order.getUserId());
            pstm.setInt(3, order.getOrderDetailId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order findById(int order_detail_id) {
        String sql = "SELECT * FROM `order` WHERE order_detail_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        Order order = null;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, order_detail_id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                order = new Order(
                        rs.getInt("product_id"),
                        rs.getString("user_id"),
                        rs.getInt("order_detail_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;
    }

    @Override
    public void update(Order order) {
        String sql = "UPDATE `order` SET product_id = ?, user_id = ?, order_detail_id = ? WHERE order_detail_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, order.getProductId());
            pstm.setString(2, order.getUserId());
            pstm.setInt(3, order.getOrderDetailId());
            pstm.setInt(4, order.getOrderDetailId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int order_detail_id) {
        String sql = "DELETE FROM `order` WHERE order_detail_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, order_detail_id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findByUserId(String userId) {
        String sql = "SELECT * FROM `order` WHERE user_id = ? ;";
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userId);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("product_id"),
                        rs.getString("user_id"),
                        rs.getInt("order_detail_id"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

}
