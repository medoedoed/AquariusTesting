import handlers.ConfigHandler;
import picocli.CommandLine;

@CommandLine.Command(name = "jsonp", version = "0.0.1", mixinStandardHelpOptions = true)
public class JsonParser implements Runnable {
    @CommandLine.Parameters(index = "0", description = "path to config file")
    private String configPath;

    @CommandLine.Parameters(index = "1", description = "config ID")
    private int configId;

    public static void main(String[] args) {
        int exitCode = new CommandLine(JsonParser.class).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        var configHandler = new ConfigHandler(configPath, configId);

        try {
            configHandler.handle();
        } catch (Exception e) {
            System.err.println("[ERROR]: " + e.getMessage());
        }
    }
}
