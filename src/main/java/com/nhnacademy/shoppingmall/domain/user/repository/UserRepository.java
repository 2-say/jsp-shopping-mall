package com.nhnacademy.shoppingmall.domain.user.repository;

import com.nhnacademy.shoppingmall.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);
    Optional<User> findById(String userId);
    int save(User user);
    int deleteByUserId(String userId);
    int update(User user);
    int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt);
    int countByUserId(String userId);
}
