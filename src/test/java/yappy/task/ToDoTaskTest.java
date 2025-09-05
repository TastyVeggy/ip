package yappy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import yappy.task.exception.EmptyTaskDescriptionException;

public class ToDoTaskTest {
    private ToDoTask task;

    @BeforeEach
    void setUp() throws EmptyTaskDescriptionException {
        this.task = new ToDoTask("Dummy todo task");
    }

    @Test
    void constructor_correctArgs_success() {
        assertNotNull(task);
        assertEquals("[T][ ] Dummy todo task", task.toString());
    }

}
