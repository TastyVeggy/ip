package yappy.task.exception;

public class TaskInvalidArgsException extends TaskException {
    public TaskInvalidArgsException(String argsFormat) {
        super(argsFormat);
    }
}
