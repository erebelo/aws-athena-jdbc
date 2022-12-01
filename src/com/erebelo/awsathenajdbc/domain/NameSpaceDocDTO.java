package com.erebelo.awsathenajdbc.domain;

public class NameSpaceDocDTO {

    private String db;
    private String coll;

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getColl() {
        return coll;
    }

    public void setColl(String coll) {
        this.coll = coll;
    }

    @Override
    public String toString() {
        return "NameSpaceDocDTO{" +
                "db='" + db + '\'' +
                ", coll='" + coll + '\'' +
                '}';
    }
}
