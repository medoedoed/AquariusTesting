package logs;

public class WarningMessage extends Message {
    private static final String ANSI_ORANGE = "\u001B[38;5;214m";


    public static void send(String messageBody) {
        if (!isActive) return;

        printToConsole("[WARNING]: " + messageBody, ANSI_ORANGE);
    }
}
