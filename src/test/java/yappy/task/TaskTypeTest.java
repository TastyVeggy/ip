package yappy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import yappy.task.exception.EmptyTaskDescriptionException;
import yappy.task.exception.TaskInvalidArgsException;

public class TaskTypeTest {

    // Todo tests
    @Test
    void testTodoCreateValid() throws Exception {
        Task task = TaskType.TODO.create("Dummy task");
        assertTrue(task instanceof ToDoTask);
        assertEquals("[T][ ] Dummy task", task.toString());
    }

    @Test
    void testTodoCreateEmptyDescription() {
        assertThrows(EmptyTaskDescriptionException.class, () -> {
            TaskType.TODO.create("");
        });
    }

    // Deadline tests
    @Test
    void testDeadlineCreateValid() throws Exception {
        String input = "Dummy deadline task /by Jan 1 2000, 11:59 PM";
        Task task = TaskType.DEADLINE.create(input);
        assertTrue(task instanceof DeadlineTask);

        assertEquals("[D][ ] Dummy deadline task (by: Jan 1 2000, 11:59 PM)", task.toString());
    }

    @Test
    void testDeadlineCreateInvalidFormat() {
        // Missing "/by"
        String input = "Deadline task without by delimiter Jan 1 2000, 11:59 PM";
        assertThrows(TaskInvalidArgsException.class, () -> {
            TaskType.DEADLINE.create(input);
        });
    }

    @Test
    void testDeadlineCreateInvalidDate() {
        String input = "Deadline task with invalid date /by invalid-date";
        assertThrows(TaskInvalidArgsException.class, () -> {
            TaskType.DEADLINE.create(input);
        });
    }

    @Test
    void testDeadlineCreateEmptyDescription() {
        String input = "/by Jan 1 2000, 11:59 PM";
        assertThrows(TaskInvalidArgsException.class, () -> {
            TaskType.DEADLINE.create(input);
        });
    }

    // Event tests
    @Test
    void testEventCreateValid() throws Exception {
        String input = "Dummy event /from Jan 1 2000, 11:59 PM /to Nov 1 2000, 11:00 AM";
        Task task = TaskType.EVENT.create(input);
        assertTrue(task instanceof EventTask);

        EventTask eventTask = (EventTask) task;
        assertEquals("[E][ ] Dummy event (from: Jan 1 2000, 11:59 PM to: Nov 1 2000, 11:00 AM)",
                eventTask.toString());
    }

    @Test
    void testEventCreateInvalidFormat() {
        // Missing /from delimiter
        String inputWithoutFrom =
                "Event missing from Jan 1 2000, 11:59 PM /to Nov 1 2000, 11:00 AM";
        assertThrows(TaskInvalidArgsException.class, () -> {
            TaskType.EVENT.create(inputWithoutFrom);
        });

        // Missing /to delimiter
        String inputWithoutTo =
                "Event missing to /from Jan 1 2000, 11:59 PM to Nov 1 2000, 11:00 AM";
        assertThrows(TaskInvalidArgsException.class, () -> {
            TaskType.EVENT.create(inputWithoutTo);
        });
    }

    @Test
    void testEventCreateInvalidDates() {
        String input = "Conference /from invalid-date /to invalid-date";
        assertThrows(TaskInvalidArgsException.class, () -> {
            TaskType.EVENT.create(input);
        });
    }

    @Test
    void testEventCreateEmptyDescription() {
        String input = "/from Jan 1 2000, 11:59 PM /to Jan 2 2000, 11:59 PM";
        assertThrows(TaskInvalidArgsException.class, () -> {
            TaskType.DEADLINE.create(input);
        });
    }
}
