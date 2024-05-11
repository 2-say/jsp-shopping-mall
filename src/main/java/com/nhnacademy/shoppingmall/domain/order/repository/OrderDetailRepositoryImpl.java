package com.nhnacademy.shoppingmall.domain.order.repository;

import com.nhnacademy.shoppingmall.global.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.domain.order.domain.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailRepositoryImpl implements OrderDetailRepository {

    @Override
    public int save(OrderDetail orderDetail) {
        String sql = "INSERT INTO order_detail (order_detail_quantity, order_detail_price, address, addressee, phone, order_comment, order_rdate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();
        int generatedId = -1; // 초기값 설정

        try {
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, orderDetail.getQuantity());
            pstm.setInt(2, orderDetail.getTotalPrice());
            pstm.setString(3, orderDetail.getAddress());
            pstm.setString(4, orderDetail.getAddressee());
            pstm.setString(5, orderDetail.getPhone());
            pstm.setString(6, orderDetail.getComment());
            pstm.setTimestamp(7, Timestamp.valueOf(orderDetail.getRDate()));
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1); // 생성된 키 값 가져오기
            } else {
                throw new SQLException("Failed to get generated key.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return generatedId;
    }



    @Override
    public OrderDetail findById(int orderDetailId) {
        String sql = "SELECT * FROM order_detail WHERE order_detail_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        OrderDetail orderDetail = null;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, orderDetailId);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                orderDetail = new OrderDetail(
                        rs.getInt("order_detail_id")
                        ,rs.getInt("order_detail_quantity")
                        ,rs.getInt("order_detail_price")
                        ,rs.getString("address")
                        ,rs.getString("addressee")
                        ,rs.getString("phone")
                        ,rs.getString("order_comment")
                        ,rs.getTimestamp("order_rdate").toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDetail;
    }

    @Override
    public List<OrderDetail> findAll() {
        String sql = "SELECT * FROM order_detail";
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<OrderDetail> orderDetails = new ArrayList<>();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail(
                        rs.getInt("order_detail_id")
                        ,rs.getInt("order_detail_quantity")
                        ,rs.getInt("order_detail_price")
                        ,rs.getString("address")
                        ,rs.getString("addressee")
                        ,rs.getString("phone")
                        ,rs.getString("order_comment")
                        ,rs.getTimestamp("order_rdate").toLocalDateTime());
                orderDetails.add(orderDetail);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderDetails;
    }

    @Override
    public void update(OrderDetail orderDetail) {
        String sql = "UPDATE order_detail SET order_detail_quantity = ?, order_detail_price = ?, " +
                "address = ?, addressee = ?, phone = ?, order_comment = ?, order_rdate = ? WHERE order_detail_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1, orderDetail.getQuantity());
            pstm.setInt(2, orderDetail.getTotalPrice());
            pstm.setString(3, orderDetail.getAddress());
            pstm.setString(4, orderDetail.getAddressee());
            pstm.setString(5, orderDetail.getPhone());
            pstm.setString(6, orderDetail.getComment());
            pstm.setTimestamp(7, Timestamp.valueOf(orderDetail.getRDate()));
            pstm.setInt(8, orderDetail.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int orderDetailId) {
        String sql = "DELETE FROM order_detail WHERE order_detail_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, orderDetailId);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
