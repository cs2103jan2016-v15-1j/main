package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.DeleteCommand;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;

public class DeleteCommandTest {

    DataLists displayList = new DataLists();
    DataLists masterList = new DataLists();
    DeadlineTask task1 = new DeadlineTask("task 1", LocalDateTime.of(2016, 10, 10, 10, 10));
    DeadlineTask task2 = new DeadlineTask("task 2", LocalDateTime.of(2016, 10, 10, 10, 10));
    Event event3 = new Event("event 3", LocalDateTime.of(2016, 10, 10, 10, 10),
            LocalDateTime.of(2016, 11, 11, 11, 11));
    StubStorage storage;

    @Before
    public void setUp() throws Exception {
        displayList.add(task1);
        displayList.add(task2);
        displayList.add(event3);
        masterList.add(task1);
        masterList.add(task2);
        masterList.add(event3);
        this.storage = new StubStorage();
    }

    @Test
    public void testExecute() {
        DeleteCommand command = new DeleteCommand(2);
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Deleted!", result);
        assertEquals(1, displayList.getDeadlineTasksList().size());
        assertEquals(0, displayList.getDeadlineTasksList().indexOf(task1));

        assertEquals(1, masterList.getDeadlineTasksList().size());
        assertTrue(masterList.getDeadlineTasksList().contains(task1));
    }
    
    @Test
    public void testInvalidNumber() {
        DeleteCommand command = new DeleteCommand(-1);
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("There is no item numbered -1", result);
        command = new DeleteCommand(0);
        result = command.execute(displayList, masterList, storage, null);
        assertEquals("There is no item numbered 0", result);
        command = new DeleteCommand(100);
        result = command.execute(displayList, masterList, storage, null);
        assertEquals("There is no item numbered 100", result);
    }
    
    @Test
    public void testStorageError() {
        DeleteCommand command = new DeleteCommand(1);
        storage.setStorageError();
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Some error has occured. Please try again.", result);
        assertEquals(2, displayList.getDeadlineTasksList().size());
        assertEquals(0, displayList.getDeadlineTasksList().indexOf(task1));
        assertEquals(1, displayList.getDeadlineTasksList().indexOf(task2));
        assertEquals(2, masterList.getDeadlineTasksList().size());
        assertTrue(masterList.getDeadlineTasksList().contains(task1));
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
    }

}