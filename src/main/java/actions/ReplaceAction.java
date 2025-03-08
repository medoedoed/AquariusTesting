package actions;

import data.ConfigData;

import java.io.File;
import java.util.ArrayList;

public class ReplaceAction extends Action {
    protected ReplaceAction(ConfigData configData, ArrayList<File> files, String savePath) {
        super(configData, files, savePath);
    }

    @Override
    public void makeJson() {

    }
}
