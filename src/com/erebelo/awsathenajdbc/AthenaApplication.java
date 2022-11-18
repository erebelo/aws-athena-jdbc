package com.erebelo.awsathenajdbc;

import java.sql.SQLException;

public class AthenaApplication {

    public static void main(String[] args) throws SQLException {
        AthenaQueryExecutor queryExecutor = new AthenaQueryExecutor();
        queryExecutor.execute();
    }
}
