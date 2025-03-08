package mods;

import data.ConfigData;
import logs.InfoMessage;
import logs.WarningMessage;

import java.io.File;
import java.util.ArrayList;

public class FileMode extends Mode {
    public FileMode(ConfigData configData, String action) {
        getFiles(configData.filesData().path());
        this.configData = configData;
        this.action = action;
    }

    protected void getFiles(String pathsString) {
        String[] pathArray = pathsString.split(",");
        this.files.clear();

        for (String path : pathArray) {
            File file = new File(path.trim());
            if (file.exists() && file.isFile() && file.canRead()) {
                this.files.add(file);
            } else {
                new WarningMessage("Invalid or inaccessible file: " + path).send();
            }
        }

//        if (files.isEmpty()) {
//            throw new IllegalArgumentException("No valid files provided.");
//        }

        new InfoMessage("File paths: " + this.files).send();
    }
}
