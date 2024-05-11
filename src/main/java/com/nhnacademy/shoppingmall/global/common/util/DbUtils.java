package com.nhnacademy.shoppingmall.global.common.util;


import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static final DataSource DATASOURCE;

    static {
        BasicDataSource basicDataSource = new BasicDataSource();

        try {
            basicDataSource.setDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //todo1-1 {ip},{database},{username},{password} 설정
        //todo1-2 initialSize, maxTotal, maxIdle, minIdle 은 모두 5로 설정합니다.
        //todo1-3 Validation Query를 설정하세요
        basicDataSource.setUrl("jdbc:mysql://133.186.241.167:3306/nhn_academy_31");
        basicDataSource.setUsername("nhn_academy_31");
        basicDataSource.setPassword("3u.SF)xeXWIfzB[Y");
        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(5);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(5);

        basicDataSource.setValidationQuery("select 1");
        basicDataSource.setMaxWait(Duration.ofSeconds(2));

        //todo1-4 적절히 변경하세요
        DATASOURCE = basicDataSource;
    }

    public static DataSource getDataSource(){
        return DATASOURCE;
    }

}
