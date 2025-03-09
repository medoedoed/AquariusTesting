package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import data.ConfigData;

import java.io.File;
import java.util.ArrayList;

public class StringAction extends Action {
    protected StringAction(ConfigData configData, ArrayList<File> files, String savePath) {
        super(configData, files, savePath);
    }

    @Override
    protected JsonNode processLine(String line, int fileIndex) {
        return JsonNodeFactory.instance.textNode(line);
    }

    @Override
    protected JsonNode getDefaultValue() {
        return JsonNodeFactory.instance.textNode("");
    }

}
