package yappy.ui;

import java.util.Scanner;
import java.util.StringJoiner;

import yappy.Constants;
import yappy.exception.YappyException;
import yappy.task.TaskList;
import yappy.task.exception.TaskListLoadBackupException;
import yappy.task.exception.TaskListSaveBackupException;
import yappy.util.UiUtil;

/**
 * Represents the all-important program, Yappy, a full-fledged task tracking application that
 * supports the following types of tasks:
 * <ul>
 * <li>todo</li>
 * <li>deadline</li>
 * <li>event</li>
 * </ul>
 */
public class Yappy {
    private TaskList taskList;
    private String commandName;

    private record ParsedInput(String commandName, String args) {
    };

    private record CommandResult(String response, TaskList taskList) {
    };

    /**
     * Creates Yappy instance that powers the chatbot within the GUI Yappy program.
     */
    public Yappy() {
        this.taskList = attemptToLoadTasksFromBackup();
        assert this.taskList != null : "Tasklist should be initialised";
    }

    /**
     * Returns the response from the chatbot program given the input from the user.
     *
     * @param input The input by the user of the GUI Yappy program.
     * @return The response from Yappy given the user input.
     */
    public String interact(String input) {
        ParsedInput parsedInput = parseInput(input);
        this.commandName = parsedInput.commandName();

        CommandResult result = executeCommand(parsedInput, taskList);

        this.taskList = result.taskList();
        return result.response();
    }

    /**
     * Returns the name of the most recent command.
     *
     * @return The most recent command name;
     */
    public String getCommandName() {
        return this.commandName;
    }

    /**
     * Returns the greetings from Yappy.
     *
     * @return The greetings from Yappy.
     */
    public static String getGreetings() {
        return "Hello! I'm Yappy\n" + "What can I do for you?";
    }

    /**
     * Runs the CLI Yappy program.
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        UiUtil.printBreakLine();
        greet();
        UiUtil.printBreakLine();
        runTaskCliProgram();
        UiUtil.printBreakLine();
    }

    private static void greet() {
        System.out.println(getGreetings());
    }

    private static void runTaskCliProgram() {
        TaskList taskList = attemptToLoadTasksFromBackup();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (true) {
            ParsedInput parsedInput = parseInput(input);

            UiUtil.printBreakLine();

            CommandResult result = Yappy.executeCommand(parsedInput, taskList);
            taskList = result.taskList();
            System.out.println(result.response());

            // exit program when given exit command
            if (parsedInput.commandName().equals(Command.EXIT.getCommandInfo().name())) {
                break;
            }

            UiUtil.printBreakLine();
            input = scanner.nextLine();
        }
        scanner.close();
    }

    private static TaskList attemptToLoadTasksFromBackup() {
        TaskList taskList;
        try {
            taskList = TaskList.usingBackup(Constants.TASKS_SAVE_FILE);
        } catch (TaskListLoadBackupException e) {
            taskList = new TaskList();
        }
        return taskList;

    }

    private static ParsedInput parseInput(String input) {
        String commandName = "";
        String argStr = "";
        if (!input.isBlank()) {
            String[] tokens = input.trim().split("\\s+", 2);

            assert tokens.length >= 1 : "Split should always produce at least one token";

            commandName = tokens[0];
            if (tokens.length > 1) {
                argStr = tokens[1];
            }
        }

        assert commandName != null : "Command name should not be null";
        assert argStr != null : "Arg string should not be null";
        return new ParsedInput(commandName, argStr);
    }

    private static CommandResult executeCommand(ParsedInput parsedInput, TaskList taskList) {
        assert parsedInput != null : "ParsedInput should not be null";
        assert parsedInput.commandName() != null : "Command name should not be null";
        assert parsedInput.args() != null : "Args should not  be null";
        assert taskList != null : "TaskList should not be null";
        return Command.fromName(parsedInput.commandName()).map(cmd -> {
            try {
                String response = cmd.execute(parsedInput.args(), taskList);
                return new CommandResult(response, taskList);
            } catch (YappyException e) {
                return new CommandResult(e.getMessage(), taskList);
            }
        }).map(result -> {
            try {
                taskList.save(Constants.TASKS_SAVE_FILE);
                return result;
            } catch (TaskListSaveBackupException e) {
                return new CommandResult(result.response() + "\n" + e.getMessage(),
                        result.taskList());
            }
        }).orElseGet(() -> {
            StringJoiner joiner =
                    new StringJoiner("\n -", "Unknown command. Supported commands are:\n -", "");
            for (Command validCommand : Command.values()) {
                joiner.add(validCommand.getCommandInfo().name());
            }
            return new CommandResult(joiner.toString(), taskList);
        });
    }

}
