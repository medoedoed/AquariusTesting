package mods;

import java.io.File;
import java.util.ArrayList;

public class DirectoryMode extends Mode {
    public DirectoryMode(String pathsString, String action) {
        getPaths(pathsString);
    }

    @Override
    public void execute() {
        System.out.println(paths);
    }
}
