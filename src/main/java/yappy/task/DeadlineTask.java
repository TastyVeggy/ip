package yappy.task;

import java.time.LocalDateTime;

import yappy.task.exception.EmptyTaskDescriptionException;
import yappy.util.DateTimeUtil;

/**
 * Represents a task with a deadline
 */
public class DeadlineTask extends Task {
    private LocalDateTime deadline;

    /**
     * Creates a task with a deadline
     *
     * @param description The description of the task.
     * @param deadline The deadline of the task
     */
    public DeadlineTask(String description, LocalDateTime deadline)
            throws EmptyTaskDescriptionException {
        super(description);
        this.deadline = deadline;
    }

    /**
     * Returns the string representation of the deadline task, including its task type and deadline.
     *
     * @return The string representation of the deadline task.
     */
    @Override
    public String toString() {
        String s = "[D]" + super.toString() + " (by: " + DateTimeUtil.format(this.deadline) + ")";
        return s;
    }
}
