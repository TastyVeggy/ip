package yappy.task.exception;

/**
 * Represents an exception thrown to indicate an error in saving task list to
 * the backup file.
 */
public class TaskListSaveBackupException extends TaskException {
    /**
     * Creates a TaskListSaveBackupException with diagnostic message.
     * 
     * @param e Original exception thrown resulting in the saving of task list.
     * to the backup file to be unsuccessful.
     */
    public TaskListSaveBackupException(Exception e) {
        super( "Unable to backup tasks due to: " + e.getMessage() + "\n\nFuture instances of this program may not have the most updated task list");
    }
}
