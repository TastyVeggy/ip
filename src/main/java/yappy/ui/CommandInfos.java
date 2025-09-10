package yappy.ui;

import java.util.List;
import java.util.stream.Collectors;

import yappy.task.TaskType;
import yappy.util.DateTimeUtil;

/**
 * Contains information of the commands supported by Yappy. Namely, the command name, the action
 * that the command aims to execute and the usage of the command.
 */
public class CommandInfos {

    /**
     * Record encapsulating the information related to a commmand.
     */
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

    public static final CommandInfo FIXED_DURATION = new CommandInfo("fixed_duration",
            "add fixed duration task",
            "fixed_duration " + TaskType.FIXED_DURATION.getArgsFormat() + generateDurationUsage());

    public static final CommandInfo FIND =
            new CommandInfo("find", "find tasks with given keyword", "find <keyword>");


    private static String generateDateTimeUsage() {
        List<String> usages = DateTimeUtil.getUsageForSupportedFormats();
        String s = "\n\nFor datetime, please use one of the following supported formats:\n"
                + usages.stream().map(u -> " - " + u).collect(Collectors.joining("\n"));
        return s;
    }

    private static String generateDurationUsage() {
        String s = "\n\nFor duration, please use XhYmZs (e.g. 2h15m30s)";
        return s;
    }
}
