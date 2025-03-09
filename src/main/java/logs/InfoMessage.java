package logs;

public class InfoMessage extends Message {
    private static final String ANSI_BLUE = "\u001B[34m";
  
    public static void send(String messageBody) {
        if (!isActive) return;
        printToConsole("[INFO]: " + messageBody, ANSI_BLUE);
    }
}