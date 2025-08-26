package yappy.task.exception;

public class TaskListLoadBackupException extends TaskException {
    public TaskListLoadBackupException(Exception e, String filepath) {
        super(
                "Unable to restore tasks due to: " + e.getMessage()
                        + "\n\nThere may have been no previous instance of this program or the "
                        + filepath 
                        + " file has been deleted.");

    }
    
}
