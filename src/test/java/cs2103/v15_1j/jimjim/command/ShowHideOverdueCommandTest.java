package cs2103.v15_1j.jimjim.command;

/* @@author A0124995R */

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.controller.Configuration;
import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.uifeedback.ShowHideOverdueFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class ShowHideOverdueCommandTest {
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
		boolean willShow = true;
		ShowHideOverdueCommand command = new ShowHideOverdueCommand(willShow);
		UIFeedback feedback = command.execute(conStates);
		assertTrue(feedback instanceof ShowHideOverdueFeedback);
	}
}
