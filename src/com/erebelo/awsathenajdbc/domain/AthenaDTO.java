package com.erebelo.awsathenajdbc.domain;

public class AthenaDTO {

    private String operationtype;
    private String documentKey;
    private String clustertimenumeric;
    private String clustertimepretty;
    private NameSpaceDocDTO namespacedocument;
    private String fulldocument;
    private DocumentChangesDTO documentchanges;

    public String getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(String operationtype) {
        this.operationtype = operationtype;
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public String getClustertimenumeric() {
        return clustertimenumeric;
    }

    public void setClustertimenumeric(String clustertimenumeric) {
        this.clustertimenumeric = clustertimenumeric;
    }

    public String getClustertimepretty() {
        return clustertimepretty;
    }

    public void setClustertimepretty(String clustertimepretty) {
        this.clustertimepretty = clustertimepretty;
    }

    public NameSpaceDocDTO getNamespacedocument() {
        return namespacedocument;
    }

    public void setNamespacedocument(NameSpaceDocDTO namespacedocument) {
        this.namespacedocument = namespacedocument;
    }

    public String getFulldocument() {
        return fulldocument;
    }

    public void setFulldocument(String fulldocument) {
        this.fulldocument = fulldocument;
    }

    public DocumentChangesDTO getDocumentchanges() {
        return documentchanges;
    }

    public void setDocumentchanges(DocumentChangesDTO documentchanges) {
        this.documentchanges = documentchanges;
    }

    @Override
    public String toString() {
        return "AthenaDTO{" +
                "operationtype='" + operationtype + '\'' +
                ", documentKey='" + documentKey + '\'' +
                ", clustertimenumeric='" + clustertimenumeric + '\'' +
                ", clustertimepretty='" + clustertimepretty + '\'' +
                ", namespacedocument=" + namespacedocument +
                ", fulldocument=" + fulldocument +
                ", documentchanges=" + documentchanges +
                '}';
    }
}
