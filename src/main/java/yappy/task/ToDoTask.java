package yappy.task;

import yappy.task.exception.EmptyTaskDescriptionException;

public class ToDoTask extends Task {

	/**
	 * Creates a ToDo which is essentially just a Task but the toString output is slightly different.
	 *
	 * @param description The description of the task.
	 */
	public ToDoTask(String description) throws EmptyTaskDescriptionException {
		super(description);
	}

	@Override
	public String toString() {
		String s = "[T]" + super.toString();
		return s;
	}
	
}
