package logs;

public class ErrorMessage extends Message {
    private static final String ANSI_RED = "\u001B[31m";

    public ErrorMessage(String message) {
        super(message);
    }

    @Override
    public void send() {
        printToConsole("[ERROR]: " + this.messageBody, ANSI_RED);
    }
}
