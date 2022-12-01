package com.erebelo.awsathenajdbc.constants;

public class AthenaConstants {

    private AthenaConstants() {
    }

    public static final String DRIVER_CLASS = "com.simba.athena.jdbc.Driver";
    public static final String AWS_ACCESS_KEY = "";
    public static final String AWS_SECRET_KEY = "";
    public static final String CONNECTION_URL = "jdbc:awsathena://AwsRegion=us-east-1";
    public static final String S3_OUTPUT_LOCATION = "";  /* "s3://my_bucket_name/" */
    public static final String QUERY = "";
    /* "SELECT * FROM \"database_name\".\"table_name\" WHERE documentkey = '%s' AND clustertimenumeric <= %s ORDER BY clustertimenumeric ASC;"; */

}
