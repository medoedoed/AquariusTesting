package mods;


import data.ConfigData;
import logs.InfoMessage;

public class ModsFactory {
    private static final ModsFactory instance = new ModsFactory();

    private ModsFactory() {}

    public static ModsFactory getInstance() {
        return instance;
    }

    public Mode getMode(ConfigData configData, String action) {
        final String modeName = configData.filesData().mode();

        new InfoMessage("Current mode value: " + modeName).send();

        return switch (modeName) {
            case "dir" -> new DirectoryMode(configData, action);
            case "files" -> new FileMode(configData, action);
            default -> throw new IllegalArgumentException("Unexpected mode value: " + modeName);
        };
    }
}
