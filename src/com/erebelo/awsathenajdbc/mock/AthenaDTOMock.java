package com.erebelo.awsathenajdbc.mock;

import com.erebelo.awsathenajdbc.domain.AthenaDTO;
import com.erebelo.awsathenajdbc.domain.DocumentChangesDTO;
import com.erebelo.awsathenajdbc.domain.NameSpaceDocDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AthenaDTOMock {

    private AthenaDTOMock() {
    }

    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String DOC_KEY = "681f9464762ba75d68eb4490";
    private static final String FULL_DOC = "{\"id\":\"637f9464762ba75d68eb4449\",\"createdBy\":\"x123456\"," +
            "\"createdDateTime\":\"2022-05-20'T'14:20:55.389\",\"profile\":{\"dateOfBirth\":\"1991-05-13\"," +
            "\"retirementAge\":77,\"genderCode\":\"Female\",\"maritalStatus\":\"Married\",\"numberOfDependents\":2," +
            "\"alreadyRetired\":false,\"retirementLocation\":{\"latitude\":30.15584197844,\"longitude\":65" +
            ".6745191115,\"formattedAddress\":\"MiamiBeach,FL33141,USA\",\"city\":\"MiamiBeach\",\"state\":\"FL\"," +
            "\"country\":\"USA\",\"postalCode\":\"33141\"},\"spouseProfile\":{\"firstName\":\"Jason\"," +
            "\"lastName\":\"Alexander\",\"dateOfBirth\":\"1965-09-01\",\"genderCode\":\"Male\"," +
            "\"employmentStatus\":\"Retired\",\"retirementAge\":77}}}";

    public static List<AthenaDTO> getAthenaDTOList() {
        return Arrays.asList(getInsertAthenaDTO(), getAthenaDTOUpdateOne(), getAthenaDTOUpdateTwo(),
                getAthenaDTOUpdateThree(), getDeleteAthenaDTO());
    }

    private static AthenaDTO getInsertAthenaDTO() {
        AthenaDTO athenaDTO = new AthenaDTO();

        athenaDTO.setOperationtype(INSERT);
        athenaDTO.setDocumentKey(DOC_KEY);
        athenaDTO.setClustertimenumeric("1669305445000");
        athenaDTO.setClustertimepretty("2022-11-24T15:57:25Z");
        athenaDTO.setNamespacedocument(getNameSpaceDocDTO());
        athenaDTO.setFulldocument(FULL_DOC);
        athenaDTO.setDocumentchanges(null);

        return athenaDTO;
    }

    private static AthenaDTO getAthenaDTOUpdateOne() {
        AthenaDTO athenaDTO = new AthenaDTO();
        athenaDTO.setOperationtype(UPDATE);
        athenaDTO.setDocumentKey(DOC_KEY);
        athenaDTO.setClustertimenumeric("1669305584000");
        athenaDTO.setClustertimepretty("2022-11-24T15:59:44Z");
        athenaDTO.setNamespacedocument(getNameSpaceDocDTO());
        athenaDTO.setFulldocument(null);
        athenaDTO.setDocumentchanges(getDocumentChangesDTOUpdateOne());

        return athenaDTO;
    }

    private static AthenaDTO getAthenaDTOUpdateTwo() {
        AthenaDTO athenaDTO = new AthenaDTO();
        athenaDTO.setOperationtype(UPDATE);
        athenaDTO.setDocumentKey(DOC_KEY);
        athenaDTO.setClustertimenumeric("1669305643000");
        athenaDTO.setClustertimepretty("2022-11-24T16:00:43Z");
        athenaDTO.setNamespacedocument(getNameSpaceDocDTO());
        athenaDTO.setFulldocument(null);
        athenaDTO.setDocumentchanges(getDocumentChangesDTOUpdateTwo());

        return athenaDTO;
    }

    private static AthenaDTO getAthenaDTOUpdateThree() {
        AthenaDTO athenaDTO = new AthenaDTO();
        athenaDTO.setOperationtype(UPDATE);
        athenaDTO.setDocumentKey(DOC_KEY);
        athenaDTO.setClustertimenumeric("1669305705000");
        athenaDTO.setClustertimepretty("2022-11-24T16:01:45Z");
        athenaDTO.setNamespacedocument(getNameSpaceDocDTO());
        athenaDTO.setFulldocument(null);
        athenaDTO.setDocumentchanges(getDocumentChangesDTOUpdateThree());

        return athenaDTO;
    }

    private static AthenaDTO getDeleteAthenaDTO() {
        AthenaDTO athenaDTO = new AthenaDTO();
        athenaDTO.setOperationtype(DELETE);
        athenaDTO.setDocumentKey(DOC_KEY);
        athenaDTO.setClustertimenumeric("1669305881000");
        athenaDTO.setClustertimepretty("2022-11-24T16:04:41Z");
        athenaDTO.setNamespacedocument(getNameSpaceDocDTO());
        athenaDTO.setFulldocument(null);
        athenaDTO.setDocumentchanges(null);

        return athenaDTO;
    }

    private static NameSpaceDocDTO getNameSpaceDocDTO() {
        NameSpaceDocDTO nameSpaceDocDTO = new NameSpaceDocDTO();
        nameSpaceDocDTO.setDb("my_db");
        nameSpaceDocDTO.setColl("my_collection");

        return nameSpaceDocDTO;
    }

    private static DocumentChangesDTO getDocumentChangesDTOUpdateOne() {
        Map<String, Object> updatedfields = new HashMap<>();
        updatedfields.put("profile.alreadyRetired", true);
        updatedfields.put("profile.dateOfBirth", "1975-01-05");
        updatedfields.put("profile.retirementLocation.city", "Orlando");
        updatedfields.put("profile.retirementLocation.formattedAddress", "Orlando, FL 33141, USA");

        DocumentChangesDTO documentChangesDTO = new DocumentChangesDTO();
        documentChangesDTO.setRemovedfields(null);
        documentChangesDTO.setUpdatedfields(updatedfields);

        return documentChangesDTO;
    }

    private static DocumentChangesDTO getDocumentChangesDTOUpdateTwo() {
        Map<String, Object> updatedfields = new HashMap<>();
        updatedfields.put("profile.retirementAge", "67");
        updatedfields.put("profile.currentLocation.country", "BRA");

        DocumentChangesDTO documentChangesDTO = new DocumentChangesDTO();
        documentChangesDTO.setRemovedfields(new String[]{"createdBy", "profile.retirementLocation.longitude"});
        documentChangesDTO.setUpdatedfields(updatedfields);

        return documentChangesDTO;
    }

    private static DocumentChangesDTO getDocumentChangesDTOUpdateThree() {
        DocumentChangesDTO documentChangesDTO = new DocumentChangesDTO();
        documentChangesDTO.setRemovedfields(new String[]{"profile.alreadyRetired",
                "profile.retirementLocation.latitude", "profile.spouseProfile.retirementAge"});
        documentChangesDTO.setUpdatedfields(null);

        return documentChangesDTO;
    }
}
