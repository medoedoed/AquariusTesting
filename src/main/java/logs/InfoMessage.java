package logs;

public class InfoMessage extends Message {
    private static final String ANSI_BLUE = "\u001B[34m";

    public InfoMessage(String messageBody) {
        super(messageBody);
    }

    @Override
    public void send() {
        printToConsole("[INFO]: " + this.messageBody, ANSI_BLUE);
    }
}