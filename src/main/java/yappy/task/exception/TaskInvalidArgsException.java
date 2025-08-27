package yappy.task.exception;

/**
 * Represents an exception thrown to indicate the argStr used in the creation of the task is of the
 * incorrect format.
 * 
 * It includes the proper format of the argStr for creating the relevant task.
 */
public class TaskInvalidArgsException extends TaskException {
    /**
     * Creates a TaskInvalidArgsExeption with the appropriate args format.
     * 
     * @param argsFormat Format of the argStr for creating the relevant task.
     */
    public TaskInvalidArgsException(String argsFormat) {
        super(argsFormat);
    }
}
