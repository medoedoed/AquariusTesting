package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import data.ConfigData;

import java.io.File;
import java.util.ArrayList;


public class CountAction extends Action {
    protected CountAction(ConfigData configData, ArrayList<File> files, String savePath) {
        super(configData, files, savePath);
    }



    @Override
    protected JsonNode processLine(String line, int fileIndex) {
        int wordCount = line.trim().isEmpty() ? 0 : line.trim().split("\\s+").length;
        return JsonNodeFactory.instance.numberNode(wordCount);
    }

    @Override
    protected JsonNode getDefaultValue() {
        return JsonNodeFactory.instance.numberNode(0);
    }
}

