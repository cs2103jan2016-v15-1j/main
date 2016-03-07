package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AddTaskCommandTest {
    
    ArrayList<TaskEvent> displayList;
    ArrayList<TaskEvent> list;
    StubStorage storage;

    @Before
    public void setUp() throws Exception {
        this.displayList = new ArrayList<TaskEvent>();
        this.list = new ArrayList<TaskEvent>();
        this.storage = new StubStorage();
    }

    @Test
    public void testExecute() {
        AddTaskCommand command =
                new AddTaskCommand("Buy oranges",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        String result = command.execute(displayList, list, storage, null);
        assertEquals("Task added", result);
        assertEquals(1, displayList.size());
        assertEquals("Buy oranges", displayList.get(0).getName());
        assertEquals(LocalDateTime.of(2016, 4, 30, 12, 00),
                ((Task)displayList.get(0)).getDateTime());
    }
    
    @Test
    public void testStorageError() {
        AddTaskCommand command =
                new AddTaskCommand("Storage error",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        // Make sure storage fails
        storage.setStorageError();
        String result = command.execute(displayList, list, storage, null);
        assertEquals("Some error has occured. Please try again.", result);
        assertEquals(true, displayList.isEmpty());
    }

}