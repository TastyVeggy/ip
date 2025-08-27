package yappy.util;

public class UiUtil {
    public static final String BREAKLINE = "_________________________________________";

    private UiUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Prints a breakline
     */
    public static void printBreakLine() {
        System.out.println(BREAKLINE);
    }
}
