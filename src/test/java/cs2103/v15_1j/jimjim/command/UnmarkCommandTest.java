package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.StubStorage;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UnmarkFeedback;

public class UnmarkCommandTest {

    DataLists masterList = new DataLists();
    FloatingTask task1 = new FloatingTask("task 1");
    DeadlineTask task2 = new DeadlineTask("task 2", LocalDateTime.of(2016, 10, 10, 10, 10));
    Event event3 = new Event("event 3", LocalDateTime.of(2016, 10, 10, 10, 10),
            LocalDateTime.of(2016, 11, 11, 11, 11));
    StubStorage storage;

    @Before
    public void setUp() throws Exception {
        task1.setCompleted(true);
        task2.setCompleted(true);
        masterList.add(task1);
        masterList.add(task2);
        masterList.add(event3);
        this.storage = new StubStorage();
    }

    @Test
    public void testUnmarkFloating() {
        UnmarkCommand command = new UnmarkCommand('f', 1);
        UIFeedback result = command.execute(null, masterList, storage, null);
        assertTrue(result instanceof UnmarkFeedback);
        assertEquals(task1, ((UnmarkFeedback)result).getTask());
        assertEquals(1, masterList.getFloatingTasksList().size());
        assertTrue(masterList.getFloatingTasksList().contains(task1));
        assertFalse(task1.getCompleted());
    }

    @Test
    public void testUnmarkDeadline() {
        UnmarkCommand command = new UnmarkCommand('d', 1);
        UIFeedback result = command.execute(null, masterList, storage, null);
        assertTrue(result instanceof UnmarkFeedback);
        assertEquals(task2, ((UnmarkFeedback)result).getTask());
        assertEquals(1, masterList.getDeadlineTasksList().size());
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        assertFalse(task2.getCompleted());
    }
    
    @Test
    public void testInvalidNumber() {
        UnmarkCommand command = new UnmarkCommand('f', -1);
        UIFeedback result = command.execute(null, masterList, storage, null);
        assertTrue(result instanceof FailureFeedback);
        FailureFeedback feedback = (FailureFeedback) result;
        assertEquals("There is no item numbered f-1", feedback.getMessage());
        command = new UnmarkCommand('d', 0);
        result = command.execute(null, masterList, storage, null);
        assertTrue(result instanceof FailureFeedback);
        feedback = (FailureFeedback) result;
        assertEquals("There is no item numbered d0", feedback.getMessage());
        command = new UnmarkCommand('d', 100);
        result = command.execute(null, masterList, storage, null);
        assertTrue(result instanceof FailureFeedback);
        feedback = (FailureFeedback) result;
        assertEquals("There is no item numbered d100", feedback.getMessage());
    }

    @Test
    public void testStorageError() {
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        UnmarkCommand command = new UnmarkCommand('d', 1);
        storage.setStorageError();
        UIFeedback result = command.execute(null, masterList, storage, null);
        assertTrue(result instanceof FailureFeedback);
        FailureFeedback feedback = (FailureFeedback) result;
        assertEquals("Some error has occured. Please try again.",
                feedback.getMessage());
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        assertTrue(task2.getCompleted());
    }

}
