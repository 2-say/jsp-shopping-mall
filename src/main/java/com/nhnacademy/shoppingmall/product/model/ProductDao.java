package com.nhnacademy.shoppingmall.product.model;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.entity.Product;
import jdk.jfr.Category;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Slf4j
public class ProductDao {

    public ArrayList<Product> list(int page, int pageSize) {
        int startRow = page * pageSize;
        int endRow = startRow + pageSize;

        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product LIMIT " + String.valueOf(startRow) + "," + String.valueOf(endRow);
        log.info("sql = {}" ,sql);
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int productPrice = rs.getInt("product_price");
                String productDescription = rs.getString("product_description");
                int productField = rs.getInt("product_field");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime rdate = LocalDateTime.parse(rs.getString("product_rdate"), formatter);

                int category = rs.getInt("category_id");

                Product product = new Product(productId, productName, productPrice, productDescription, productField, rdate, category);
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ArrayList<Product> listCategory(String categoryName, int page, int pageSize) {
        int startRow = page * pageSize;
        int endRow = startRow + pageSize;

        ArrayList<Product> list = new ArrayList<>();

        String sql = "WITH RECURSIVE Subcategories AS (\n" +
                "  SELECT category_id\n" +
                "  FROM category\n" +
                "  WHERE category_name = ? \n" +
                "  UNION ALL\n" +
                "  SELECT c.category_id\n" +
                "  FROM category c\n" +
                "  INNER JOIN Subcategories s ON c.parent_category_id = s.category_id\n" +
                ")\n" +
                "SELECT p.* , s.category_id\n" +
                "FROM product p\n" +
                "JOIN Subcategories s ON p.category_id = s.category_id" +
                " LIMIT " + String.valueOf(startRow) + "," + String.valueOf(endRow);
                log.info("sql ={}", sql);

        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, categoryName);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int productPrice = rs.getInt("product_price");
                String productDescription = rs.getString("product_description");
                int productField = rs.getInt("product_field");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime rdate = LocalDateTime.parse(rs.getString("product_rdate"), formatter);

                int category = rs.getInt("category_id");
                Product product = new Product(productId, productName, productPrice, productDescription, productField, rdate, category);
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
