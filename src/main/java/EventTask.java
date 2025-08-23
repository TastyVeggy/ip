import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {
	private LocalDateTime from;
	private LocalDateTime to;

	/**
	 * Creates an Event, which is a Task with a from-to time period)
	 *
	 * @param description The description of the event.
	 * @param from        The start of event.
	 * @param to          The end of event.
	 */
	public EventTask(String description, LocalDateTime from, LocalDateTime to) throws EmptyTaskDescriptionException {
		super(description);
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		String s = "[E]"
				+ super.toString()
				+ " (from: "
				+ this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"))
				+ " to: "
				+ this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"))
				+ ")";
		return s;
	}

}
