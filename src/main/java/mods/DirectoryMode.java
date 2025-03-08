package mods;


import data.ConfigData;
import logs.InfoMessage;

import java.io.File;
import java.util.Objects;

public class DirectoryMode extends Mode {
    public DirectoryMode(ConfigData configData, String action) {
        GetFiles(configData.filesData().path());
        this.configData = configData;
        this.action = action;
    }

    private void GetFiles(String directoryPath) {
        File dir = new File(directoryPath);

        if (dir.exists() && dir.isDirectory()) {
            this.files.clear();
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isFile()) {
                    this.files.add(file);
                }
            }
            new InfoMessage("Found " + this.files.size()
                    + " files in directory: " + directoryPath + '\n'
                    + "Files: " + this.files).send();
        } else {
            throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
        }
    }
}
