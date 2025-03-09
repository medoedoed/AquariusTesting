package logs;

public class ErrorMessage extends Message {
    private static final String ANSI_RED = "\u001B[31m";
    private static boolean isActive = true;

    public static void disable() {
        isActive = false;
    }

    public static void enable() {
        isActive = true;
    }

    public static void send(String messageBody) {
        if (!isActive) return;
        printToConsole("[ERROR]: " + messageBody, ANSI_RED);
    }
}
