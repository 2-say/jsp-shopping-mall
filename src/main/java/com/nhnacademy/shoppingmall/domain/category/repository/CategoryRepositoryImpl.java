package com.nhnacademy.shoppingmall.domain.category.repository;

import com.nhnacademy.shoppingmall.global.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.domain.category.domain.Categories;
import com.nhnacademy.shoppingmall.domain.category.domain.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public List<Categories> selectCategory() {
        //전체 카테고리 조회
        String sql = "SELECT c2.category_id, c1.category_name AS parent_name, c2.category_name AS child_name FROM category c1 LEFT JOIN category c2 ON c1.category_id = c2.parent_category_id\n" +
                "WHERE c2.category_name is not null\n" +
                "ORDER BY c1.category_id, c2.category_id";

        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            String currentParent = null;
            List<Category> childList = new ArrayList<>();
            List<Categories> result = new ArrayList<>();

            while (rs.next()) {
                String parentName = rs.getString("parent_name");
                String childName = rs.getString("child_name");
                int categoryId = rs.getInt("category_id");

                if (currentParent == null) currentParent = parentName;

                if (currentParent.equals(parentName)) {
                    childList.add(new Category(categoryId, childName));
                } else {
                    result.add(new Categories(childList, currentParent));
                    currentParent = parentName;
                    childList = new ArrayList<>();
                    childList.add(new Category(categoryId, childName));
                }
            }
            rs.close();
            pstm.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    //카테고리 id로 이름 조회
    public String findById(int categoryId) {
        String sql = "SELECT category_name FROM category WHERE category_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, categoryId);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("category_name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void createCategory(String categoryName) {
        String sql = "INSERT INTO category (category_name) VALUES (?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, categoryName);
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 카테고리 정보 수정
    @Override
    public void updateCategory(String categoryId, String categoryName, String parentCategoryId) {
        String sql = "UPDATE category SET category_name=?, parent_category_id=? WHERE category_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, categoryName);
            pstm.setString(2, parentCategoryId);
            pstm.setString(3, categoryId);
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 카테고리 삭제
    @Override
    public void deleteCategory(String categoryId) {
        String sql = "DELETE FROM category WHERE category_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, categoryId);
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
