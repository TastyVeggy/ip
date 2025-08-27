package yappy.ui;

import yappy.task.TaskType;
import yappy.util.DateTimeUtil;

public class CommandInfos {

    public record CommandInfo(String name, String action, String usage) {
    }

    public static final CommandInfo LIST = new CommandInfo("list", "list task", "list");

    public static final CommandInfo MARK =
            new CommandInfo("mark", "mark task", "mark <task index (positive Arabic numerical)");

    public static final CommandInfo UNMARK = new CommandInfo("unmark", "unmark task",
            "unmark <task index (positive Arabic numerical)>");

    public static final CommandInfo DELETE = new CommandInfo("delete", "delete task",
            "delete <task index (positive Arabic numerical)");

    public static final CommandInfo EXIT = new CommandInfo("bye", "exit", "bye");

    public static final CommandInfo TODO =
            new CommandInfo("todo", "add todo task", "todo " + TaskType.TODO.getArgsFormat());

    public static final CommandInfo DEADLINE = new CommandInfo("deadline", "add deadline task",
            "deadline " + TaskType.DEADLINE.getArgsFormat() + generateDateTimeUsage());

    public static final CommandInfo EVENT = new CommandInfo("event", "add event task",
            "event " + TaskType.EVENT.getArgsFormat() + generateDateTimeUsage());


    private static String generateDateTimeUsage() {
        String s = "\n\nFor datetime, please use one of the following supported formats:\n";
        for (String usage : DateTimeUtil.getUsageForSupportedFormats()) {
            s += " - " + usage + "\n";
        }
        return s;
    }
}
