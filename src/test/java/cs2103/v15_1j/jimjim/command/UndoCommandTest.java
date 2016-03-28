package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.StubStorage;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.uifeedback.AddFeedback;
import cs2103.v15_1j.jimjim.uifeedback.DeleteFeedback;
import cs2103.v15_1j.jimjim.uifeedback.MarkFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UnmarkFeedback;

public class UndoCommandTest {
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
	}

	@Test
	public void testUndoAdd() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertEquals(masterList.size(), 1);
		
		undoCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertEquals(masterList.size(), 0);
	}
	
	@Test
	public void testUndoDelete() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertEquals(masterList.size(), 1);
		
		DeleteCommand deleteCommand = new DeleteCommand('d', 1);
		deleteCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertEquals(masterList.size(), 0);
		
		undoCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertEquals(masterList.size(), 1);
	}

	@Test
	public void testUndoMarkDone() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		Task task = (Task) addCommand.getTaskEvent();
		addCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertEquals(masterList.size(), 1);
		assertFalse(task.getCompleted());
		
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 1);
		markDoneCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertTrue(task.getCompleted());
		undoCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertFalse(task.getCompleted());
	}
	
	@Test
	public void testUndoUnmarkDone() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		Task task = (Task) addCommand.getTaskEvent(); 
		assertFalse(task.getCompleted());
		
		assertEquals(masterList.size(), 1);
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 1);
		markDoneCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertTrue(task.getCompleted());
		
		UnmarkCommand unmarkCommand = new UnmarkCommand('d', 1);
		unmarkCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertFalse(task.getCompleted());
		undoCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		assertTrue(task.getCompleted());
	}
}
