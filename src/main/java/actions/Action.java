package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    protected File outputFile;

    protected static final String DEFAULT_OUTPUT_DIRECTORY = ".";
    protected static final String DEFAULT_OUTPUT_NAME = "output.json";


    protected Action(ConfigData configData, ArrayList<File> files, String savePath) {
        this.files = files;
        this.configData = configData;
        try {
            setFilePath(savePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void createHeader(ConfigData configData) {
        ObjectMapper objectMapper = new ObjectMapper();
        root = objectMapper.createObjectNode();

        root.put("configFile", configData.configFile());
        root.put("configurationID", configData.configId());

        ObjectNode configNode = objectMapper.valueToTree(configData);
        root.set("configurationData", configNode);
    }

    protected void saveJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, root);
        System.out.println("Json saved to " + outputFile.getCanonicalPath());
    }

    protected void setFilePath(String savePath) throws IOException {

        if (savePath == null || savePath.isBlank()) {
            this.outputFile = new File(DEFAULT_OUTPUT_DIRECTORY + File.separator + DEFAULT_OUTPUT_NAME);
            WarningMessage.send("No file path provided. Using default: " +
                    this.outputFile.getCanonicalFile());
            return;
        }

        File file = new File(savePath);

        if (file.exists() && file.isDirectory()) {
            this.outputFile = new File(file.getAbsoluteFile() + File.separator + DEFAULT_OUTPUT_NAME);
            return;
        }

        if (file.exists() && file.isFile()) {
            throw new IllegalArgumentException("File already exists: " + file.getCanonicalPath());
        }

        this.outputFile = file;
        InfoMessage.send("Json will be saved as: " + this.outputFile.getAbsolutePath());
    }



    public void makeJson() throws IOException {
        createHeader(configData);

        ObjectNode outNode = root.putObject("out");

        List<BufferedReader> readers = new ArrayList<>();
        for (File file : files) {
            readers.add(new BufferedReader(new FileReader(file)));
        }

        boolean hasData = false;
        int lineIndex = 1;

        try {
            while (true) {
                ObjectNode lineNode = outNode.putObject(String.valueOf(lineIndex));
                boolean hasNonEmptyLine = false;

                for (int i = 0; i < readers.size(); i++) {
                    String line = readLine(readers.get(i));
                    if (line != null) {
                        hasNonEmptyLine = true;
                        hasData = true;
                        lineNode.set(String.valueOf(i + 1), processLine(line, i));
                    } else {
                        lineNode.set(String.valueOf(i + 1), getDefaultValue());
                    }
                }

                if (!hasNonEmptyLine) {
                    outNode.remove(String.valueOf(lineIndex));
                    break;
                }
                lineIndex++;
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
