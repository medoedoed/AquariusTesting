package actions;

import logs.InfoMessage;
import mods.DirectoryMode;
import mods.FileMode;

public class ActionFactory {
    private static final ActionFactory instance = new ActionFactory();

    private ActionFactory() {}

    public static ActionFactory getInstance() {
        return instance;
    }

    public Action getAction(String action) {
        new InfoMessage("Current action: " + action).send();

        return switch (action) {
            case "string" -> new StringAction();
            case "count" -> new CountAction();
            case "replace" -> new ReplaceAction();
            default -> throw new IllegalArgumentException("Unexpected action value: " + action);
        };
    }
}
