package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*todo#3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "SELECT * FROM users WHERE user_id=? and user_password = ?";
        log.debug("sql:{}", sql);

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userId);
            pstm.setString(2, userPassword);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("user_id")
                        , rs.getString("user_name")
                        , rs.getString("user_password")
                        , rs.getString("user_birth")
                        , User.Auth.valueOf(rs.getString("user_auth"))
                        , rs.getInt("user_point")
                        , Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null
                        , Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        String sql = "SELECT * FROM users WHERE user_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("user_id")
                        , rs.getString("user_name")
                        , rs.getString("user_password")
                        , rs.getString("user_birth")
                        , User.Auth.valueOf(rs.getString("user_auth"))
                        , rs.getInt("user_point")
                        , Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null
                        , Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.

        countByUserId(user.getUserId());
        String sql = "INSERT INTO users (user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at) VALUES(?, ?, ?, ? , ?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, user.getUserId());
            pstm.setString(2, user.getUserName());
            pstm.setString(3, user.getUserPassword());
            pstm.setString(4, user.getUserBirth());
            pstm.setString(5, user.getUserAuth().name());
            pstm.setInt(6, user.getUserPoint());
            pstm.setString(7, user.getCreatedAt().toString());

            if(user.getLatestLoginAt() == null)
                pstm.setString(8,null);
            else
                pstm.setString(8, user.getLatestLoginAt().toString());

            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.
        if (countByUserId(userId) == 0) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new IllegalArgumentException("Not exist userId");
        }

        String sql = "DELETE FROM users WHERE user_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userId);
            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(User user) {
        //todo#3-5 회원수정, executeUpdate()을 반환합니다.
        if (countByUserId(user.getUserId()) == 0) {
            throw new RuntimeException("Not exist user");
        }

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE users SET user_name = ?, user_password =?, user_birth= ?,user_auth=?, user_point=? WHERE user_id = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, user.getUserName());
            pstm.setString(2, user.getUserPassword());
            pstm.setString(3, user.getUserBirth());
            pstm.setString(4, user.getUserAuth().name());
            pstm.setInt(5, user.getUserPoint());
            pstm.setString(6, user.getUserId());
            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        //todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        if (countByUserId(userId) == 0) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("Not exist user");
        }

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE users SET latest_login_at = ? WHERE user_id = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, latestLoginAt.toString());
            pstm.setString(2, userId);
            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE user_id =?) as cnt";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int cnt = Integer.parseInt(rs.getString("cnt"));
                if (cnt > 1) throw new RuntimeException("Duplicate user");
                return cnt;
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException(e);
        }
        return 0;
    }
}
