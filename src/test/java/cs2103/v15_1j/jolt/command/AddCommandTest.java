package cs2103.v15_1j.jolt.command;

/* @@author A0124995R */

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.command.AddCommand;
import cs2103.v15_1j.jolt.command.UndoableCommand;
import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.uifeedback.AddFeedback;
import cs2103.v15_1j.jolt.uifeedback.DeleteFeedback;
import cs2103.v15_1j.jolt.uifeedback.FailureFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class AddCommandTest {
    
    ControllerStates conStates;
    DataLists masterList;
    DataLists displayList;
    StubStorage storage;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;

    @Before
    public void setUp() throws Exception {
        masterList = new DataLists();
        displayList = new DataLists();
        storage = new StubStorage();
        undoCommandHistory = new Stack<UndoableCommand>();
        redoCommandHistory = new Stack<UndoableCommand>();

        conStates = new ControllerStates();
        conStates.masterList = masterList;
        conStates.displayList = displayList;
        conStates.storage = storage;
        conStates.undoCommandHistory = undoCommandHistory;
        conStates.redoCommandHistory = redoCommandHistory;
    }

    @Test
    public void testAddFloatingTask() {
        AddCommand command =
                new AddCommand("Buy oranges");
        UIFeedback result = command.execute(conStates);
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
        UIFeedback result = command.execute(conStates);
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
        UIFeedback result = command.execute(conStates);
        
        assertTrue(result instanceof AddFeedback);
        AddFeedback addFeedback = (AddFeedback) result;
        assertEquals(command.getTaskEvent(), addFeedback.getTaskEvent());
        assertEquals(1, masterList.getEventsList().size());
        
        assertEquals("Meeting with boss", masterList.getEventsList().get(0).getName());
        Event event = (Event) masterList.getEventsList().get(0);
        
        assertEquals(startDateTime, event.getStartDateTime());
        assertEquals(endDateTime, event.getEndDateTime());
    }

    @Test
    public void testStorageError() {
        AddCommand command =
                new AddCommand("Storage error",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        // Make sure storage fails
        storage.setStorageError();
        UIFeedback result = command.execute(conStates);
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
		addCommand.execute(conStates);
		assertEquals(masterList.size(), 1);
		
		UIFeedback actualFeedback = addCommand.undo(conStates);
		assertEquals(masterList.size(), 0);
		assertEquals(expectedFeedback, actualFeedback);
    }
}