package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.entity.user.domain.User;
import com.nhnacademy.shoppingmall.entity.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/userUpdate.do")
public class UserUpdateController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 생년월일 문자열에서 '-'를 제거하고 8자리의 숫자 형식으로 변환
        String birthdate = req.getParameter("birthdate").replaceAll("-", "");

        // 사용자로부터 전달된 데이터를 사용하여 User 객체 생성
        User user = new User(
                req.getParameter("user_id"),
                req.getParameter("user_name"),
                req.getParameter("user_password"),
                birthdate, // 수정된 생년월일 값으로 설정
                User.Auth.ROLE_USER,
                Integer.parseInt(req.getParameter("user_point")),
                LocalDateTime.now(), // 현재 시간을 createdAt으로 설정
                LocalDateTime.parse(req.getParameter("latest_login_at")) // 문자열을 LocalDateTime으로 변환하여 latestLoginAt으로 설정
        );

        // UserServiceImpl 객체 생성
        UserServiceImpl userService = new UserServiceImpl(new UserRepositoryImpl());

        // UserServiceImpl의 updateUser 메서드를 호출하여 사용자 정보 업데이트
        userService.updateUser(user);

        // 업데이트 후 반환할 페이지 주소 또는 뷰 이름 반환 (여기서는 null을 반환하도록 하였으나, 필요에 따라 수정 가능)
        return "redirect:/index.do";
    }


}
