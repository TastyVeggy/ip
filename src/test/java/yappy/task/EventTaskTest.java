package yappy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import yappy.task.exception.EmptyTaskDescriptionException;

public class EventTaskTest {
    private EventTask task;

    @BeforeEach
    void setUp() throws EmptyTaskDescriptionException{
        this.task = new EventTask("Dummy event task", LocalDateTime.parse("2000-01-01T23:59:59"), LocalDateTime.parse("2000-01-02T23:59:59"));
    }

    @Test
    void testConstructorCreatesDeadlineTaskSuccessfully() {
        assertNotNull(task);
        assertEquals("[D][ ] Dummy deadline task (by: Jan 1 2000, 11:59 PM)", task.toString());
    }
}
