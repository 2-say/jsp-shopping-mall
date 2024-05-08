package com.nhnacademy.shoppingmall.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

import static com.nhnacademy.shoppingmall.controller.product.ProductDetailController.RECENT_PRODUCTS;

public class CookieUtils {

    // 쿠키 가져오기
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    // 쿠키 읽기
    public static Optional<String> readServletCookie(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

    // 쿠키 추가하기
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키 삭제하기
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static void AddObjectCookie(Object object,HttpServletResponse response ) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String recentProductsJson = objectMapper.writeValueAsString(object);
            String encodedRecentProductsJson = URLEncoder.encode(recentProductsJson, StandardCharsets.UTF_8.toString());
            Cookie cookie = new Cookie(RECENT_PRODUCTS, encodedRecentProductsJson);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // Cookie 유효기간 1일로 설정
            response.addCookie(cookie);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}