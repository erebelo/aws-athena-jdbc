package com.erebelo.awsathenajdbc.domain;

import java.util.Arrays;
import java.util.Map;

public class DocumentChangesDTO {

    private String[] removedfields;
    private Map<String, Object> updatedfields;

    public String[] getRemovedfields() {
        return removedfields;
    }

    public void setRemovedfields(String[] removedfields) {
        this.removedfields = removedfields;
    }

    public Map<String, Object> getUpdatedfields() {
        return updatedfields;
    }

    public void setUpdatedfields(Map<String, Object> updatedfields) {
        this.updatedfields = updatedfields;
    }

    @Override
    public String toString() {
        return "DocumentChangesDTO{" +
                "removedfields=" + Arrays.toString(removedfields) +
                ", updatedfields=" + updatedfields +
                '}';
    }
}
