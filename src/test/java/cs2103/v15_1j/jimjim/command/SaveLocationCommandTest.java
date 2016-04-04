package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.controller.Configuration;
import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.SaveLocationFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class SaveLocationCommandTest {
    ControllerStates conStates;
    DataLists masterList;
    DataLists displayList;
    StubStorage storage;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;
    Configuration config;

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

	@Test
	public void testExecute() {
		String newSavePath = "test/save/path/save_data.json";
		SaveLocationCommand saveLocationCommand = new SaveLocationCommand(newSavePath);
		UIFeedback feedback = saveLocationCommand.execute(conStates);
		assertTrue(feedback instanceof SaveLocationFeedback);
		assertEquals(newSavePath, conStates.config.savePath);
	}
	
	@Test
	public void testStorageError() {
		((StubStorage) conStates.storage).setStorageError();
		String backup = conStates.config.savePath;
		String newSavePath = "test/save/path/save_data.json";
		SaveLocationCommand saveLocationCommand = new SaveLocationCommand(newSavePath);
		UIFeedback feedback = saveLocationCommand.execute(conStates);
		assertTrue(feedback instanceof FailureFeedback);
		assertEquals(backup, conStates.config.savePath);
	}

	@Test
	public void testInvalidFilePath() {
		String backup = conStates.config.savePath;
		String invalidSavePath = "test/save/path/save_data.txt"; // Wrong file extension
		SaveLocationCommand saveLocationCommand = new SaveLocationCommand(invalidSavePath);
		UIFeedback feedback = saveLocationCommand.execute(conStates);
		assertTrue(feedback instanceof FailureFeedback);
		assertEquals(backup, conStates.config.savePath);
	}
}
