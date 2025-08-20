import java.util.Scanner;
import java.util.Map;
import java.util.function.Consumer;

public class Yappy {
	public static final String BREAKLINE = "_________________________________________";
	public static final String LOGO = "__   __                      \n"
		+ "\\ \\ / /_ _ _ __  _ __  _   _ \n"
		+ " \\ V / _` | '_ \\| '_ \\| | | |\n"
		+ "  | | (_| | |_) | |_) | |_| |\n"
		+ "  |_|\\__,_| .__/| .__/ \\__, |\n"
		+ "	  |_|   |_|    |___/\n";
	public static final String EXIT_COMMAND = "bye";

	private static final Map<String, Consumer<String>> commands = Map.of(
		"list", s -> listTask(),
		"mark", s -> markTask(s),
		"unmark", s -> unmarkTask(s),
		"todo", s -> addToDoTask(s),
		"deadline", s -> addDeadlineTask(s)
	);
	private static Task[] tasks = new Task[100];
	private static int taskCount = 0;

    public static void main(String[] args) {
		printBreakLine();
		greet();
		printBreakLine();
		listenInputAndRespond();
		printBreakLine();
		exit();
		printBreakLine();
    }

	private static void printBreakLine() {
		System.out.println(BREAKLINE);
	}

	private static void greet() {
		String greeting = Yappy.LOGO + "\n"
		 + "Hello! I'm Yappy\n"
		 + "What can I do for you?";
		System.out.println(greeting);
	}

	private static void listenInputAndRespond() {
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();

		while (!input.equals(EXIT_COMMAND)) {
			String command = "";
			String argument = "";
			if (!input.isBlank()) {
				String[] parsed_input = input.trim().split("\\s+", 2);
				command = parsed_input[0];
				if (parsed_input.length > 1) {
					argument = parsed_input[1];
				}
			}

			printBreakLine();
			if (commands.containsKey(command)) {
				commands.get(command).accept(argument);
			} else {
				System.out.println("Please start your input with a valid command. List of supported commands:");
				for (String validCommand : commands.keySet()) {
					System.out.println("  " + validCommand);
				}
				System.out.println("  " + EXIT_COMMAND);
			}
			printBreakLine();
			input = scanner.nextLine();
		}
	}

	private static void addToDoTask(String description) {
		ToDoTask toDoTask = new ToDoTask(description);
		storeTask(toDoTask);
	}

	private static void addDeadlineTask(String constructionString) {
		String description = null;
		String deadline = null;
		if (!constructionString.isBlank()) {
			String[] parsed_argument = constructionString.trim().split(" /by ");
			if (parsed_argument.length == 2) {
				description = parsed_argument[0];
				deadline = parsed_argument[1];
			}
		}

		if (description != null && deadline != null) {
			DeadlineTask deadlineTask = new DeadlineTask(description, deadline);
			storeTask(deadlineTask);
		} else {
			System.out.println("For a deadline task, please have your input be of the form");
			System.out.println("  daedline <description> \\by <deadline>");
		}
	}

	private static void storeTask(Task task) {
		tasks[taskCount] = task;
		taskCount++;
		System.out.println("Got it. I've added this task:");
		System.out.println("  " + task);
		System.out.printf("Now you have %d task", taskCount);
		if (taskCount > 1) {
			System.out.print("s");

		}
		System.out.println(" in the list");
	}

	private static void listTask() {
		System.out.println("Here are the tasks in your list:");
		for (int i = 0; i < taskCount; i++) {
			System.out.printf("%d.%s%n", i + 1, tasks[i]);
		}
	}

	private static void markTask(String taskIndexStr) {
		try {
			int taskIndex = Integer.parseInt(taskIndexStr);
			if (taskIndex > taskCount) {
				System.out.printf("Task %d does not exist. Use `list` to find a valid task.%n", taskIndex);
				return;
			}
			Task task = tasks[taskIndex - 1];
			task.markAsDone();
			System.out.println("Nice! I've marked this task as done:\n  " + task);
		} catch (NumberFormatException e) {
			System.out.println("Please use Arabic numericals (i.e. 1, 2, 3, ...) to indicate which task to be marked.");
		}
	}

	private static void unmarkTask(String taskIndexStr) {
		try {
			int taskIndex = Integer.parseInt(taskIndexStr);
			if (taskIndex > taskCount) {
				System.out.printf("Task %d does not exist. Use `list` to find a valid task.%n", taskIndex);
				return;
			}
			Task task = tasks[taskIndex - 1];
			task.unmarkAsDone();
			System.out.println("OK, I've marked this task as not done yet:\n  " + task);
		} catch (NumberFormatException e) {
			System.out.println("Please use Arabic numericals (i.e. 1, 2, 3, ...) to indicate which task to be unmarked.");
		}
	}

	private static void exit() {
		System.out.println("Bye. Hope to see you again soon!");
		return;
	}
}
