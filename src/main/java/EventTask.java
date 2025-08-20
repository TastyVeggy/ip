public class EventTask extends Task {
	private String from;
	private String to;

	/**
	 * Creates an Event, which is a Task with a from-to time period)
	 *
	 * @param description The description of the event.
	 * @param from The start of event.
	 * @param to The end of event.
	 */
	public EventTask(String description, String from, String to) {
		super(description);
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		String s = "[E]" 
			+ super.toString() 
			+ " (from: " 
			+ this.from
			+ " to: " 
			+ this.to
			+ ")";
		return s;
	}
	
}
