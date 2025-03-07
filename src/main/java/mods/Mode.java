package mods;

import java.io.File;
import java.util.ArrayList;

public abstract class Mode {
    protected ArrayList<File> paths = new ArrayList<>();

    void getPaths(String pathsString) {
        String[] pathArray = pathsString.split(",");
        paths.clear();
        for (String path : pathArray) {
            paths.add(new File(path.trim()));
        }
    }

    abstract void execute();
}
