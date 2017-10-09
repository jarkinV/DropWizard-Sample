
package com.softserve.healthCheck;

import javax.sql.DataSource;

import com.codahale.metrics.health.HealthCheck;

public class DBHealthCheck extends HealthCheck {

    private final DataSource dataSourse;

    public DBHealthCheck(DataSource dataSourse) {
        super();
        this.dataSourse = dataSourse;
    }

    @Override
    protected Result check() throws Exception {
        if (dataSourse.getConnection() != null) {
            System.out.println("ddd");
            return Result.healthy();
        } else {
            System.out.println("qqq");
            return Result.unhealthy("Can't connect to DB");
        }
    }
}
