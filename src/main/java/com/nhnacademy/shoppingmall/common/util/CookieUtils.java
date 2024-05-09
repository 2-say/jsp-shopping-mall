package com.nhnacademy.shoppingmall.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class CookieUtils {
    public static final String RECENT_PRODUCTS = "recentProducts";

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

    public static boolean isCookieExists(HttpServletRequest request, String cookieName) {
        // 요청으로부터 쿠키 배열을 가져옴
        Cookie[] cookies = request.getCookies();

        // 쿠키 배열이 null이 아니고 길이가 0보다 큰 경우에만 실행
        if (cookies != null && cookies.length > 0) {
            // 쿠키 배열을 순회하면서 특정 이름의 쿠키가 있는지 확인
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    // 쿠키를 찾았을 때 true 반환
                    return true;
                }
            }
        }
        // 쿠키를 찾지 못했을 때 false 반환
        return false;
    }

    public static void AddObjectCookie(Object object,HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            String recentProductsJson = objectMapper.writeValueAsString(object);
            String encodedRecentProductsJson = URLEncoder.encode(recentProductsJson, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie(RECENT_PRODUCTS, encodedRecentProductsJson);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // Cookie 유효기간 1일로 설정
            response.addCookie(cookie);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Queue<Product> getProductQueueFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(RECENT_PRODUCTS)) {
                    try {
                        String encodedProductQueueJson = cookie.getValue();
                        String decodedProductQueueJson = URLDecoder.decode(encodedProductQueueJson, StandardCharsets.UTF_8.toString());
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.registerModule(new JavaTimeModule());
                        return objectMapper.readValue(decodedProductQueueJson, new TypeReference<>() {
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return new ArrayDeque<>(); // 쿠키가 없거나 쿠키값이 비어있을 경우 빈 큐 반환
    }
}