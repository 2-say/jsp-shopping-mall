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
}
