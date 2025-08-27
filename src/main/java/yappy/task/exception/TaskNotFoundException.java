package yappy.task.exception;

/**
 * Represents an exception thrown to indicate that an attempt was made to access
 * a task at an index that does not exist in the task list.
 */
public class TaskNotFoundException extends TaskException {

    /**
     * Creates a TaskNotFoundException with a message indicating
     * the invalid task index that was accessed.
     *
     * @param taskIndex The index of the task that was not found.
     */
    public TaskNotFoundException(int taskIndex) {
        super("Task " + taskIndex + " does not exist.");
    }
    
}
