package yappy.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;

import yappy.task.exception.EmptyTaskDescriptionException;

public class ToDoTaskTest {
    private ToDoTask task;

    @BeforeEach
    void setUp() throws EmptyTaskDescriptionException{
        this.task = new ToDoTask("Dummy todo task");
    }

    @Test
    void testConstructorCreatesDeadlineTaskSuccessfully() {
        assertNotNull(task);
        assertEquals("[T][ ] Dummy deadline task", task.toString());
    }
    
}
