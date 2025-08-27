package yappy.task.exception;

/**
 * Represents an exception thrown to indicate an error in loading task list from
 * the backup file.
 */
public class TaskListLoadBackupException extends TaskException {
    /**
     * Creates a TaskListLoadBackupException with diagnostic message.
     * 
     * @param e Original exception thrown resulting in the loading of task list
     * from the backup file to be unsuccessful.
     * @param filepath Filepath of the backup file.
     */
    public TaskListLoadBackupException(Exception e, String filepath) {
        super(
                "Unable to restore tasks due to: " + e.getMessage()
                        + "\n\nThere may have been no previous instance of this program or the "
                        + filepath 
                        + " file has been deleted.");

    }
    
}
