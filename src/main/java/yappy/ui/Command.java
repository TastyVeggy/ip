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

public enum Command {
    LIST(CommandInfos.LIST) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            if (!argStr.isBlank()) {
                throw new YappyInputException(getCommandInfo());
            }
            return render(taskList);
        }

        private String render(TaskList taskList) {
            return "Here are the tasks in your list:\n" 
                    + taskList.getListStr();
        }
    },
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
            } catch(TaskNotFoundException e) {
                throw new YappyException(e.getMessage());
            }
        }
    },
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
                String s = "Noted, I've removed this task:\n " 
                        + deletedTask
                        + String.format("%nNow you have %d tasks in this list.%n", taskList.getSize());
                return s;
            } catch(TaskNotFoundException e) {
                throw new YappyException(e.getMessage());
            }
        }
    },
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
    EXIT(CommandInfos.EXIT) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            if (!argStr.isBlank()) {
                throw new YappyInputException(getCommandInfo());
            }

            return Constants.BYE_MESSAGE;
        }
    },
    TODO(CommandInfos.TODO) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            return executeTaskAddition(
                argStr,
                taskList,
                TaskType.TODO,
                getCommandInfo());
        }
    },
    DEADLINE(CommandInfos.DEADLINE) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            return executeTaskAddition(
                argStr,
                taskList,
                TaskType.DEADLINE,
                getCommandInfo());
        }
    },
    EVENT(CommandInfos.EVENT) {
        public String execute(String argStr, TaskList taskList) throws YappyException {
            return executeTaskAddition(
                argStr,
                taskList,
                TaskType.EVENT,
                getCommandInfo());
        }
    };

    private CommandInfo commandInfo;

    private Command(CommandInfo commandInfo) {
        this.commandInfo = commandInfo;
    }

    public CommandInfo getCommandInfo() {
        return this.commandInfo;
    }


    public static Optional<Command> fromName(String commandName) {
        return Arrays.stream(Command.values())
            .filter(c -> c.commandInfo.name().equals(commandName))
            .findFirst();
    }

    public abstract String execute(String argStr, TaskList taskList) throws YappyException;

    private static String executeTaskAddition(String argStr, TaskList taskList, TaskType taskType, CommandInfo commandUsage) throws YappyException {
            Task task;
            try {
                task = taskType.create(argStr);
            } catch (TaskInvalidArgsException e) {
                throw new YappyInputException(commandUsage);
            } catch (TaskException e) {
                throw new YappyException(e.getMessage());
            }
            taskList.add(task);
            String s = "Got it. I've added this task:\n " 
                    + task
                    + String.format("\nNow you have %d task", taskList.getSize());
            if (taskList.getSize() > 1) {
                s += "s";
            }

            s += " in the list.";
            return s;
    }
}
