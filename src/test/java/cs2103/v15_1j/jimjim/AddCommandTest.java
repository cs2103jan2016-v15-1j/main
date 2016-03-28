package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.AddCommand;
import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import cs2103.v15_1j.jimjim.uifeedback.AddFeedback;
import cs2103.v15_1j.jimjim.uifeedback.DeleteFeedback;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class AddCommandTest {
    
    DataLists masterList;
    StubStorage storage;
    Stack<Command> undoCommandHistory;

    @Before
    public void setUp() throws Exception {
        this.masterList = new DataLists();
        this.storage = new StubStorage();
        this.undoCommandHistory = new Stack<Command>();
    }

    @Test
    public void testAddFloatingTask() {
        AddCommand command =
                new AddCommand("Buy oranges");
        UIFeedback result = command.execute(null, masterList, storage, null, undoCommandHistory);
        assertTrue(result instanceof AddFeedback);
        AddFeedback addFeedback = (AddFeedback) result;
        assertEquals(command.getTaskEvent(), addFeedback.getTaskEvent());
        assertEquals(1, masterList.getFloatingTasksList().size());
        assertEquals("Buy oranges", masterList.getFloatingTasksList().get(0).getName());
    }

    @Test
    public void testAddDeadlineTask() {
        AddCommand command =
                new AddCommand("Buy oranges",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        UIFeedback result = command.execute(null, masterList, storage, null, undoCommandHistory);
        assertTrue(result instanceof AddFeedback);
        AddFeedback addFeedback = (AddFeedback) result;
        assertEquals(command.getTaskEvent(), addFeedback.getTaskEvent());
        assertEquals(1, masterList.getDeadlineTasksList().size());
        assertEquals("Buy oranges", masterList.getDeadlineTasksList().get(0).getName());
        assertEquals(LocalDateTime.of(2016, 4, 30, 12, 00),
                ((DeadlineTask)masterList.getDeadlineTasksList().get(0)).getDateTime());
    }
    
    @Test
    public void testAddEvent() {
    	LocalDateTime startDateTime = LocalDateTime.of(2016, 4, 30, 12, 00);
    	LocalDateTime endDateTime = LocalDateTime.of(2016, 4, 30, 16,00);
        AddCommand command =
                new AddCommand("Meeting with boss", startDateTime, endDateTime);
        UIFeedback result = command.execute(null, masterList, storage, null, undoCommandHistory);
        
        assertTrue(result instanceof AddFeedback);
        AddFeedback addFeedback = (AddFeedback) result;
        assertEquals(command.getTaskEvent(), addFeedback.getTaskEvent());
        assertEquals(1, masterList.getEventsList().size());
        
        assertEquals("Meeting with boss", masterList.getEventsList().get(0).getName());
        Event event = (Event) masterList.getEventsList().get(0);
        List<EventTime> eventTimeList = event.getDateTimes();
        
        assertEquals(startDateTime, eventTimeList.get(0).getStartDateTime());
        assertEquals(endDateTime, eventTimeList.get(0).getEndDateTime());
    }

    @Test
    public void testStorageError() {
        AddCommand command =
                new AddCommand("Storage error",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        // Make sure storage fails
        storage.setStorageError();
        UIFeedback result = command.execute(null, masterList, storage, null, undoCommandHistory);
        assertTrue(result instanceof FailureFeedback);
        FailureFeedback feedback = (FailureFeedback) result;
        assertEquals("Some error has occured. Please try again.",
                feedback.getMessage());
        assertEquals(true, masterList.getDeadlineTasksList().isEmpty());
    }

    @Test
    public void testUndo() {
    	AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		DeleteFeedback expectedFeedback = new DeleteFeedback(addCommand.getTaskEvent());
		addCommand.execute(null, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 1);
		
		UIFeedback actualFeedback = addCommand.undo(null, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 0);
		assertEquals(expectedFeedback, actualFeedback);
    }
}