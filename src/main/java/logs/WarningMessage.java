package logs;

public class WarningMessage extends Message {
    private static final String ANSI_ORANGE = "\u001B[38;5;214m";
    private static boolean isActive = true;

    public static void disable() {
        isActive = false;
    }

    public static void enable() {
        isActive = false;
    }
    public static void send(String messageBody) {
        if (!isActive) return;

        printToConsole("[WARNING]: " + messageBody, ANSI_ORANGE);
    }
}
