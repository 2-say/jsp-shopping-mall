package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point.service.PointServiceImpl;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointChannelRequest extends ChannelRequest {

    private User user;
    private int amount;

    public PointChannelRequest(User user, int amount) {
        this.user = user;
        this.amount = amount;
    }

    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.

        PointServiceImpl pointService = new PointServiceImpl(new UserRepositoryImpl());
        pointService.chargePoint(amount,user);

        log.debug("pointChannel execute");

        DbConnectionThreadLocal.reset();
    }
}
