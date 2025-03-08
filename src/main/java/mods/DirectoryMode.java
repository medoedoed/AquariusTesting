package mods;


import logs.InfoMessage;

import java.io.File;

public class DirectoryMode implements Mode {
    private File directory;

    public DirectoryMode(String pathsString, String action) {
        GetDirectory(pathsString);
    }

    @Override
    public void execute() {
    }

    public void GetDirectory(String directoryPath) {
        File dir = new File(directoryPath);

        if (dir.exists() && dir.isDirectory()) {
            this.directory = dir;
        } else {
            throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
        }

        new InfoMessage("Directory Path: " + directoryPath).send();
    }
}
