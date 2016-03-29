package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.StubStorage;
import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.Task;

public class RedoCommandTest {
    ControllerStates conStates;
    DataLists masterList;
    StubStorage storage;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;
    UndoCommand undoCommand;
    RedoCommand redoCommand;
    
	@Before
	public void setUp() throws Exception {
		masterList = new DataLists();
		storage = new StubStorage();
		undoCommandHistory = new Stack<UndoableCommand>();
		redoCommandHistory = new Stack<UndoableCommand>();
		undoCommand = new UndoCommand();
		redoCommand = new RedoCommand();

        conStates = new ControllerStates();
        conStates.masterList = masterList;
        conStates.storage = storage;
        conStates.undoCommandHistory = undoCommandHistory;
        conStates.redoCommandHistory = redoCommandHistory;
	}

	@Test
	public void testRedoAdd() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(conStates);
		assertEquals(masterList.size(), 1);
		
		undoCommand.execute(conStates);
		assertEquals(masterList.size(), 0);
		redoCommand.execute(conStates);
		assertEquals(masterList.size(), 1);
	}
	
	@Test
	public void testUndoDelete() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(conStates);
		assertEquals(masterList.size(), 1);
		
		DeleteCommand deleteCommand = new DeleteCommand('d', 1);
		deleteCommand.execute(conStates);
		assertEquals(masterList.size(), 0);
		
		undoCommand.execute(conStates);
		assertEquals(masterList.size(), 1);
		redoCommand.execute(conStates);
		assertEquals(masterList.size(), 0);
	}

	@Test
	public void testUndoMarkDone() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		Task task = (Task) addCommand.getTaskEvent();
		addCommand.execute(conStates);
		assertEquals(masterList.size(), 1);
		assertFalse(task.getCompleted());
		
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 1);
		markDoneCommand.execute(conStates);
		assertTrue(task.getCompleted());
		undoCommand.execute(conStates);
		assertFalse(task.getCompleted());
		redoCommand.execute(conStates);
		assertTrue(task.getCompleted());
	}
	
	@Test
	public void testUndoUnmarkDone() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(conStates);
		Task task = (Task) addCommand.getTaskEvent(); 
		assertFalse(task.getCompleted());
		
		assertEquals(masterList.size(), 1);
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 1);
		markDoneCommand.execute(conStates);
		assertTrue(task.getCompleted());
		
		UnmarkCommand unmarkCommand = new UnmarkCommand('d', 1);
		unmarkCommand.execute(conStates);
		assertFalse(task.getCompleted());
		
		undoCommand.execute(conStates);
		assertTrue(task.getCompleted());
		redoCommand.execute(conStates);
		assertFalse(task.getCompleted());
	}

}
