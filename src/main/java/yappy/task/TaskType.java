package yappy.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yappy.task.exception.EmptyTaskDescriptionException;
import yappy.task.exception.TaskException;
import yappy.task.exception.TaskInvalidArgsException;
import yappy.util.DateTimeUtil;

/**
 * Represents the different types of tasks supported by Yappy application.
 *
 * Each task type defines a factory method which creates the corresponding type of task given a raw
 * argument string.
 *
 * Supported task types are:
 * <ul>
 * <li>todo</li>
 * <li>deadline</li>
 * <li>event</li>
 * </ul>
 *
 */
public enum TaskType {
    /**
     * A todo task with a description.
     *
     * Format: {@code <description>}.
     */
    TODO("<description>") {
        @Override
        public ToDoTask create(String argStr) throws EmptyTaskDescriptionException {
            return new ToDoTask(argStr);
        }
    },
    /**
     * A deadline task with description and deadline.
     *
     * Format: {@code <description> /by <deadline>}.
     */
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
    /**
     * An event task with description, start time and end time.
     *
     * Format: {@code <description> /from <start (datetime)> /to <end (datetime)>}.
     */
    EVENT("<description> /from <start (datetime)> /to <end (datetime)>") {
        private final Pattern pattern =
                Pattern.compile("^(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+?)$");

        @Override
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


    /**
     * Creates a task of the current type using the given argument string.
     *
     * @param argStr The raw argument string used in constructing the task.
     * @return a Task instance of the corresponding type.
     * @throws TaskException If the argument string is invalid.
     */
    public abstract Task create(String argStr) throws TaskException;

    /**
     * Returns the expected argument format for this task type.
     *
     * @return String representation of the argument format.
     */
    public String getArgsFormat() {
        return argsFormat;
    }
}
