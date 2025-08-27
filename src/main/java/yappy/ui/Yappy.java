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
	public static void main(String[] args) {
		UiUtil.printBreakLine();
		greet();
		UiUtil.printBreakLine();
		runTaskProgram();
		UiUtil.printBreakLine();
	}

	private static void greet() {
		String greeting = Constants.LOGO + "\n" + "Hello! I'm Yappy\n" + "What can I do for you?";
		System.out.println(greeting);
	}

	private static TaskList attemptToLoadTasksFromBackup() {
		TaskList taskList;
		try {
			taskList = TaskList.usingBackup(Constants.TASKS_SAVE_FILE);
			System.out.println("Successfully synced tasks from backup");
		} catch (TaskListLoadBackupException e) {
			System.out.println(e.getMessage());
			taskList = new TaskList();
		}
		return taskList;

	}

	private static void runTaskProgram() {
		TaskList taskList = attemptToLoadTasksFromBackup();
		UiUtil.printBreakLine();

		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();

		while (true) {
			String commandName = "";
			String intermediateArgStr = "";
			if (!input.isBlank()) {
				String[] parsedInput = input.trim().split("\\s+", 2);
				commandName = parsedInput[0];
				if (parsedInput.length > 1) {
					intermediateArgStr = parsedInput[1];
				}
			}
			String argStr = intermediateArgStr;

			UiUtil.printBreakLine();

			Command.fromName(commandName).map(cmd -> {
				try {
					return cmd.execute(argStr, taskList);
				} catch (YappyException e) {
					return e.getMessage();
				}
			}).ifPresentOrElse(result -> {
				System.out.println(result);
				try {
					taskList.save(Constants.TASKS_SAVE_FILE);
				} catch (TaskListSaveBackupException e) {
					System.out.println(e.getMessage());
				}
			}, () -> {
				System.out.println("Unkown command. Supported commands are:");
				for (Command validCommand : Command.values()) {
					System.out.println(" - " + validCommand.getCommandInfo().name());
				}
			});


			if (commandName.equals(Command.EXIT.getCommandInfo().name())) {
				break;
			}
			UiUtil.printBreakLine();
			input = scanner.nextLine();
		}
		scanner.close();
	}
}
