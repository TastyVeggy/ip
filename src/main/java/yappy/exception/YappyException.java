package yappy.exception;

/**
 * Represents an exception thrown to indicate an error specific to Yappy application. Exception
 * messages are automatically prefixed with "Yappy cannot yap!!!".
 */
public class YappyException extends Exception {

    /**
     * Creates a YappyException with the specified message.
     *
     * @param message The error message.
     */
    public YappyException(String message) {
        super("Yappy cannot yap!!! " + message);
    }
}
