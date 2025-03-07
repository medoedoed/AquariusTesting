package handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {
    private String configPath;
    private int configId;

    public ConfigHandler(String configPath, int configId) {
        this.configPath = configPath;
        this.configId = configId;
    }

    public void handle() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(configPath));
        JsonNode configurations = root.get("configurations");

        if (configurations != null && configurations.isArray()) {
            for (JsonNode config : configurations) {
                if (config.get("id").asInt() == configId) {
                    System.out.println("Found configuration: " + config);
                    return;
                }
            }
        }
        System.out.printf("Configuration ID %d not found.%n", configId);
    }
}
