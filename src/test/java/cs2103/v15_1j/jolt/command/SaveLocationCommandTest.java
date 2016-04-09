package cs2103.v15_1j.jolt.command;

/* @@author A0124995R */

import static org.junit.Assert.*;

import java.io.File;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.command.SaveLocationCommand;
import cs2103.v15_1j.jolt.command.UndoableCommand;
import cs2103.v15_1j.jolt.controller.Configuration;
import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.uifeedback.FailureFeedback;
import cs2103.v15_1j.jolt.uifeedback.SaveLocationFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class SaveLocationCommandTest {
    ControllerStates conStates;
    DataLists masterList;
    DataLists displayList;
    StubStorage storage;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;
    Configuration config;
    String testFilePath;

    @Before
    public void setUp() throws Exception {
        masterList = new DataLists();
        displayList = new DataLists();
        storage = new StubStorage();
        undoCommandHistory = new Stack<UndoableCommand>();
        redoCommandHistory = new Stack<UndoableCommand>();
        config = new Configuration();

        conStates = new ControllerStates();
        conStates.masterList = masterList;
        conStates.displayList = displayList;
        conStates.storage = storage;
        conStates.undoCommandHistory = undoCommandHistory;
        conStates.redoCommandHistory = redoCommandHistory;
        conStates.config = config;
    }
    
    @After
    public void tearDown() throws Exception {
		File f = new File(testFilePath);
		f.delete();
    }
    

	@Test
	public void testExecute() {
		testFilePath = "test/save/path/save_data.json";
		SaveLocationCommand saveLocationCommand = new SaveLocationCommand(testFilePath);
		UIFeedback feedback = saveLocationCommand.execute(conStates);
		assertTrue(feedback instanceof SaveLocationFeedback);
		assertEquals(testFilePath, conStates.config.savePath);
	}
	
	@Test
	public void testStorageError() {
		((StubStorage) conStates.storage).setStorageError();
		String backup = conStates.config.savePath;
		testFilePath = "test/save/path/save_data.json";
		SaveLocationCommand saveLocationCommand = new SaveLocationCommand(testFilePath);
		UIFeedback feedback = saveLocationCommand.execute(conStates);
		assertTrue(feedback instanceof FailureFeedback);
		assertEquals(backup, conStates.config.savePath);
	}

	@Test
	public void testInvalidFilePath() {
		String backup = conStates.config.savePath;
		testFilePath = "test haha"; // Invalid file path
		SaveLocationCommand saveLocationCommand = new SaveLocationCommand(testFilePath);
		UIFeedback feedback = saveLocationCommand.execute(conStates);
		assertTrue(feedback instanceof FailureFeedback);
		assertEquals(backup, conStates.config.savePath);
	}
}
