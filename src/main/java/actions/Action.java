package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.ConfigData;

import java.io.File;
import java.io.IOException;

public abstract class Action {
    private JsonNode root;

    protected void createHeader(ConfigData configData) {
        ObjectMapper objectMapper = new ObjectMapper();
        root = objectMapper.valueToTree(configData);
    }

    protected void saveJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File("configData.json"), root);
    }

    public abstract void makeJson(ConfigData configData);
}
