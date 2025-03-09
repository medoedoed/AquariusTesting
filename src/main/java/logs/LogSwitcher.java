package logs;

public class LogSwitcher {
    public static void SetLogs(boolean info, boolean warning, boolean error) {
        if (info) InfoMessage.enable();
        if (warning) WarningMessage.enable();
        if (error) ErrorMessage.enable();
    }
}
