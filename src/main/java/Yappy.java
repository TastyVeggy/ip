import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
public class Yappy {
	public static final String BREAKLINE = "_________________________________________";
	public static final String LOGO = "__   __                      \n"
		+ "\\ \\ / /_ _ _ __  _ __  _   _ \n"
		+ " \\ V / _` | '_ \\| '_ \\| | | |\n"
		+ "  | | (_| | |_) | |_) | |_| |\n"
		+ "  |_|\\__,_| .__/| .__/ \\__, |\n"
		+ "	  |_|   |_|    |___/\n";
	public static final String EXIT_COMMAND = "bye";

	private static final Map<String, YappyConsumer<String>> commands = Map.of(
		"list", s -> listTask(s),
		"mark", s -> markTask(s),
		"unmark", s -> unmarkTask(s),
		"todo", s -> addToDoTask(s),
		"deadline", s -> addDeadlineTask(s),
		"event", s -> addEventTask(s),
		"delete", s -> deleteTask(s)
	);
	private static ArrayList<Task> tasks = new ArrayList<>();
	// private static int taskCount = 0;

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
				String[] parsedInput = input.trim().split("\\s+", 2);
				command = parsedInput[0];
				if (parsedInput.length > 1) {
					argument = parsedInput[1];
				}
			}

			printBreakLine();
			if (commands.containsKey(command)) {
				try {
					commands.get(command).accept(argument);
				} catch (YappyException e) {
					System.out.println(e.getMessage());
				}
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

		scanner.close();
	}

	private static void addToDoTask(String description) throws YappyException {
		ToDoTask toDoTask;
		try {
			toDoTask = new ToDoTask(description);
		} catch (EmptyTaskDescriptionException e) {
			throw new YappyException(e.getMessage());
		}
		storeTask(toDoTask);
	}

	private static void addDeadlineTask(String constructionString) throws YappyException{
		String description = null;
		String deadline = null;
		if (!constructionString.isBlank()) {
			String[] parsedArgument = constructionString.split("/by ");
			if (parsedArgument.length == 2) {
				description = parsedArgument[0].trim();
				deadline = parsedArgument[1].trim();
			}
		}

		if (deadline != null) {
			DeadlineTask deadlineTask;
			try {
				deadlineTask = new DeadlineTask(description, deadline);
			} catch (EmptyTaskDescriptionException e) {
				throw new YappyException(e.getMessage());
			}
			storeTask(deadlineTask);
		} else {
			throw new YappyInputException("create a deadline task", "deadline <description> /by <deadline>");
		}
	}

	private static void addEventTask(String constructionString) throws YappyException {
		String description = null;
		String from = null;
		String to = null;
		if (!constructionString.isBlank()) {
			String[] semiParsedArgument = constructionString.split("/from ");
			if (semiParsedArgument.length == 2) {
				description = semiParsedArgument[0].trim();
				String fromAndToString = semiParsedArgument[1].trim();

				String[] fromAndTo = fromAndToString.split("/to ");

				if (fromAndTo.length == 2) {
					from = fromAndTo[0].trim();
					to = fromAndTo[1].trim();
				}
			}
		}

		EventTask eventTask;
		if (from != null && to != null) {
			try {
				eventTask = new EventTask(description, from, to);
			} catch (EmptyTaskDescriptionException e) {
				throw new YappyException(e.getMessage());
			}
			storeTask(eventTask);
		} else {
			throw new YappyInputException("create an event task", "event <description> /from <start> /to <end>");
		}
	}

	private static void storeTask(Task task) {
		tasks.add(task);
		System.out.println("Got it. I've added this task:");
		System.out.println("  " + task);
		System.out.printf("Now you have %d task", tasks.size());
		if (tasks.size() > 1) {
			System.out.print("s");

		}
		System.out.println(" in the list.");
	}

	private static void listTask(String arg) throws YappyInputException{
		if (!arg.isBlank()) {
			throw new YappyInputException("list tasks", "list");

		}
		System.out.println("Here are the tasks in your list:");
		for (int i = 0; i < tasks.size(); i++) {
			System.out.printf("%d.%s%n", i + 1, tasks.get(i));
		}
	}

	private static void markTask(String taskIndexStr) throws YappyException {
		int taskIndex;
		try {
			System.out.println(taskIndexStr);
			taskIndex = Integer.parseInt(taskIndexStr);
		} catch (NumberFormatException e) {
			throw new YappyInputException("mark task", "mark <task index (Arabic numerical)>");
		}
		if (taskIndex > tasks.size()) {
			throw new YappyTaskNotFoundException(taskIndex);
		}
		Task task = tasks.get(taskIndex - 1);
		task.markAsDone();
		System.out.println("Nice! I've marked this task as done:\n  " + task);
	}

	private static void unmarkTask(String taskIndexStr) throws YappyException {
		int taskIndex;
		try {
			taskIndex = Integer.parseInt(taskIndexStr);
		} catch (NumberFormatException e) {
			throw new YappyInputException("unmark task", "unmark <task index (Arabic numerical)>");
		}
		if (taskIndex > tasks.size()) {
			throw new YappyTaskNotFoundException(taskIndex);
		}
		Task task = tasks.get(taskIndex - 1);
		task.unmarkAsDone();
		System.out.println("OK, I've marked this task as not done yet:\n  " + task);
	}

	private static void deleteTask(String taskIndexStr) throws YappyException {
		int taskIndex;
		try {
			taskIndex = Integer.parseInt(taskIndexStr);
		} catch (NumberFormatException e) {
			throw new YappyInputException("delete task", "delete <task index (Arabic numerical)>");
		}
		if (taskIndex > tasks.size()) {
			throw new YappyTaskNotFoundException(taskIndex);
		}
		Task task = tasks.get(taskIndex - 1);
		tasks.remove(taskIndex - 1);
		System.out.println("Noted, I've removed this task:\n  " + task);
		System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
	}

	private static void exit() {
		System.out.println("Bye. Hope to see you again soon!");
		return;
	}
}
