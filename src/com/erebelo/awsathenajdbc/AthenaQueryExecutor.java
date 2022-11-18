package com.erebelo.awsathenajdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AthenaQueryExecutor {

    public void execute() throws SQLException {
        Properties props = new Properties();
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            setProperties(props);
            Class.forName(AthenaConstants.DRIVER_CLASS);
            conn = DriverManager.getConnection(AthenaConstants.CONNECTION_URL, props);
            statement = conn.createStatement();

            rs = statement.executeQuery(AthenaConstants.QUERY);
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.print(rsmd.getColumnName(i) + ":  " + rs.getString(i) + "\t\t");
                }
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    private void setProperties(Properties properties) {
        System.setProperty("aws.accessKeyId", AthenaConstants.AWS_ACCESS_KEY);
        System.setProperty("aws.secretKey", AthenaConstants.AWS_SECRET_KEY);
        properties.setProperty("S3OutputLocation", AthenaConstants.S3_OUTPUT_LOCATION);
        properties.setProperty("AwsCredentialsProviderClass", "com.simba.athena.amazonaws.auth" +
                ".SystemPropertiesCredentialsProvider");
    }
}
