package logs;

public abstract class Message {
    protected String messageBody;

    public Message(String messageBody) {
        this.messageBody = messageBody;
    }

    abstract public void send();

    protected void printToConsole(String message, String colorCode) {
        System.out.println(colorCode + message + "\u001B[0m");
    }
}
