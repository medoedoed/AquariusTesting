package mods;

import data.ConfigData;
import logs.InfoMessage;
import logs.WarningMessage;

import java.io.File;

public class FileMode extends Mode {
    public FileMode(ConfigData configData, String action, String savePath) {
        getFiles(configData.filesData().path());
        this.configData = configData;
        this.action = action;
        this.savePath = savePath;
    }

    protected void getFiles(String pathsString) {
        String[] pathArray = pathsString.split(",");
        this.files.clear();

        for (String path : pathArray) {
            File file = new File(path.trim());
            if (file.exists() && file.isFile() && file.canRead()) {
                this.files.add(file);
            } else {
                WarningMessage.send("Invalid or inaccessible file: " + path);
            }
        }

//        if (files.isEmpty()) {
//            throw new IllegalArgumentException("No valid files provided.");
//        }

        InfoMessage.send("File paths: " + this.files);
    }
}
