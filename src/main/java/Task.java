public class Task {
	protected String description;
	protected boolean isDone;

	/**
	 * Creates a new Task with the given description.
	 *
	 * @param description The description of the task.
	 */
	public Task(String description) throws EmptyTaskDescriptionException {
		if (description == null || description.isBlank()) {
			throw new EmptyTaskDescriptionException();
		}
		this.description = description;
		this.isDone = false;
	}

	/** Returns the icon corresponding to the status of the task. Task which
	 * are done returns the "X" icon, while tasks which are not done returns 
	 * " ".
	 *
	 * @param description The description of the task.
	 * @return The status icon of the task
	 */
	public String getStatusIcon() {
		return (isDone ? "X" : " ");
	}

	/** Mark task as done
	 */
	public void markAsDone() {
		this.isDone = true;
	}

	/** Unmark task as done
	 */
	public void unmarkAsDone() {
		this.isDone = false;
	}

	@Override
	public String toString() {
		String s = "[" 
			+ this.getStatusIcon()
			+ "] "
			+ this.description;
		return s;
	}
}
