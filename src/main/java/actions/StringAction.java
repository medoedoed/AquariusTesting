package actions;

import data.ConfigData;

public class StringAction extends Action {
    @Override
    public void makeJson(ConfigData configData) {
        createHeader(configData);
    }
}
