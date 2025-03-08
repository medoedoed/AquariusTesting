package mods;


import actions.ActionFactory;
import data.ConfigData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Mode {
    protected final ArrayList<File> files = new ArrayList<>();
    protected String action;
    protected ConfigData configData;
    protected String savePath;

    public void execute() throws IOException {
        ActionFactory.getInstance().getAction(action, configData, this.files, savePath).makeJson();
    }
}
