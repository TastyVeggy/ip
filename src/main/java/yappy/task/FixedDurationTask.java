package yappy.task;

import java.time.Duration;

import yappy.task.exception.EmptyTaskDescriptionException;
import yappy.util.DurationUtil;

/**
 * Represents a task which has a fixed duration.
 */
public class FixedDurationTask extends Task {
    private Duration duration;

    /**
     * Creates a task with a fixed duration.
     *
     * @param description The description of the task.
     * @param duration The duration of the task.
     */
    public FixedDurationTask(String description, Duration duration)
            throws EmptyTaskDescriptionException {
        super(description);
        this.duration = duration;
    }

    /**
     * Returns the string representation of the event task, including its task type, start and end.
     *
     * @return The string representation of the event task.
     */
    @Override
    public String toString() {
        String s = "[F]" + super.toString() + " (" + DurationUtil.format(this.duration) + ")";
        return s;
    }
}
