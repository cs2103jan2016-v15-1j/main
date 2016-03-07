package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;

public class MarkDoneCommandTest {

    ArrayList<TaskEvent> displayList;
    ArrayList<TaskEvent> list;
    Task task1 = new Task("task 1", LocalDateTime.of(2016, 10, 10, 10, 10));
    Task task2 = new Task("task 2", LocalDateTime.of(2016, 10, 10, 10, 10));
    Event event3 = new Event("event 3", LocalDateTime.of(2016, 10, 10, 10, 10),
            LocalDateTime.of(2016, 11, 11, 11, 11));
    Task task4 = new Task("task 4", LocalDateTime.of(2016, 10, 10, 10, 10));
    Event event5 = new Event("event 5", LocalDateTime.of(2016, 10, 10, 10, 10),
            LocalDateTime.of(2016, 11, 11, 11, 11));
    Event event6 = new Event("event 6", LocalDateTime.of(2016, 10, 10, 10, 10),
            LocalDateTime.of(2016, 11, 11, 11, 11));
    StubStorage storage;

    @Before
    public void setUp() throws Exception {
        this.displayList = new ArrayList<>();
        displayList.add(task1);
        displayList.add(task2);
        displayList.add(event3);
        displayList.add(task4);
        displayList.add(event5);
        this.list = new ArrayList<>();
        list.add(task1);
        list.add(task2);
        list.add(event3);
        list.add(event5);
        list.add(event6);
        this.storage = new StubStorage();
    }

    @Test
    public void testExecute() {
        MarkDoneCommand command = new MarkDoneCommand(2);
        String result = command.execute(displayList, list, storage, null);
        assertEquals("Done!", result);
        assertEquals(4, displayList.size());
        assertEquals(0, displayList.indexOf(task1));
        assertEquals(1, displayList.indexOf(event3));
        assertEquals(2, displayList.indexOf(task4));
        assertEquals(3, displayList.indexOf(event5));
        assertEquals(-1, displayList.indexOf(task2));

        assertEquals(5, list.size());
        assertTrue(list.contains(task1));
        assertTrue(list.contains(task2));
        assertTrue(list.contains(event5));
        assertTrue(list.contains(event6));
        assertTrue(list.contains(event3));
        
        assertTrue(task2.getCompleted());
    }
    
    @Test
    public void testInvalidNumber() {
        MarkDoneCommand command = new MarkDoneCommand(-1);
        String result = command.execute(displayList, list, storage, null);
        assertEquals("There is no item numbered -1", result);
        command = new MarkDoneCommand(0);
        result = command.execute(displayList, list, storage, null);
        assertEquals("There is no item numbered 0", result);
        command = new MarkDoneCommand(100);
        result = command.execute(displayList, list, storage, null);
        assertEquals("There is no item numbered 100", result);
    }
    
    @Test
    public void testEvent() {
        MarkDoneCommand command = new MarkDoneCommand(3);
        String result = command.execute(displayList, list, storage, null);
        assertEquals("Number 3 is an event, not a task!", result);
        assertEquals(5, displayList.size());
        assertEquals(0, displayList.indexOf(task1));
        assertEquals(1, displayList.indexOf(task2));
        assertEquals(2, displayList.indexOf(event3));
        assertEquals(3, displayList.indexOf(task4));
        assertEquals(4, displayList.indexOf(event5));

        assertEquals(5, list.size());
        assertTrue(list.contains(task1));
        assertTrue(list.contains(task2));
        assertTrue(list.contains(event5));
        assertTrue(list.contains(event6));
        assertTrue(list.contains(event3));
    }

    @Test
    public void testSyncDisplayList() {
        MarkDoneCommand command = new MarkDoneCommand(4);
        String result = command.execute(displayList, list, storage, null);
        assertEquals("Done!", result);
        assertEquals(4, displayList.size());
        assertEquals(0, displayList.indexOf(task1));
        assertEquals(1, displayList.indexOf(task2));
        assertEquals(2, displayList.indexOf(event3));
        assertEquals(3, displayList.indexOf(event5));

        assertEquals(6, list.size());
        assertTrue(list.contains(task1));
        assertTrue(list.contains(task2));
        assertTrue(list.contains(task4));
        assertTrue(list.contains(event5));
        assertTrue(list.contains(event6));
        assertTrue(list.contains(event3));
        
        assertTrue(task4.getCompleted());
    }

    @Test
    public void testStorageError() {
        MarkDoneCommand command = new MarkDoneCommand(1);
        storage.setStorageError();
        String result = command.execute(displayList, list, storage, null);
        assertEquals("Some error has occured. Please try again.", result);
        assertEquals(5, displayList.size());
        assertEquals(0, displayList.indexOf(task1));
        assertEquals(1, displayList.indexOf(task2));
        assertEquals(2, displayList.indexOf(event3));
        assertEquals(3, displayList.indexOf(task4));
        assertEquals(4, displayList.indexOf(event5));

        assertEquals(5, list.size());
        assertTrue(list.contains(task1));
        assertTrue(list.contains(task2));
        assertTrue(list.contains(event5));
        assertTrue(list.contains(event6));
        assertTrue(list.contains(event3));
        
        assertFalse(task1.getCompleted());
    }

}
