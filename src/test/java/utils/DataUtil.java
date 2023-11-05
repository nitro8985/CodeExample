package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class DataUtil {

    private static final String configFileName = "src/test/resources/config.json";
    public static final int RANDSTRLEN = 10;

    public static String getValue(String fieldName) {
        try {
            String configString = Files.readString(Path.of(configFileName));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readValue(configString, JsonNode.class);
            return node.findValuesAsText(fieldName).get(0);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading test data: ", e);
        }
    }
}
