package mods;

import logs.InfoMessage;
import logs.WarningMessage;

import java.io.File;
import java.util.ArrayList;

public class FileMode implements Mode {
    private final ArrayList<File> files = new ArrayList<>();

    public FileMode(String pathsString, String action) {
        getFiles(pathsString);
    }

    @Override
    public void execute() {
        // TODO
    }

    private void getFiles(String pathsString) {
        String[] pathArray = pathsString.split(",");
        files.clear();

        for (String path : pathArray) {
            File file = new File(path.trim());
            if (file.exists() && file.isFile() && file.canRead()) {
                files.add(file);
            } else {
                new WarningMessage("Invalid or inaccessible file: " + path).send();
            }
        }

//        if (files.isEmpty()) {
//            throw new IllegalArgumentException("No valid files provided.");
//        }

        new InfoMessage("Files paths: " + files).send();
    }

}
