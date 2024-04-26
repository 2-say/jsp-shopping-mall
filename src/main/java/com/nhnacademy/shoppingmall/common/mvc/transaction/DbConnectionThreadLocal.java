package com.nhnacademy.shoppingmall.common.mvc.transaction;

import com.nhnacademy.shoppingmall.common.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class DbConnectionThreadLocal {
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> sqlErrorThreadLocal = ThreadLocal.withInitial(() -> false);

    public static void initialize() {
        Connection con = null;
        //todo2-1 - connection pool에서 connectionThreadLocal에 connection을 할당합니다.
        //todo2-2 connectiond의 Isolation level을 READ_COMMITED를 설정 합니다.
        //todo2-3 auto commit 을 false로 설정합니다.
        try {
            con = DbUtils.getDataSource().getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connectionThreadLocal.set(con); //변수 설정
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connectionThreadLocal.get();
    }

    public static void setSqlError(boolean sqlError) {
        sqlErrorThreadLocal.set(sqlError);
    }

    public static boolean getSqlError() {
        return sqlErrorThreadLocal.get();
    }

    public static void reset() {
        //todo2-4 사용이 완료된 connection은 close를 호출하여 connection pool에 반환합니다.
        Connection con = connectionThreadLocal.get();
        if (con == null) return;
        try {
            if (sqlErrorThreadLocal.get()) {
                con.rollback();
            } else {
                //todo2-6 getSqlError() 에러가 존재하지 않다면 commit 합니다.
                con.commit();
            }
            if (!con.isClosed()) con.close();
            //todo2-7 현제 사용하고 있는 connection을 재 사용할 수 없도록 connectionThreadLocal을 초기화 합니다.
            connectionThreadLocal.remove();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
