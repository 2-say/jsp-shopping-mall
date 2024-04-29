package com.nhnacademy.shoppingmall.product.model;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.entity.Product;
import jdk.jfr.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProductDao {
    public ArrayList<Product> list() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("product_id");
                String productName = rs.getString("product_name");
                String productPrice = rs.getString("product_price");
                String productDescription = rs.getString("product_description");
                int productField = rs.getInt("product_field");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime rdate = LocalDateTime.parse(rs.getString("product_rdate"),formatter);

                String category = rs.getString("category_id");
                String imageName = rs.getString("image_name");
                String imagePath = rs.getString("image_path");
                Product product = new Product(productId, productName, productPrice, productDescription, productField, rdate, category, imageName, imagePath);
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
