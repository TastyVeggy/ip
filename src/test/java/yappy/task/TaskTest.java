package yappy.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import yappy.task.exception.EmptyTaskDescriptionException;

public class TaskTest {
    private Task task;


    @BeforeEach
    void setUp() throws EmptyTaskDescriptionException {
        this.task = new Task("Dummy task");
    }

    @Test
    void testConstructorCreatesTaskSuccessfully() {
        assertNotNull(task);
        assertEquals("[ ] Dummy task", task.toString());
    }

    @Test
    void testConstructorThrowsExceptionOnEmptyOrNullDescription() {
        assertThrows(EmptyTaskDescriptionException.class, () -> {
            new ToDoTask("");
        });
        assertThrows(EmptyTaskDescriptionException.class, () -> {
            new ToDoTask(null);
        });
    }

    @Test
    void testMarkAndUnmarkTask() {
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
        task.unmarkAsDone();
        assertEquals(" ", task.getStatusIcon());
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
    }
}
