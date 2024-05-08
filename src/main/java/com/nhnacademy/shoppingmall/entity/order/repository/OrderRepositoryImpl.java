package com.nhnacademy.shoppingmall.entity.order.repository;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.entity.order.domain.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderRepositoryImpl {

    public void save(Order order) {
        String sql = "INSERT INTO orders (user_id, product_id, total_price, order_comment, order_rdate, address, addressee, phone) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, order.getUserId());
            pstm.setInt(2, order.getProductId());
            pstm.setInt(3, order.getTotalPrice());
            pstm.setString(4, order.getComment());
            pstm.setTimestamp(5, Timestamp.valueOf(order.getRDate()));
            pstm.setString(6, order.getAddress());
            pstm.setString(7, order.getAddressee());
            pstm.setString(8, order.getPhone());

            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
