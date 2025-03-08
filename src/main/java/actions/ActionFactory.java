package actions;

import data.ConfigData;
import logs.InfoMessage;

import java.io.File;
import java.util.ArrayList;

public class ActionFactory {
    private static final ActionFactory instance = new ActionFactory();

    private ActionFactory() {}

    public static ActionFactory getInstance() {
        return instance;
    }

    public Action getAction(String action, ConfigData configData, ArrayList<File> files, String savePath) {
        InfoMessage.send("Current action: " + action);

        return switch (action) {
            case "string" -> new StringAction(configData, files, savePath);
            case "count" -> new CountAction(configData, files, savePath);
            case "replace" -> new ReplaceAction(configData, files, savePath);
            default -> throw new IllegalArgumentException("Unexpected action value: " + action);
        };
    }
}
