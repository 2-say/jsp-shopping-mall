package com.nhnacademy.shoppingmall.domain.user.service;

import com.nhnacademy.shoppingmall.domain.user.domain.User;

public interface UserService {

    User getUser(String userId);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(String userId);

    User doLogin(String userId, String userPassword);

    void decreasePoint(String userId, int amount);

}
