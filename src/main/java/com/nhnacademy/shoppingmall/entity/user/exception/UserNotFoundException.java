package com.nhnacademy.shoppingmall.entity.user.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String userId){
        super(String.format("user not found:"+userId));
    }
}
