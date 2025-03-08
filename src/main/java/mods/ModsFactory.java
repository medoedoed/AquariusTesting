package mods;


import data.ConfigData;
import logs.InfoMessage;

public class ModsFactory {
    private static final ModsFactory instance = new ModsFactory();

    private ModsFactory() {}

    public static ModsFactory getInstance() {
        return instance;
    }

    public Mode getMode(ConfigData configData, String action, String savePath) {
        final String modeName = configData.filesData().mode();

        InfoMessage.send("Current mode value: " + modeName);

        return switch (modeName) {
            case "dir" -> new DirectoryMode(configData, action, savePath);
            case "files" -> new FileMode(configData, action, savePath);
            default -> throw new IllegalArgumentException("Unexpected mode value: " + modeName);
        };
    }
}
