package yappy.task;

import yappy.task.exception.EmptyTaskDescriptionException;

/**
 * Represents a simple todo task with a description.
 */
public class ToDoTask extends Task {

	/**
	 * Creates a ToDo which is essentially just a Task but the toString output is slightly different.
	 *
	 * @param description The description of the task.
	 */
	public ToDoTask(String description) throws EmptyTaskDescriptionException {
		super(description);
	}

	/**
	 * Returns the string representation of the todo task.
	 * 
	 * @return  The string representation of the todo task.
	 */
	@Override
	public String toString() {
		String s = "[T]" + super.toString();
		return s;
	}
	
}
