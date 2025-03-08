package mods;


import actions.ActionFactory;
import data.ConfigData;

import java.io.File;
import java.util.ArrayList;

public abstract class Mode {
    protected final ArrayList<File> files = new ArrayList<>();
    protected String action;
    protected ConfigData configData;

    public void execute() {
        ActionFactory.getInstance().getAction(action).makeJson(configData);
    }
}
