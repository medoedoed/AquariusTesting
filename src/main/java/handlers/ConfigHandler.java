package handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mods.DirectoryMode;
import mods.FileMode;
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
        var root = mapper.readTree(new File(configPath));
        var configurations = root.get("configurations");

        if (configurations == null || !configurations.isArray()) {
            throw new IllegalArgumentException("Wrong configuration JSON");
        }

        for (JsonNode config : configurations) {
            if (config.get("id").asInt() == configId) {
                var mode = config.get("mode").asText();
                var paths = config.get("path").asText();
                var action = config.get("action").asText();

                callMode(mode, paths, action);

                return;
            }
        }

        throw new IllegalArgumentException("Configuration ID " + configId + " not found");
    }

    private void callMode(String mode, String paths, String action) {
        switch (mode) {
            case "dir":
                new DirectoryMode(paths, action).execute();
            case "files":
                new FileMode(paths, action).execute();
                break;
            default:
                throw new IllegalArgumentException("Unexpected mode value: " + mode);
        }

    }
}
