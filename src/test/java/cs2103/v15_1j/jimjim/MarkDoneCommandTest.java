package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.MarkDoneCommand;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;

public class MarkDoneCommandTest {

    DataLists displayList = new DataLists();
    DataLists masterList = new DataLists();
    FloatingTask task1 = new FloatingTask("task 1");
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
    public void testMarkFloating() {
        MarkDoneCommand command = new MarkDoneCommand('f', 1);
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Done!", result);
        assertTrue(displayList.getFloatingTasksList().isEmpty());
        assertEquals(1, masterList.getFloatingTasksList().size());
        assertTrue(masterList.getFloatingTasksList().contains(task1));
        assertTrue(task1.getCompleted());
    }

    @Test
    public void testMarkDeadline() {
        MarkDoneCommand command = new MarkDoneCommand('d', 1);
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Done!", result);
        assertTrue(displayList.getDeadlineTasksList().isEmpty());
        assertEquals(1, masterList.getDeadlineTasksList().size());
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        assertTrue(task2.getCompleted());
    }
    
    @Test
    public void testInvalidNumber() {
        MarkDoneCommand command = new MarkDoneCommand('f', -1);
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("There is no item numbered f-1", result);
        command = new MarkDoneCommand('d', 0);
        result = command.execute(displayList, masterList, storage, null);
        assertEquals("There is no item numbered d0", result);
        command = new MarkDoneCommand('d', 100);
        result = command.execute(displayList, masterList, storage, null);
        assertEquals("There is no item numbered d100", result);
    }

    @Test
    public void testSyncDisplayList() {
        masterList.remove(task2);
        MarkDoneCommand command = new MarkDoneCommand('d', 1);
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Done!", result);
        assertTrue(displayList.getDeadlineTasksList().isEmpty());
        assertEquals(1, masterList.getDeadlineTasksList().size());
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        assertTrue(task2.getCompleted());
    }

    @Test
    public void testStorageError() {
        assertTrue(displayList.getDeadlineTasksList().contains(task2));
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        MarkDoneCommand command = new MarkDoneCommand('d', 1);
        storage.setStorageError();
        String result = command.execute(displayList, masterList, storage, null);
        assertEquals("Some error has occured. Please try again.", result);
        assertTrue(displayList.getDeadlineTasksList().contains(task2));
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        assertFalse(task2.getCompleted());
    }

}
