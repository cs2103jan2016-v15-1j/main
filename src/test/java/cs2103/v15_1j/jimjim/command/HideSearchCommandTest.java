package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
	
public class HideSearchCommandTest {
	Stack<Command> undoCommandHistory;
    @Before
    public void setUp() throws Exception {
    	undoCommandHistory = new Stack<Command>();
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
        HideSearchCommand clearCmd = new HideSearchCommand();
        clearCmd.execute(searchResults, null, null, null, undoCommandHistory);
        assertTrue(searchResults.getFloatingTasksList().isEmpty());
        assertTrue(searchResults.getDeadlineTasksList().isEmpty());
        assertTrue(searchResults.getEventsList().isEmpty());
    }

}
