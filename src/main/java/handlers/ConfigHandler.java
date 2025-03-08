package handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.ConfigData;
import data.FilesData;
import mods.DirectoryMode;
import mods.FileMode;
import mods.ModsFactory;
import picocli.CommandLine;

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

        if (configurations == null || !configurations.isArray()) {
            throw new IllegalArgumentException("Wrong configuration JSON");
        }

        for (JsonNode config : configurations) {
            if (config.get("id").asInt() == configId) {
                final String mode = config.get("mode").asText();
                final String path = config.get("path").asText();
                final String action = config.get("action").asText();
                final var configData = new ConfigData(
                        configPath,
                        configId,
                        new FilesData(mode, path));

                ModsFactory.getInstance().getMode(configData, action).execute();
                return;
            }
        }

        throw new IllegalArgumentException("Configuration ID " + configId + " not found");
    }
}
