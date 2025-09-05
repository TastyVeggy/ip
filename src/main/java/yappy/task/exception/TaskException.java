package yappy.task.exception;

/**
 * Represents an exception thrown to indicate an error in saving task list to the backup file.
 */
public class TaskException extends Exception {
    /**
     * Creates a TaskException with the specified message.
     *
     * @param message The error message.
     */
    public TaskException(String message) {
        super(message);
    }
}
