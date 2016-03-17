package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.AddDeadlineTaskCommand;
import cs2103.v15_1j.jimjim.model.DeadlineTask;

public class AddDeadlineTaskCommandTest {
    
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
        AddDeadlineTaskCommand command =
                new AddDeadlineTaskCommand("Buy oranges",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Task added", result);
        assertEquals(1, displayList.getDeadlineTasksList().size());
        assertEquals("Buy oranges", displayList.getDeadlineTasksList().get(0).getName());
        assertTrue(displayList.getDeadlineTasksList().get(0)instanceof DeadlineTask);
        assertEquals(LocalDateTime.of(2016, 4, 30, 12, 00),
                ((DeadlineTask)displayList.getDeadlineTasksList().get(0)).getDateTime());
    }
    
    @Test
    public void testStorageError() {
        AddDeadlineTaskCommand command =
                new AddDeadlineTaskCommand("Storage error",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        // Make sure storage fails
        storage.setStorageError();
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Some error has occured. Please try again.", result);
        assertEquals(true, displayList.getDeadlineTasksList().isEmpty());
    }

}