package yappy.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yappy.task.exception.EmptyTaskDescriptionException;
import yappy.task.exception.TaskException;
import yappy.task.exception.TaskInvalidArgsException;
import yappy.util.DateTimeUtil;

public enum TaskType {
    TODO("<description>") {
        public ToDoTask create(String argStr) throws EmptyTaskDescriptionException {
            return new ToDoTask(argStr);
        }
    },
    DEADLINE("<description> /by <deadline (datetime)>") {
        private final Pattern pattern = Pattern.compile("^(.+?)\\s+/by\\s+(.+?)$");

        @Override
        public DeadlineTask create(String argStr) throws TaskException {
            Matcher matcher = pattern.matcher(argStr);
            if (!matcher.matches()) {
                throw new TaskInvalidArgsException(getArgsFormat());
            }

            String description = matcher.group(1).trim();
            String by = matcher.group(2).trim();

            try {
                LocalDateTime deadline = DateTimeUtil.parse(by);
                return new DeadlineTask(description, deadline);
            } catch (DateTimeParseException e) {
                throw new TaskInvalidArgsException(getArgsFormat());
            }
        }
    },
    EVENT("<description> /from <start (datetime)> /to <end (datetime)>") {
        private final Pattern pattern = Pattern.compile("^(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+?)$");

        public EventTask create(String argStr) throws TaskException {
            Matcher matcher = pattern.matcher(argStr);
            if (!matcher.matches()) {
                throw new TaskInvalidArgsException(getArgsFormat());
            }

            String description = matcher.group(1).trim();
            String fromStr = matcher.group(2).trim();
            String toStr = matcher.group(3).trim();


            try {
                LocalDateTime from = DateTimeUtil.parse(fromStr);
                LocalDateTime to = DateTimeUtil.parse(toStr);
                return new EventTask(description, from, to);
            } catch (DateTimeParseException e) {
                throw new TaskInvalidArgsException(getArgsFormat());
            }
        }
    };
    private final String argsFormat;

    private TaskType(String argsFormat) {
        this.argsFormat = argsFormat;
    }


    public abstract Task create(String argStr) throws TaskException;

    public String getArgsFormat() {
        return argsFormat;
    }
}
