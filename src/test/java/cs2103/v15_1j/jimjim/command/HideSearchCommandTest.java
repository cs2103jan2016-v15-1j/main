package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.StubStorage;
import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.uifeedback.HideSearchFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;
	
public class HideSearchCommandTest {
	ControllerStates conStates;
	DataLists masterList;
	StubStorage storage;
	Stack<UndoableCommand> undoCommandHistory;
	Stack<UndoableCommand> redoCommandHistory;
	
	@Before
	public void setUp() {
		conStates = new ControllerStates();
		masterList = new DataLists();
        undoCommandHistory = new Stack<UndoableCommand>();
        redoCommandHistory = new Stack<UndoableCommand>();
		storage = new StubStorage();
		
        conStates.masterList = masterList;
        conStates.displayList = new DataLists(conStates.masterList);
        conStates.searchResultsList = new DataLists();
        conStates.storage = storage;
        conStates.undoCommandHistory = undoCommandHistory;
        conStates.redoCommandHistory = redoCommandHistory;
	}

    @Test
    public void testExecute() {
        List<FloatingTask> floats = new ArrayList<>();
        floats.add(new FloatingTask("float task 1"));
        List<DeadlineTask> deadlines = new ArrayList<>();
        deadlines.add(new DeadlineTask("deadline task 2", LocalDateTime.now()));
        List<Event> events = new ArrayList<>();
        events.add(new Event("event 3", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        DataLists searchResults = new DataLists(deadlines, floats, events);
        HideSearchCommand hideSearchCmd = new HideSearchCommand();
        ControllerStates conStates = new ControllerStates();
        conStates.searchResultsList = searchResults;
        UIFeedback feedback = hideSearchCmd.execute(conStates);
        assertTrue(searchResults.getFloatingTasksList().isEmpty());
        assertTrue(searchResults.getDeadlineTasksList().isEmpty());
        assertTrue(searchResults.getEventsList().isEmpty());
        assertTrue(feedback instanceof HideSearchFeedback);
    }
}
