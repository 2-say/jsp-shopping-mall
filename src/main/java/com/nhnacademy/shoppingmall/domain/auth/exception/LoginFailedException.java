package com.nhnacademy.shoppingmall.domain.auth.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String userId) {
        super(String.format("login Failed", userId));
    }
}
