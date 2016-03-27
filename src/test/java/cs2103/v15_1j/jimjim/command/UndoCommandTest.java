package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.StubStorage;
import cs2103.v15_1j.jimjim.model.DataLists;

public class UndoCommandTest {
    DataLists displayList;
    DataLists masterList;
    StubStorage storage;
    Stack<Command> undoCommandHistory;
    UndoCommand undoCommand;
    
	@Before
	public void setUp() throws Exception {
		displayList = new DataLists();
		masterList = new DataLists();
		storage = new StubStorage();
		undoCommandHistory = new Stack<Command>();
		undoCommand = new UndoCommand();
	}

	@Test
	public void testUndoAdd() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(displayList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 1);
		String feedback = undoCommand.execute(displayList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 0);
		assertEquals("Task/Event removed", feedback);
	}
	
	@Test
	public void testUndoDelete() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(displayList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 1);
		DeleteCommand deleteCommand = new DeleteCommand('d', 1);
		deleteCommand.execute(displayList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 0);
		String feedback = undoCommand.execute(displayList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 1);
		assertEquals("Task/Event added", feedback);
	}

	@Test
	public void testUndoMarkDone() {
		AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.now());
		addCommand.execute(displayList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 1);
		MarkDoneCommand markDoneCommand = new MarkDoneCommand('d', 1);
		markDoneCommand.execute(displayList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 0);
		String feedback = undoCommand.execute(displayList, masterList, storage, null, undoCommandHistory);
		assertEquals(masterList.size(), 1);
		assertEquals("Task undone!", feedback);
	}
}
