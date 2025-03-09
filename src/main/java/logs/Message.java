package logs;

public abstract class Message {
    protected static boolean isActive = false;

    public static void disable() {
        isActive = false;
    }

    public static void enable() {
        isActive = true;
    }
    protected static void printToConsole(String message, String colorCode) {
        System.out.println(colorCode + message + "\u001B[0m");
    }
}
