package yappy.task;

import java.io.Serializable;

import yappy.task.exception.EmptyTaskDescriptionException;

/**
 * Represents a task which has a description and can be marked or unmarked as done.
 */
public class Task implements Serializable {
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

    /**
     * Returns the icon corresponding to the status of the task. Task which are done returns the "X"
     * icon, while tasks which are not done returns " ".
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Mark task as done
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmark task as done
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns whether description contains the given keyword
     *
     * @param keyword Keyword to search in description
     * @return Whether description contains the given keyword
     */
    public boolean containsInDescription(String keyword) {
        return this.description.contains(keyword);
    }

    @Override
    public String toString() {
        String s = "[" + this.getStatusIcon() + "] " + this.description;
        return s;
    }
}
