package com.nhnacademy.shoppingmall.point.service;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;

public class PointServiceImpl {

    private final UserRepository userRepository;

    public PointServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void chargePoint(int amount, User user) {
        if (userRepository.countByUserId(user.getUserId()) == 0) {
            throw new UserNotFoundException(user.getUserId());
        }
        user.setUserPoint(user.getUserPoint() + amount);
        userRepository.update(user);
    }

}
