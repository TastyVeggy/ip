package yappy.task.exception;

public class TaskListSaveBackupException extends TaskException {
    public TaskListSaveBackupException(Exception e) {
        super( "Unable to backup tasks due to: " + e.getMessage() + "\n\nFuture instances of this program may not have the most updated task list");
    }
}
