package logs;

public class ErrorMessage extends Message {
    private static final String ANSI_RED = "\u001B[31m";

    public static void send(String messageBody) {
        if (!isActive) return;
        printToConsole("[ERROR]: " + messageBody, ANSI_RED);
    }
}
