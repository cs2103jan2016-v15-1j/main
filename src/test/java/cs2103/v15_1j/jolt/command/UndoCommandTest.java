package cs2103.v15_1j.jolt.command;

/* @@author A0124995R */

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.command.AddCommand;
import cs2103.v15_1j.jolt.command.DeleteCommand;
import cs2103.v15_1j.jolt.command.MarkDoneCommand;
import cs2103.v15_1j.jolt.command.UndoCommand;
import cs2103.v15_1j.jolt.command.UndoableCommand;
import cs2103.v15_1j.jolt.command.UnmarkCommand;
import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.TaskEvent;

public class UndoCommandTest {
    ControllerStates conStates;
    DataLists searchResultsList;
    DataLists masterList;
    StubStorage storage;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;
    UndoCommand undoCommand;
    
	@Before
	public void setUp() throws Exception {
		searchResultsList = new DataLists();
		masterList = new DataLists();
		storage = new StubStorage();
		undoCommandHistory = new Stack<UndoableCommand>();
		redoCommandHistory = new Stack<UndoableCommand>();
		undoCommand = new UndoCommand();

        conStates = new ControllerStates();
        conStates.masterList = masterList;
        conStates.displayList = new DataLists();
        conStates.displayList.copy(conStates.masterList);
        conStates.searchResultsList = new DataLists();
        conStates.storage = storage;
        conStates.undoCommandHistory = undoCommandHistory;
        conStates.redoCommandHistory = redoCommandHistory;
	}

	@Test
	public void testUndoAdd() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(conStates);
		assertEquals(masterList.size(), 1);
		
		undoCommand.execute(conStates);
		assertEquals(masterList.size(), 0);
	}
	
	@Test
	public void testUndoDelete() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(conStates);
		assertEquals(masterList.size(), 1);
        conStates.displayList = new DataLists();
        conStates.displayList.copy(conStates.masterList);
		
		DeleteCommand deleteCommand = new DeleteCommand('d', 1);
		deleteCommand.execute(conStates);
		assertEquals(masterList.size(), 0);
        conStates.displayList = new DataLists();
        conStates.displayList.copy(conStates.masterList);
		
		undoCommand.execute(conStates);
		assertEquals(masterList.size(), 1);
	}

	@Test
	public void testUndoMarkDone() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		TaskEvent task = addCommand.getTaskEvent();
		addCommand.execute(conStates);
		assertEquals(masterList.size(), 1);
		assertFalse(task.getCompleted());
        conStates.displayList = new DataLists();
        conStates.displayList.copy(conStates.masterList);
		
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 1);
		markDoneCommand.execute(conStates);
		assertTrue(task.getCompleted());
		undoCommand.execute(conStates);
		assertFalse(task.getCompleted());
	}
	
	@Test
	public void testUndoUnmarkDone() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(conStates);
		TaskEvent task = addCommand.getTaskEvent(); 
		assertFalse(task.getCompleted());
        conStates.displayList = new DataLists();
        conStates.displayList.copy(conStates.masterList);
		
		assertEquals(masterList.size(), 1);
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 1);
		markDoneCommand.execute(conStates);
		assertTrue(task.getCompleted());
		
		UnmarkCommand unmarkCommand = new UnmarkCommand('d', 1);
		unmarkCommand.execute(conStates);
		assertFalse(task.getCompleted());
		undoCommand.execute(conStates);
		assertTrue(task.getCompleted());
	}
}
