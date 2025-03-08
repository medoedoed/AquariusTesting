package actions;

import data.ConfigData;

import java.io.File;
import java.util.ArrayList;

public class CountAction extends Action {
    protected CountAction(ConfigData configData, ArrayList<File> files, String savePath) {
        super(configData, files, savePath);
    }

    @Override
    public void makeJson() {

    }
}
