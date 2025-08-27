package yappy.util;

/**
 * Utility class for ui of Yappy application.
 */
public class UiUtil {
	public static final String BREAKLINE = "_________________________________________";

    private UiUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Prints a breakline.
     */
	public static void printBreakLine() {
		System.out.println(BREAKLINE);
	}

    
}
