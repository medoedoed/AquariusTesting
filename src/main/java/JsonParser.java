import handlers.ConfigHandler;
import logs.ErrorMessage;
import picocli.CommandLine;

@CommandLine.Command(name = "jsonp", version = "0.0.1", mixinStandardHelpOptions = true)
public class JsonParser implements Runnable {
    @CommandLine.Parameters(index = "0", description = "path to config file")
    private String configPath;

    @CommandLine.Parameters(index = "1", description = "config ID")
    private int configId;

    @CommandLine.Option(names = {"-p", "--path"}, description = "path to save json")
    private String savePath;

    public static void main(String[] args) {
        new CommandLine(JsonParser.class).execute(args);
    }

    @Override
    public void run() {
        var configHandler = new ConfigHandler(configPath, configId, savePath);
        try {
            configHandler.handle();
        } catch (Exception e) {
            ErrorMessage.send(e.getMessage());
//            System.exit(1);
        }
    }
}
