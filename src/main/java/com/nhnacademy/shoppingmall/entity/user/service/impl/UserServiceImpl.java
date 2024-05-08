package com.nhnacademy.shoppingmall.entity.user.service.impl;

import com.nhnacademy.shoppingmall.entity.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.entity.user.service.UserService;
import com.nhnacademy.shoppingmall.entity.user.domain.User;
import com.nhnacademy.shoppingmall.entity.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.entity.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId) {
        //todo4-1 회원조회
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }

    @Override
    public void saveUser(User user) {
        //todo4-2 회원등록
        if (userRepository.countByUserId(user.getUserId()) == 0)
            userRepository.save(user);
        else throw new UserAlreadyExistsException(user.getUserId());
    }

    @Override
    public void updateUser(User user) {
        //todo4-3 회원수정
        if (userRepository.countByUserId(user.getUserId()) == 0) {
            throw new UserNotFoundException(user.getUserId());
        }
        userRepository.update(user);
    }

    @Override
    public void deleteUser(String userId) {
        //todo4-4 회원삭제
        if (userRepository.countByUserId(userId) == 0) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteByUserId(userId);
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //todo4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        Optional<User> userOptional = userRepository.findByUserIdAndUserPassword(userId, userPassword);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        User user = userOptional.get();
        userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
        return user;
    }

    @Override
    public void decreasePoint(String userId, int amount) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        User user = userOptional.get();
        int userPoint = user.getUserPoint();

        if(userPoint < amount) {
            throw new IllegalArgumentException("포인트 잔액이 부족합니다");
        }

        user.setUserPoint(userPoint - amount);

        log.info("user point : {}" , user.getUserPoint());
        userRepository.update(user);
    }
}
