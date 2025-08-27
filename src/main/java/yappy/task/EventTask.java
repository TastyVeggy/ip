package yappy.task;

import java.time.LocalDateTime;

import yappy.task.exception.EmptyTaskDescriptionException;
import yappy.util.DateTimeUtil;

/**
 * Represents a task which has a start time and end time.
 */
public class EventTask extends Task {
	private LocalDateTime from;
	private LocalDateTime to;

	/**
	 * Creates an Event, which is a Task with a from-to time period)
	 *
	 * @param description The description of the event.
	 * @param from The start of event.
	 * @param to The end of event.
	 */
	public EventTask(String description, LocalDateTime from, LocalDateTime to)
			throws EmptyTaskDescriptionException {
		super(description);
		this.from = from;
		this.to = to;
	}

	/**
	 * Returns the string representation of the event task, including its task
	 * type, start and end.
	 * 
	 * @return  The string representation of the event task.
	 */
	@Override
	public String toString() {
		String s = "[E]" + super.toString() + " (from: " + DateTimeUtil.format(this.from) + " to: "
				+ DateTimeUtil.format(this.to) + ")";
		return s;
	}

}
