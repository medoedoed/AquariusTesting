package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import data.ConfigData;

import java.io.File;
import java.util.ArrayList;

public class ReplaceAction extends Action {
    protected ReplaceAction(ConfigData configData, ArrayList<File> files, String savePath) {
        super(configData, files, savePath);
    }

    @Override
    protected JsonNode processLine(String line, int fileIndex) {
        StringBuilder replacedLine = new StringBuilder();

        for (char ch : line.toCharArray()) {
            if (Character.isLetter(ch)) {
                int charIndex = Character.toLowerCase(ch) - 'a' + 1;
                replacedLine.append(charIndex + fileIndex + 1);
            } else {
                replacedLine.append(ch);
            }
        }

        return JsonNodeFactory.instance.textNode(replacedLine.toString());
    }

    @Override
    protected JsonNode getDefaultValue() {
        return JsonNodeFactory.instance.textNode("");
    }
}
