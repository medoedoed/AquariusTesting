package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import data.ConfigData;
import logs.InfoMessage;
import logs.WarningMessage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Action {
    protected ObjectNode root;
    protected final ArrayList<File> files;
    protected final ConfigData configData;
    protected File savePath = new File("");

    private final String CONFIG_FILE_NAME = "config.json";

    protected Action(ConfigData configData, ArrayList<File> files, String savePath) {
        this.files = files;
        this.configData = configData;
        setDirectory(savePath);
    }

    protected void createHeader(ConfigData configData) {
        ObjectMapper objectMapper = new ObjectMapper();
        root = objectMapper.valueToTree(configData);
    }

    protected void saveJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(this.savePath + File.separator + CONFIG_FILE_NAME), root);
        System.out.println("Json saved to " + this.savePath.getAbsolutePath() + File.separator + CONFIG_FILE_NAME);
    }

    protected void setDirectory(String savePath) {
        if (savePath == null) {
            InfoMessage.send("Json save directory set to default: " + this.savePath.getAbsolutePath());
            return;
        }

        File path = new File(savePath);
        if (!path.exists()) {
            WarningMessage.send("Json save directory does not exist: " + savePath +
                    "\nDirectory will be set to default: " + this.savePath.getAbsolutePath());
            return;
        }

        if (!path.isDirectory()) {
            WarningMessage.send("Provided path is not a directory: " + path.getAbsolutePath());
            return;
        }

        InfoMessage.send("Json save directory set to " + path.getAbsolutePath());

        this.savePath = path;
    }

    public abstract void makeJson() throws IOException;
}
