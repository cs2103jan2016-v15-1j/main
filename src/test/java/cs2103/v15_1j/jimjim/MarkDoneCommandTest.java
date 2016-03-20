package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.MarkDoneCommand;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;

public class MarkDoneCommandTest {

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
        MarkDoneCommand command = new MarkDoneCommand(2);
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Done!", result);
        assertEquals(1, displayList.getDeadlineTasksList().size());
        assertEquals(0, displayList.getDeadlineTasksList().indexOf(task1));
        assertEquals(-1, displayList.getDeadlineTasksList().indexOf(task2));

        assertEquals(2, masterList.getDeadlineTasksList().size());
        assertTrue(masterList.getDeadlineTasksList().contains(task1));
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        
        assertTrue(task2.getCompleted());
    }
    
    @Test
    public void testInvalidNumber() {
        MarkDoneCommand command = new MarkDoneCommand(-1);
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("There is no item numbered -1", result);
        command = new MarkDoneCommand(0);
        result = command.execute(displayList, masterList, storage, null);
        assertEquals("There is no item numbered 0", result);
        command = new MarkDoneCommand(100);
        result = command.execute(displayList, masterList, storage, null);
        assertEquals("There is no item numbered 100", result);
    }

    @Test
    public void testSyncDisplayList() {
        masterList.remove(task2);
        MarkDoneCommand command = new MarkDoneCommand(2);
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Done!", result);
        assertEquals(1, displayList.getDeadlineTasksList().size());
        assertEquals(0, displayList.getDeadlineTasksList().indexOf(task1));
        assertEquals(-1, displayList.getDeadlineTasksList().indexOf(task2));

        assertEquals(2, masterList.getDeadlineTasksList().size());
        assertTrue(masterList.getDeadlineTasksList().contains(task1));
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        
        assertTrue(task2.getCompleted());
    }

    @Test
    public void testStorageError() {
        MarkDoneCommand command = new MarkDoneCommand(2);
        storage.setStorageError();
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Some error has occured. Please try again.", result);

        assertEquals(2, displayList.getDeadlineTasksList().size());
        assertEquals(0, displayList.getDeadlineTasksList().indexOf(task1));
        assertEquals(1, displayList.getDeadlineTasksList().indexOf(task2));

        assertEquals(2, masterList.getDeadlineTasksList().size());
        assertTrue(masterList.getDeadlineTasksList().contains(task1));
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        
        assertFalse(task2.getCompleted());
    }

}
