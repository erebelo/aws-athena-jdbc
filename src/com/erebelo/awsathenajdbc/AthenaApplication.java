package com.erebelo.awsathenajdbc;

import com.erebelo.awsathenajdbc.service.AthenaQueryExecutor;

import java.io.IOException;
import java.sql.SQLException;

public class AthenaApplication {

    public static void main(String[] args) throws IOException, SQLException {
        AthenaQueryExecutor queryExecutor = new AthenaQueryExecutor();
        queryExecutor.readEntry();
        queryExecutor.execute();
        queryExecutor.buildObject();
        queryExecutor.result();
    }
}
