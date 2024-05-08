package com.nhnacademy.shoppingmall.entity.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import com.nhnacademy.shoppingmall.entity.product.repository.ProductRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    //상품 저장
    public int save(Product product) {
        String sql = "INSERT INTO product (product_name, product_price, product_description, product_field, product_rdate) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, product.getName());
            pstm.setInt(2, product.getPrice());
            pstm.setString(3, product.getDescription());
            pstm.setInt(4, product.getProductField());
            pstm.setString(5, product.getRDate().toString());

            // 쿼리 실행 및 결과 처리
            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            // 생성된 키 가져오기
            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // product_id 값 반환
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //상품 삭제
    @Override
    public int delete(int productId) {
        String sql = "DELETE FROM product WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);
            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //상품 찾기
    @Override
    public Optional<Product> findById(int productId) {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Product product = new Product(
                        rs.getInt("product_id")
                        , rs.getString("product_name")
                        , rs.getInt("product_price")
                        , rs.getString("product_description")
                        , rs.getInt("product_field")
                        , LocalDateTime.parse(rs.getString("product_rdate"), formatter));
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    //상품 업데이트
    @Override
    public void update(Product product) {
        String sql = "UPDATE product SET product_name = ?, product_price = ?, product_description = ?, product_field = ?, product_rdate = ? WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, product.getName());
            pstm.setInt(2, product.getPrice());
            pstm.setString(3, product.getDescription());
            pstm.setInt(4, product.getProductField());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = product.getRDate().format(formatter);
            pstm.setString(5, formattedDate);
            pstm.setInt(6, product.getId());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //상품 테이블 마지막 번호 찾기
    @Override
    public int findLastNumber() {
        String sql = "SELECT MAX(product.product_id) + 1 AS next_id FROM product";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("next_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    //사이즈만큼 상품 가져오기
    @Override
    public List<Product> findByPageSize(int page, int pageSize) {
        int startRow = page * pageSize;
        int endRow = startRow + pageSize;

        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product LIMIT " + String.valueOf(startRow) + "," + String.valueOf(endRow);
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

                Product product = new Product(productId, productName, productPrice, productDescription, productField, rdate);
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //카테고리 해당하는 사이즈만큼 상품 가져오기
    @Override
    public List<Product> findByPageSizeAndCategory(String categoryName, int page, int pageSize) {
        int startRow = page * pageSize;
        int endRow = startRow + pageSize;

        ArrayList<Product> list = new ArrayList<>();

        String sql = "SELECT p.* FROM product_category " +
                "JOIN nhn_academy_31.category c on c.category_id = product_category.category_id\n" +
                "JOIN nhn_academy_31.product p on p.product_id = product_category.product_id\n" +
                "WHERE c.category_name = ? LIMIT ?, ? ";

        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, categoryName);
            pstm.setInt(2, startRow);
            pstm.setInt(3, endRow);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int productPrice = rs.getInt("product_price");
                String productDescription = rs.getString("product_description");
                int productField = rs.getInt("product_field");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime rdate = LocalDateTime.parse(rs.getString("product_rdate"), formatter);

                Product product = new Product(productId, productName, productPrice, productDescription, productField, rdate);
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //상품 존재 확인
    @Override
    public boolean existByProductId(int productId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM product WHERE product_id =?) as cnt";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int cnt = Integer.parseInt(rs.getString("cnt"));
                if (cnt > 1) throw new RuntimeException("Duplicate user");
                return true;
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public void deleteOneQuantity(int productId) {
        String sql = "UPDATE product SET product_field = product_field - 1 WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Updating product_field failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countProductFieldByProductId(int productId) {
        String sql = "SELECT product_field FROM product WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("product_field");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0; // 상품 ID에 해당하는 product_field가 없을 경우 0을 반환합니다.
    }
}
