package yappy.task.exception;

/**
 * Represents an exception thrown to indicate that empty description has been given to a task.
 */
public class EmptyTaskDescriptionException extends TaskException {

    /**
     * Creates an EmptyTaskDescriptionException with its predefined error message.
     */
    public EmptyTaskDescriptionException() {
        super("The description of a task cannot be empty!!!");
    }
}
