package com.nhnacademy.shoppingmall.controller.auth.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String userId) {
        super(String.format("login Failed", userId));
    }
}
