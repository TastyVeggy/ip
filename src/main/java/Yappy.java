import java.util.Scanner;

public class Yappy {
	public static final String BREAKLINE = "_________________________________________";
	public static final String LOGO = "__   __                      \n"
		+ "\\ \\ / /_ _ _ __  _ __  _   _ \n"
		+ " \\ V / _` | '_ \\| '_ \\| | | |\n"
		+ "  | | (_| | |_) | |_) | |_| |\n"
		+ "  |_|\\__,_| .__/| .__/ \\__, |\n"
		+ "	  |_|   |_|    |___/\n";
	public static final String EXIT_COMMAND = "bye";

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
			echo(input);
			input = scanner.nextLine();
		}
	}

	private static void echo(String input) {
		printBreakLine();
		System.out.println(input);
		printBreakLine();
	}

	private static void exit() {
		System.out.println("Bye. Hope to see you again soon!");
		return;
	}
}
