package yappy.ui;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yappy.Constants;
import yappy.exception.YappyException;
import yappy.exception.YappyInputException;
import yappy.task.Task;
import yappy.task.TaskList;
import yappy.task.TaskType;
import yappy.task.exception.TaskException;
import yappy.task.exception.TaskInvalidArgsException;
import yappy.task.exception.TaskNotFoundException;
import yappy.ui.CommandInfos.CommandInfo;

/**
 * Represents all user-executable commands in Yappy.
 * 
 * Each command encapsulates its execution. Execution consists of processing user input, handling
 * the business logic and how the output to user is rendered.
 * 
 * Supported commands:
 * <ul>
 * <li>list</li>
 * <li>todo</li>
 * <li>deadline</li>
 * <li>event</li>
 * <li>mark</li>
 * <li>unmark</li>
 * <li>delete</li>
 * <li>bye</li>
 * </ul>
 */
public enum Command {
    /**
     * Displays all tasks in the current task list.
     * 
     * Format: {@code list}.
     */
    LIST(CommandInfos.LIST) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            if (!argStr.isBlank()) {
                throw new YappyInputException(getCommandInfo());
            }
            return render(taskList);
        }

        private String render(TaskList taskList) {
            return "Here are the tasks in your list:\n" + taskList.getListStr();
        }
    },
    /**
     * Mark a task within the task list as done.
     * 
     * Format: {@code mark <task index>}.
     */
    MARK(CommandInfos.MARK) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            final Pattern pattern = Pattern.compile("^(\\d+)$");

            Matcher matcher = pattern.matcher(argStr.trim());
            if (!matcher.matches()) {
                throw new YappyInputException(getCommandInfo());
            }

            int index = Integer.parseInt(matcher.group(1));
            try {
                Task task = taskList.markTask(index);
                return "Nice! I've marked this task as done:\n" + task;
            } catch (TaskNotFoundException e) {
                throw new YappyException(e.getMessage());
            }
        }
    },
    /**
     * Delete a task within the task list.
     * 
     * Format: {@code delete <task index>}.
     */
    DELETE(CommandInfos.DELETE) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            final Pattern pattern = Pattern.compile("^(\\d+)$");

            Matcher matcher = pattern.matcher(argStr.trim());
            if (!matcher.matches()) {
                throw new YappyInputException(getCommandInfo());
            }

            int index = Integer.parseInt(matcher.group(1));
            try {
                Task deletedTask = taskList.deleteTask(index);
                String s = "Noted, I've removed this task:\n " + deletedTask + String
                        .format("%nNow you have %d tasks in this list.%n", taskList.getSize());
                return s;
            } catch (TaskNotFoundException e) {
                throw new YappyException(e.getMessage());
            }
        }
    },
    /**
     * Unmark a task within the task list as done.
     * 
     * Format: {@code unmark <task index>}.
     */
    UNMARK(CommandInfos.UNMARK) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            final Pattern pattern = Pattern.compile("^(\\d+)$");

            Matcher matcher = pattern.matcher(argStr.trim());
            if (!matcher.matches()) {
                throw new YappyInputException(getCommandInfo());
            }

            int index = Integer.parseInt(matcher.group(1));
            try {
                Task task = taskList.unmarkTask(index);
                return "Nice! I've unmarked this task as done:\n" + task;
            } catch (TaskNotFoundException e) {
                throw new YappyException(e.getMessage());
            }
        }

    },
    /**
     * Exit Yappy application.
     * 
     * Format: {@code bye}.
     */
    EXIT(CommandInfos.EXIT) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            if (!argStr.isBlank()) {
                throw new YappyInputException(getCommandInfo());
            }

            return Constants.BYE_MESSAGE;
        }
    },
    /**
     * Add a todo task to the task list.
     * 
     * Format: {@code todo <description>}.
     */
    TODO(CommandInfos.TODO) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            return executeTaskAddition(argStr, taskList, TaskType.TODO, getCommandInfo());
        }
    },
    /**
     * Add a deadline task to the task list.
     * 
     * Format: {@code deadline <description> /by <deadline>}.
     */
    DEADLINE(CommandInfos.DEADLINE) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            return executeTaskAddition(argStr, taskList, TaskType.DEADLINE, getCommandInfo());
        }
    },
    /**
     * Add an event to the task list.
     * 
     * Format: {@code event <description> /from <start> /to <end>}.
     */
    EVENT(CommandInfos.EVENT) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            return executeTaskAddition(argStr, taskList, TaskType.EVENT, getCommandInfo());
        }
    },
    /**
     * Find tasks from the task list using a given keyword.
     * 
     * Format :{@code find <keyword>}.
     */
    FIND(CommandInfos.FIND) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            if (argStr.isBlank()) {
                throw new YappyInputException(getCommandInfo());
            }
            return taskList.getShortListWithKeyword(argStr).getListStr();
        }
    };

    private CommandInfo commandInfo;

    private Command(CommandInfo commandInfo) {
        this.commandInfo = commandInfo;
    }

    /**
     * Returns metadata about this command, including name, action and help.
     * 
     * @return The {@code CommandInfo} for this command.
     */
    public CommandInfo getCommandInfo() {
        return this.commandInfo;
    }

    /**
     * Finds a {@code Command} by its name by matching against {@code
     * commandInfo.name()}.
     * 
     * @param commandName The command name.
     * @return An {@code Optional} containing the found command, or empty if not found.
     */
    public static Optional<Command> fromName(String commandName) {
        return Arrays.stream(Command.values()).filter(c -> c.commandInfo.name().equals(commandName))
                .findFirst();
    }

    /**
     * Executes the command with the given argument string and task list. Execution involves parsing
     * the raw argument string, handling the business logic and rendering the output.
     * 
     * @param argStr The raw argument string.
     * @param taskList The current task list.
     * @return The rendered output.
     * @throws YappyException If arguments are invalid or task creation fails.
     */
    public abstract String execute(String argStr, TaskList taskList) throws YappyException;

    private static String executeTaskAddition(String argStr, TaskList taskList, TaskType taskType,
            CommandInfo commandUsage) throws YappyException {
        Task task;
        try {
            task = taskType.create(argStr);
        } catch (TaskInvalidArgsException e) {
            throw new YappyInputException(commandUsage);
        } catch (TaskException e) {
            throw new YappyException(e.getMessage());
        }
        taskList.add(task);
        String s = "Got it. I've added this task:\n " + task
                + String.format("\nNow you have %d task", taskList.getSize());
        if (taskList.getSize() > 1) {
            s += "s";
        }

        s += " in the list.";
        return s;
    }
}
