package cs2103.v15_1j.jolt.command;

/* @@author A0124995R */

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.command.AliasAddCommand;
import cs2103.v15_1j.jolt.command.AliasDeleteCommand;
import cs2103.v15_1j.jolt.command.UndoableCommand;
import cs2103.v15_1j.jolt.controller.Configuration;
import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.uifeedback.AliasAddFeedback;
import cs2103.v15_1j.jolt.uifeedback.AliasDeleteFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class AliasDeleteCommandTest {
    ControllerStates conStates;
    DataLists masterList;
    DataLists displayList;
    StubStorage storage;
    Configuration config;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;
    String alias;

    @Before
    public void setUp() throws Exception {
        masterList = new DataLists();
        displayList = new DataLists();
        storage = new StubStorage();
        config = new Configuration();
        undoCommandHistory = new Stack<UndoableCommand>();
        redoCommandHistory = new Stack<UndoableCommand>();

        conStates = new ControllerStates();
        conStates.masterList = masterList;
        conStates.displayList = displayList;
        conStates.storage = storage;
        conStates.config = config;
        conStates.undoCommandHistory = undoCommandHistory;
        conStates.redoCommandHistory = redoCommandHistory;
		alias = "be";
		int keyword = 1;
		String keywordString = "buy eggs";
		
		AliasAddCommand aliasAdd = new AliasAddCommand(alias, keyword, keywordString);
		aliasAdd.execute(conStates);
    }
    
    @Test
    public void testUndo() {
		AliasDeleteCommand aliasDelete = new AliasDeleteCommand(alias);
		UIFeedback feedback = aliasDelete.execute(conStates);
		assertTrue(feedback instanceof AliasDeleteFeedback);
		assertEquals(0, conStates.config.aliases.size());
		
		feedback = aliasDelete.undo(conStates);
		assertTrue(feedback instanceof AliasAddFeedback);
		assertEquals(1, conStates.config.aliases.size());
    }

	@Test
	public void testExecute() {
		AliasDeleteCommand aliasDelete = new AliasDeleteCommand(alias);
		UIFeedback feedback = aliasDelete.execute(conStates);
		assertTrue(feedback instanceof AliasDeleteFeedback);
		assertEquals(0, conStates.config.aliases.size());
	}
}
