package yappy.task.exception;
public class EmptyTaskDescriptionException extends TaskException {
    public EmptyTaskDescriptionException() {
        super("The description of a task cannot be empty!!!");
    }
    
}
