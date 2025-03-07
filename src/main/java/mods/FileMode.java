package mods;

import java.io.File;
import java.util.ArrayList;

public class FileMode extends Mode {
    public FileMode(String pathsString, String action) {
        getPaths(pathsString);
    }

    @Override
    public void execute() {
        System.out.println(paths);
    }
}
