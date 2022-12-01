package com.erebelo.awsathenajdbc.service;

import com.erebelo.awsathenajdbc.domain.AthenaDTO;
import com.erebelo.awsathenajdbc.domain.DocumentChangesDTO;
import com.erebelo.awsathenajdbc.domain.NameSpaceDocDTO;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static com.erebelo.awsathenajdbc.constants.AthenaConstants.AWS_ACCESS_KEY;
import static com.erebelo.awsathenajdbc.constants.AthenaConstants.AWS_SECRET_KEY;
import static com.erebelo.awsathenajdbc.constants.AthenaConstants.CONNECTION_URL;
import static com.erebelo.awsathenajdbc.constants.AthenaConstants.DRIVER_CLASS;
import static com.erebelo.awsathenajdbc.constants.AthenaConstants.QUERY;
import static com.erebelo.awsathenajdbc.constants.AthenaConstants.S3_OUTPUT_LOCATION;
import static com.erebelo.awsathenajdbc.mock.AthenaDTOMock.getAthenaDTOList;

public class AthenaQueryExecutor {

    private String chosenOption;
    private String documentkey;
    private String clustertimenumeric;

    public List<AthenaDTO> athenaList = new ArrayList<>();
    private Map<String, Object> finalMap = new HashMap<>();
    private boolean deleted = false;

    public void readEntry() throws IOException {
        System.out.println(LocalDateTime.now() + " INFO Reading the entry");
        System.out.println("...............");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (Objects.isNull(chosenOption)) {
            System.out.print("Enter 1 for mock data or 2 to read the data from athena: ");

            chosenOption = reader.readLine();

            if (chosenOption == null || chosenOption.isBlank() ||
                    !(chosenOption.equals("1") || chosenOption.equals("2"))) {
                System.out.println("*** Invalid option ***");
                chosenOption = null;
            }
        }

        if (chosenOption.equals("1")) {
            athenaList = getAthenaDTOList();
        } else {
            while (documentkey == null || documentkey.isBlank()) {
                System.out.print("Enter documentkey: ");
                documentkey = reader.readLine();

                if (documentkey == null || documentkey.isBlank()) {
                    System.out.println("*** Invalid documentkey ***");
                }
            }

            while (clustertimenumeric == null || clustertimenumeric.isBlank()) {
                System.out.print("Enter clustertimenumeric: ");
                clustertimenumeric = reader.readLine();

                if (clustertimenumeric == null || clustertimenumeric.isBlank()) {
                    System.out.println("*** Invalid clustertimenumeric ***");
                }
            }
        }
        System.out.println("...............");
    }

    public void execute() throws SQLException {
        if (chosenOption.equals("2")) {
            Properties props = new Properties();
            Connection conn = null;
            Statement statement = null;
            ResultSet rs = null;

            try {
                System.out.println(LocalDateTime.now() + " INFO Fetching Athena data");
                setProperties(props);
                Class.forName(DRIVER_CLASS);
                conn = DriverManager.getConnection(CONNECTION_URL, props);
                statement = conn.createStatement();

                rs = statement.executeQuery(String.format(QUERY, documentkey, clustertimenumeric));

                System.out.println(LocalDateTime.now() + " INFO Structuring Athena data from ResultSet to DTO object");
                while (rs.next()) {
                    athenaList.add(resultSetToAthenaDTO(rs));
                }
            } catch (Exception e) {
                throw new IllegalStateException("Error reading data from athena", e);
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
    }

    public void buildObject() {
        System.out.println(LocalDateTime.now() + " INFO Assembling the object through operations");
        if (athenaList != null && !athenaList.isEmpty()) {
            // To build the object, the first position of the collection must contain the INSERT operation
            if (athenaList.get(0).getOperationtype().equalsIgnoreCase("insert")) {
                String fullDocument = athenaList.get(0).getFulldocument();

                if (fullDocument != null && !fullDocument.isBlank()) {
                    finalMap = new Gson().fromJson(fullDocument, Map.class);
                    int size = athenaList.size();

                    // Decreasing the collection size when found the DELETE operation in the last position
                    if (size > 1) {
                        if (athenaList.get(size - 1).getOperationtype().equalsIgnoreCase("delete")) {
                            deleted = true;
                            --size;
                        }

                        // Iterating through UPDATE operations such as removing and inserting attributes and
                        // updating existing attribute values
                        for (int i = 1; i < size; i++) {
                            if (athenaList.get(i).getOperationtype().equalsIgnoreCase("update")) {
                                DocumentChangesDTO documentChanges = athenaList.get(i).getDocumentchanges();
                                if (documentChanges != null) {
                                    removeFields(documentChanges.getRemovedfields());
                                    updateFields(documentChanges.getUpdatedfields());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void result() {
        System.out.println(LocalDateTime.now() + " INFO Printing the object result");
        System.out.println("\n*******************************************************\n");
        if (deleted) {
            System.out.println("The last object interaction was the DELETE operation and its last state before " +
                    "that " +
                    "operation was:");
        }
        System.out.println("Final Object: " + new Gson().toJson(finalMap));
        System.out.println("\n*******************************************************\n");
    }

    private void setProperties(Properties properties) {
        System.setProperty("aws.accessKeyId", AWS_ACCESS_KEY);
        System.setProperty("aws.secretKey", AWS_SECRET_KEY);
        properties.setProperty("S3OutputLocation", S3_OUTPUT_LOCATION);
        properties.setProperty("AwsCredentialsProviderClass", "com.simba.athena.amazonaws.auth" +
                ".SystemPropertiesCredentialsProvider");
    }

    private AthenaDTO resultSetToAthenaDTO(ResultSet rs) throws SQLException {
        AthenaDTO athenaDTO = new AthenaDTO();
        athenaDTO.setOperationtype(rs.getString("operationtype"));
        athenaDTO.setDocumentKey(rs.getString("documentKey"));
        athenaDTO.setClustertimenumeric(rs.getString("clustertimenumeric"));
        athenaDTO.setClustertimepretty(rs.getString("clustertimepretty"));
        athenaDTO.setNamespacedocument(new Gson().fromJson(rs.getString("namespacedocument"),
                NameSpaceDocDTO.class));
        athenaDTO.setFulldocument(rs.getString("fulldocument"));
        athenaDTO.setDocumentchanges(new Gson().fromJson(rs.getString("documentchanges"),
                DocumentChangesDTO.class));

        return athenaDTO;
    }

    /*
        removedFields: ["profile.retirementLocation.latitude","profile.retirementLocation.longitude"]
     */
    private void removeFields(String[] removedFields) {
        if (removedFields != null) {
            for (String nestedKeys : removedFields) {
                String[] keys = nestedKeys.split("\\.");
                recursiveKeyRemoval(finalMap, new LinkedList<>(Arrays.asList(keys)));
            }
        }
    }

    private void recursiveKeyRemoval(Map<String, Object> map, LinkedList<String> keys) {
        if (map == null || map.isEmpty()) {
            return;
        }

        String key = keys.get(0);

        if (keys.size() == 1) {
            map.remove(key);
            return;
        }

        if (map.get(key) instanceof Map) {
            Map<String, Object> subMap = (Map<String, Object>) map.get(key);
            keys.remove(0);
            recursiveKeyRemoval(subMap, keys);
        }
    }

    /*
        updatedfields: {
            "profile.retirementlocation.formattedaddress": "Orlando, FL 33141, USA",
            "spouseprofile.employmentstatus": "Employed",
            "profile.alreadyretired": true
        }
     */
    private void updateFields(Map<String, Object> updatedFieldsMap) {
        if (updatedFieldsMap != null && updatedFieldsMap.size() > 0) {
            for (Map.Entry<String, Object> entry : updatedFieldsMap.entrySet()) {
                // Allowing only Map with one key/value level (non-nested structures)
                if (!(entry.getValue() instanceof Map)) {
                    String[] keys = entry.getKey().split("\\.");
                    recursiveKeyUpdate(finalMap, new LinkedList<>(Arrays.asList(keys)), entry.getValue());
                }
            }
        }
    }

    private void recursiveKeyUpdate(Map<String, Object> map, LinkedList<String> keys, Object value) {
        if ((map == null || map.isEmpty()) && keys.isEmpty()) {
            return;
        }

        String key = keys.get(0);

        if (keys.size() == 1) {
            map.put(key, value);
            return;
        }

        map.computeIfAbsent(key, set -> new LinkedTreeMap<String, Object>());

        if (map.get(key) instanceof Map) {
            Map<String, Object> subMap = (Map<String, Object>) map.get(key);
            keys.remove(0);
            recursiveKeyUpdate(subMap, keys, value);
        }
    }
}
