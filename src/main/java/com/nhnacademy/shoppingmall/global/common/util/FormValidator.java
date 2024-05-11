package com.nhnacademy.shoppingmall.global.common.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FormValidator {

    public static boolean isNumeric(String target) {
        try {
            Long.parseLong(target);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Long stringToLong(String target) {
        if (isNumeric(target)) {
            return Long.parseLong(target);
        } else {
            log.error("Error parse String to Long {}", target);
            return null;
//            throw new IllegalArgumentException("Error parse String to Long " + target);
        }
    }

    public static Integer stringToInteger(String target) {
        if (isNumeric(target)) {
            return Integer.parseInt(target);
        } else {
            log.error("Error parse String to Integer {}", target);
            return null;
//            throw new IllegalArgumentException("Error parse String to Integer " + target);
        }
    }
}
