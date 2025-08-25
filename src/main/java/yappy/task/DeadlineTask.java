package yappy.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import yappy.task.exception.EmptyTaskDescriptionException;

public class DeadlineTask extends Task {
	private LocalDateTime deadline;

	/**
	 * Creates a task with a deadline
	 *
	 * @param description The description of the task.
	 * @param deadline    The deadline of the task
	 */
	public DeadlineTask(String description, LocalDateTime deadline) throws EmptyTaskDescriptionException {
		super(description);
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		String s = "[D]"
				+ super.toString()
				+ " (by: "
				+ this.deadline.format(DateTimeFormatter.ofPattern("MMM d yyy, h:mm a"))
				+ ")";
		return s;
	}

}
