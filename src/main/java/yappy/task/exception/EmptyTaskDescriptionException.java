package yappy.task.exception;
public class EmptyTaskDescriptionException extends Exception {
    public EmptyTaskDescriptionException() {
        super("The description of a task cannot be empty!!!");
    }
    
}
