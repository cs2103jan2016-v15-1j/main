package cs2103.v15_1j.jolt.command;

/* @@author A0124995R */

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.command.AliasAddCommand;
import cs2103.v15_1j.jolt.command.UndoableCommand;
import cs2103.v15_1j.jolt.controller.Configuration;
import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.uifeedback.AliasAddFeedback;
import cs2103.v15_1j.jolt.uifeedback.AliasDeleteFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class AliasAddCommandTest {
    ControllerStates conStates;
    DataLists masterList;
    DataLists displayList;
    StubStorage storage;
    Configuration config;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;

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
    }
    
    @Test
    public void testUndo() {
		String alias = "be";
		int keyword = 1;
		String keywordString = "buy eggs";
		
		assertEquals(0, conStates.config.aliases.size());
		AliasAddCommand aliasAdd = new AliasAddCommand(alias, keyword, keywordString);
		aliasAdd.execute(conStates);
		assertEquals(1, conStates.config.aliases.size());
		
		UIFeedback feedback = aliasAdd.undo(conStates);
		assertTrue(feedback instanceof AliasDeleteFeedback);
		assertEquals(0, conStates.config.aliases.size());
    }

	@Test
	public void testExecute() {
		String alias = "be";
		int keyword = 1;
		String keywordString = "buy eggs";
		
		assertEquals(0, conStates.config.aliases.size());
		AliasAddCommand aliasAdd = new AliasAddCommand(alias, keyword, keywordString);
		
		UIFeedback feedback = aliasAdd.execute(conStates);
		assertTrue(feedback instanceof AliasAddFeedback);
		assertEquals(1, conStates.config.aliases.size());
	}
}
