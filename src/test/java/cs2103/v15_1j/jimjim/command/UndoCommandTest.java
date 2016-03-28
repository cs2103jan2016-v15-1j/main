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
    Stack<Command> undoCommandHistory;
    UndoCommand undoCommand;
    
	@Before
	public void setUp() throws Exception {
		searchResultsList = new DataLists();
		masterList = new DataLists();
		storage = new StubStorage();
		undoCommandHistory = new Stack<Command>();
		undoCommand = new UndoCommand();
	}

	@Test
	public void testUndoAdd() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		DeleteFeedback expectedFeedback = new DeleteFeedback(addCommand.getTaskEvent());
		addCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 1);
		
		UIFeedback actualFeedback = undoCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 0);
		assertEquals(expectedFeedback, actualFeedback);
	}
	
	@Test
	public void testUndoDelete() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		AddFeedback expectedFeedback = new AddFeedback(addCommand.getTaskEvent());
		assertEquals(masterList.size(), 1);
		
		DeleteCommand deleteCommand = new DeleteCommand('d', 1);
		deleteCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 0);
		
		UIFeedback actualFeedback = undoCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 1);
		
		assertEquals(expectedFeedback, actualFeedback);
	}

	@Test
	public void testUndoMarkDone() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		Task addedTask = (Task) addCommand.getTaskEvent();
		UnmarkFeedback expectedFeedback = new UnmarkFeedback(addedTask);
		
		assertEquals(masterList.size(), 1);
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 1);
		markDoneCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		UIFeedback actualFeedback = undoCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		
		assertEquals(expectedFeedback, actualFeedback);
	}
	
	@Test
	public void testUndoUnmarkDone() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		MarkFeedback expectedFeedback = new MarkFeedback((Task) addCommand.getTaskEvent()); 
		
		assertEquals(masterList.size(), 1);
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 1);
		markDoneCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		UnmarkCommand unmarkCommand = new UnmarkCommand('d', 1);
		unmarkCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		UIFeedback actualFeedback = undoCommand.execute(searchResultsList, masterList, storage, null, undoCommandHistory);
		
		assertEquals(expectedFeedback, actualFeedback);
	}
}
