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
		"list", s -> listTask()
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
				commands.get(command).accept(input);
			} else {
				addTask(input);
			}
			printBreakLine();
			input = scanner.nextLine();
		}
	}

	private static void addTask(String taskDescription) {
		Task task = new Task(taskDescription);
		tasks[taskCount] = task;
		taskCount++;
		System.out.println("added: " + task);
	}

	private static void listTask() {
		for (int i = 0; i < taskCount; i++) {
			System.out.printf("%d. %s%n", i + 1, tasks[i]);
		}
	}

	private static void exit() {
		System.out.println("Bye. Hope to see you again soon!");
		return;
	}
}
