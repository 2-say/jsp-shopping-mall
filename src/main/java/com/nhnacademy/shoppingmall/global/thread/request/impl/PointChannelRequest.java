package com.nhnacademy.shoppingmall.global.thread.request.impl;

import com.nhnacademy.shoppingmall.domain.point.exception.PointChargeException;
import com.nhnacademy.shoppingmall.global.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.domain.point.service.PointServiceImpl;
import com.nhnacademy.shoppingmall.global.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.domain.user.domain.User;
import com.nhnacademy.shoppingmall.domain.user.repository.impl.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointChannelRequest extends ChannelRequest {

    public static final double RETURN_POINT_RATIO = 0.1;
    private User user;
    private int amount;

    public PointChannelRequest(User user, int amount) {
        this.user = user;
        this.amount = amount;
    }

    @Override
    public void execute() {

        try {
            DbConnectionThreadLocal.initialize();
            //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.

            PointServiceImpl pointService = new PointServiceImpl(new UserRepositoryImpl());

            int point = (int) (amount * RETURN_POINT_RATIO);

            pointService.chargePoint(point, user);

            log.info("pointChannel point: {} 포인트 적립 완료", point);

            DbConnectionThreadLocal.reset();
        } catch (Exception e) {
            throw new PointChargeException("포인트 충전 중 오류가 발생했습니다. 관리자에게 문의해주세요");
        }
    }
}
