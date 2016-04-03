package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.StubStorage;
import cs2103.v15_1j.jimjim.controller.Configuration;
import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DataLists;

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
    }

	@Test
	public void testExecute() {
		String alias = "be";
		int keyword = 1;
		String keywordString = "buy eggs";
		
		assertEquals(0, conStates.config.aliases.size());
		AliasAddCommand aliasAdd = new AliasAddCommand(alias, keyword, keywordString);
		aliasAdd.execute(conStates);
		assertEquals(1, conStates.config.aliases.size());
	}
}
