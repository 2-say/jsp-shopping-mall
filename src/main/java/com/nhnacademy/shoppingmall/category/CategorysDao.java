package com.nhnacademy.shoppingmall.category;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorysDao {

    public List<Categories> selectCategory() {

        String sql = "SELECT c1.category_name AS parent_name, c2.category_name AS child_name FROM category c1 LEFT JOIN category c2 ON c1.category_id = c2.parent_category_id\n" +
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
                if (currentParent == null) currentParent = parentName;

                if (currentParent.equals(parentName)) {
                    childList.add(new Category(childName));
                } else {
                    result.add(new Categories(childList, parentName));
                    currentParent = parentName;
                    childList = new ArrayList<>();
                    childList.add(new Category(childName));
                }
            }
            rs.close();
            pstm.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
