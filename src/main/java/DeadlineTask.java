public class DeadlineTask extends Task {
	private String deadline;

	/**
	 * Creates a task with a deadline
	 *
	 * @param description The description of the task.
	 * @param deadline The deadline of the task
	 */
	public DeadlineTask(String description, String deadline) {
		super(description);
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		String s = "[D]" 
			+ super.toString() 
			+ " (by: " 
			+ this.deadline 
			+ ")";
		return s;
	}
	
}
