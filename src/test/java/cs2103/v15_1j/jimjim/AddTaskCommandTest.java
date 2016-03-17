package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.AddTaskCommand;
import cs2103.v15_1j.jimjim.model.DeadlineTask;

public class AddTaskCommandTest {
    
    DataLists displayList;
    DataLists masterList;
    StubStorage storage;

    @Before
    public void setUp() throws Exception {
        this.displayList = new DataLists();
        this.masterList = new DataLists();
        this.storage = new StubStorage();
    }

    @Test
    public void testExecute() {
        AddTaskCommand command =
                new AddTaskCommand("Buy oranges",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Task added", result);
        assertEquals(1, displayList.getTasksList().size());
        assertEquals("Buy oranges", displayList.getTasksList().get(0).getName());
        assertTrue(displayList.getTasksList().get(0)instanceof DeadlineTask);
        assertEquals(LocalDateTime.of(2016, 4, 30, 12, 00),
                ((DeadlineTask)displayList.getTasksList().get(0)).getDateTime());
    }
    
    @Test
    public void testStorageError() {
        AddTaskCommand command =
                new AddTaskCommand("Storage error",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        // Make sure storage fails
        storage.setStorageError();
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Some error has occured. Please try again.", result);
        assertEquals(true, displayList.getTasksList().isEmpty());
    }

}