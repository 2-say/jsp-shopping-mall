package com.nhnacademy.shoppingmall.common.util;

public final class FormValidator {

    public static boolean isNumeric(String target) {
        try {
            Integer.parseInt(target);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Integer stringToInteger(String target) {
        if (isNumeric(target)) {
            return Integer.parseInt(target);
        } else {
            throw new IllegalArgumentException("Error parse String to Integer " + target);
        }
    }
}
