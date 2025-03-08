package logs;

public class WarningMessage extends Message {
    private static final String ANSI_ORANGE = "\u001B[38;5;214m";

    public WarningMessage(String messageBody) {
        super(messageBody);
    }

    @Override
    public void send() {
        printToConsole("[WARNING]: " + this.messageBody, ANSI_ORANGE);
    }
}
