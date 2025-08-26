package yappy.task.exception;

public class TaskNotFoundException extends TaskException {
    public TaskNotFoundException(int taskIndex) {
        super("Task " + taskIndex + " does not exist.");
    }
    
}
