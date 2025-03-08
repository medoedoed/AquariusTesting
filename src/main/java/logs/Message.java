package logs;

public abstract class Message {
    protected static void printToConsole(String message, String colorCode) {
        System.out.println(colorCode + message + "\u001B[0m");
    }
}
