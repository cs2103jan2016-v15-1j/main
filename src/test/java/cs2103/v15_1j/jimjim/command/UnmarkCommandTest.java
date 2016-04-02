package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.StubStorage;
import cs2103.v15_1j.jimjim.command.AddCommand;
import cs2103.v15_1j.jimjim.command.MarkDoneCommand;
import cs2103.v15_1j.jimjim.command.UndoableCommand;
import cs2103.v15_1j.jimjim.command.UnmarkCommand;
import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.MarkFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UnmarkFeedback;

public class UnmarkCommandTest {
    ControllerStates conStates;
    DataLists masterList = new DataLists();
    FloatingTask task1 = new FloatingTask("task 1");
    DeadlineTask task2 = new DeadlineTask("task 2", LocalDateTime.of(2016, 10, 10, 10, 10));
    Event event3 = new Event("event 3", LocalDateTime.of(2016, 10, 10, 10, 10),
            LocalDateTime.of(2016, 11, 11, 11, 11));
    StubStorage storage;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;

    @Before
    public void setUp() throws Exception {
        masterList.add(task1);
        masterList.add(task2);
        masterList.add(event3);
        this.storage = new StubStorage();
        undoCommandHistory = new Stack<UndoableCommand>();
        redoCommandHistory = new Stack<UndoableCommand>();

        conStates = new ControllerStates();
        conStates.masterList = masterList;
        conStates.displayList = new DataLists(conStates.masterList);
        conStates.storage = storage;
        conStates.undoCommandHistory = undoCommandHistory;
        conStates.redoCommandHistory = redoCommandHistory;
    }
	
    @Test
    public void testMarkFloating() {
        MarkDoneCommand markDoneCommand = new MarkDoneCommand('f', 1);
        markDoneCommand.execute(conStates);
        UIFeedback expectedFeedback = new UnmarkFeedback(task1);
        
        UnmarkCommand unmarkCommand = new UnmarkCommand('f', 1);
        UIFeedback actualFeedback = unmarkCommand.execute(conStates);
        
        assertEquals(expectedFeedback, actualFeedback);
    }

    @Test
    public void testMarkDeadline() {
        MarkDoneCommand markDownCommand = new MarkDoneCommand('d', 1);
        markDownCommand.execute(conStates);
        UIFeedback expectedFeedback = new UnmarkFeedback(task2);

        UnmarkCommand unmarkCommand = new UnmarkCommand('d', 1);
        UIFeedback actualFeedback = unmarkCommand.execute(conStates);
        
        assertEquals(expectedFeedback, actualFeedback);
    }
    
    @Test
    public void testInvalidNumber() {
        UnmarkCommand command = new UnmarkCommand('f', -1);

        UIFeedback result = command.execute(conStates);
        assertTrue(result instanceof FailureFeedback);
        FailureFeedback feedback = (FailureFeedback) result;
        assertEquals("There is no item numbered f-1", feedback.getMessage());
        command = new UnmarkCommand('d', 0);

        result = command.execute(conStates);
        assertTrue(result instanceof FailureFeedback);
        feedback = (FailureFeedback) result;
        assertEquals("There is no item numbered d0", feedback.getMessage());

        command = new UnmarkCommand('d', 100);
        result = command.execute(conStates);
        assertTrue(result instanceof FailureFeedback);
        feedback = (FailureFeedback) result;
        assertEquals("There is no item numbered d100", feedback.getMessage());
    }

    @Test
    public void testStorageError() {
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 1);
        markDoneCommand.execute(conStates);
        UnmarkCommand unmarkCommand = new UnmarkCommand('d', 1);
        storage.setStorageError();
        
        UIFeedback result = unmarkCommand.execute(conStates);
        assertTrue(result instanceof FailureFeedback);
        FailureFeedback feedback = (FailureFeedback) result;
        assertEquals("Some error has occured. Please try again.",
                feedback.getMessage());
        
        assertTrue(masterList.getDeadlineTasksList().contains(task2));
        assertTrue(task2.getCompleted());
    }

    @Test
    public void testUndo() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(conStates);
		TaskEvent addedTask = addCommand.getTaskEvent();
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 4);
		markDoneCommand.execute(conStates);
		MarkFeedback expectedFeedback = new MarkFeedback(addedTask);
		
		conStates.displayList = new DataLists(conStates.masterList);
		
		assertEquals(4, masterList.size());
		UnmarkCommand undoMarkDoneCommand = new UnmarkCommand('d', 1);
		undoMarkDoneCommand.execute(conStates);
		UIFeedback actualFeedback = undoMarkDoneCommand.undo(conStates);
		
		assertEquals(expectedFeedback, actualFeedback);
    }
    
    @Test
    public void testClearRedo() {
    	AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(conStates);
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('f', 1);
		markDoneCommand.execute(conStates);
		assertEquals(0, conStates.redoCommandHistory.size());
		markDoneCommand.undo(conStates);
		assertEquals(1, conStates.redoCommandHistory.size());
		// Test that command resets redo command history
		UnmarkCommand undoMarkDoneCommand = new UnmarkCommand('d', 1);
		undoMarkDoneCommand.execute(conStates);
		assertEquals(0, conStates.redoCommandHistory.size());
		
    }
}
