package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import data.ConfigData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StringAction extends Action {
    protected StringAction(ConfigData configData, ArrayList<File> files, String savePath) {
        super(configData, files, savePath);
    }

    @Override
    public void makeJson() throws IOException {
        createHeader(configData);

        ArrayNode outArray = this.root.putArray("out");
        List<BufferedReader> readers = new ArrayList<>();

        try {
            for (File file : files) {
                readers.add(new BufferedReader(new FileReader(file)));
            }

            boolean hasData = false;

            while (true) {
                ArrayNode lineArray = outArray.addArray();
                boolean hasNonEmptyLine = false;

                for (BufferedReader reader : readers) {
                    String line = reader.readLine();
                    if (line != null) {
                        hasNonEmptyLine = true;
                        hasData = true;
                        lineArray.add(line);
                    } else {
                        lineArray.add("");
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

}
