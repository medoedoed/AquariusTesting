package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import data.ConfigData;
import logs.InfoMessage;
import logs.WarningMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Action {
    protected ObjectNode root;
    protected final ArrayList<File> files;
    protected final ConfigData configData;
    protected File savePath = new File("");

    private static final String CONFIG_FILE_NAME = "config.json";

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

    public void makeJson() throws IOException {
        createHeader(configData);

        ArrayNode outArray = this.root.putArray("out");
        List<BufferedReader> readers = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            readers.add(new BufferedReader(new FileReader(files.get(i))));
        }

        boolean hasData = false;

        try {
            while (true) {
                ArrayNode lineArray = outArray.addArray();
                boolean hasNonEmptyLine = false;

                for (int i = 0; i < readers.size(); i++) {
                    String line = readLine(readers.get(i));
                    if (line != null) {
                        hasNonEmptyLine = true;
                        hasData = true;
                        lineArray.add(processLine(line, i + 1));
                    } else {
                        lineArray.add(getDefaultValue());
                    }
                }

                if (!hasNonEmptyLine) {
                    outArray.remove(outArray.size() - 1);
                    break;
                }
            }

            if (!hasData) {
                root.remove("out");
            }
        } finally {
            for (BufferedReader reader : readers) {
                reader.close();
            }
        }

        saveJson();
    }

    protected String readLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    protected abstract JsonNode processLine(String line, int fileIndex);

    protected abstract JsonNode getDefaultValue();
}
