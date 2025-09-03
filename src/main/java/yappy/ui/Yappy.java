package yappy.ui;

import java.util.Scanner;
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

	private record ParsedInput(String commandName, String args) {
	};

	private record CommandResult(String response, TaskList taskList) {
	};

	public Yappy() {
		this.taskList = attemptToLoadTasksFromBackup();
	}

	public String interact(String input) {
		ParsedInput parsedInput = parseInput(input);
		CommandResult result = executeCommand(parsedInput, taskList);
		this.taskList = result.taskList();
		return result.response();
	}

	public static String getGreetings() {
		return Constants.LOGO + "\n" + "Hello! I'm Yappy\n" + "What can I do for you?";
	}

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
			commandName = tokens[0];
			if (tokens.length > 1) {
				argStr = tokens[1];
			}
		}
		return new ParsedInput(commandName, argStr);
	}

	private static CommandResult executeCommand(ParsedInput parsedInput, TaskList taskList) {
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
			StringBuilder sb = new StringBuilder("Unknown command. Supported commands are:\n");
			for (Command validCommand : Command.values()) {
				sb.append(" - ").append(validCommand.getCommandInfo().name()).append("\n");
			}
			return new CommandResult(sb.toString(), taskList);
		});
	}
}
