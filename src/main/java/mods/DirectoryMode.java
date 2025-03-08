package mods;


import data.ConfigData;
import logs.InfoMessage;

import java.io.File;
import java.util.Objects;

public class DirectoryMode extends Mode {
    public DirectoryMode(ConfigData configData, String action, String savePath) {
        GetFiles(configData.filesData().path());
        this.configData = configData;
        this.action = action;
        this.savePath = savePath;
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
            InfoMessage.send("Found " + this.files.size()
                    + " files in directory: " + directoryPath + '\n'
                    + "Files: " + this.files);
        } else {
            throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
        }
    }
}
