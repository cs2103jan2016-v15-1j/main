package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.uifeedback.ShiftFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class ShiftCommandTest {
    ControllerStates conStates;
    DataLists masterList;
    DataLists displayList;
    StubStorage storage;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;
    TaskEvent addedTaskEvent;

    @Before
    public void setUp() throws Exception {
        this.masterList = new DataLists();
        this.displayList = new DataLists();
        this.storage = new StubStorage();
        this.undoCommandHistory = new Stack<UndoableCommand>();
        this.redoCommandHistory = new Stack<UndoableCommand>();

        conStates = new ControllerStates();
        conStates.masterList = masterList;
        conStates.displayList = displayList;
        conStates.storage = storage;
        conStates.undoCommandHistory = undoCommandHistory;
        conStates.redoCommandHistory = redoCommandHistory;

        AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.of(2016, 3, 29, 0, 0), 
        										LocalDateTime.of(2016, 4, 29, 0, 0));
        addedTaskEvent = addCommand.getTaskEvent();
        addCommand.execute(conStates);
        
        conStates.displayList = new DataLists(conStates.masterList);
    }

	@Test
	public void testExecute() {
		Event temp = (Event) addedTaskEvent;
		LocalDateTime oldStartDateTime = temp.getStartDateTime();
		LocalDateTime oldEndDateTime = temp.getEndDateTime();
		long diffInSeconds = Duration.between(oldStartDateTime, oldEndDateTime).getSeconds();
		
		LocalDateTime newStartDateTime = LocalDateTime.of(2016, 2, 29, 0, 0);
		LocalDate newStartDate = newStartDateTime.toLocalDate();
		LocalTime newStartTime = newStartDateTime.toLocalTime();
		LocalDateTime expectedEndDateTime = newStartDateTime.plusSeconds(diffInSeconds);
		
		ShiftCommand command = new ShiftCommand('e', 1, newStartDate, newStartTime);
		
		UIFeedback feedback = command.execute(conStates);
		assertTrue(feedback instanceof ShiftFeedback);
		Event event = ((Event) masterList.getTaskEvent(0,  'e'));
		assertEquals(newStartDateTime, event.getStartDateTime());
		assertEquals(expectedEndDateTime, event.getEndDateTime());
	}

	@Test 
	public void testUndo() {
		Event temp = (Event) addedTaskEvent;
		LocalDateTime oldStartDateTime = temp.getStartDateTime();
		LocalDateTime oldEndDateTime = temp.getEndDateTime();
		
		LocalDateTime newStartDateTime = LocalDateTime.of(2016, 2, 29, 0, 0);
		LocalDate newStartDate = newStartDateTime.toLocalDate();
		LocalTime newStartTime = newStartDateTime.toLocalTime();
		
		ShiftCommand command = new ShiftCommand('e', 1, newStartDate, newStartTime);
		command.execute(conStates);
		
		UIFeedback feedback = command.undo(conStates);
		assertTrue(feedback instanceof ShiftFeedback);
		Event event = ((Event) masterList.getTaskEvent(0,  'e'));
		assertEquals(oldStartDateTime, event.getStartDateTime());
		assertEquals(oldEndDateTime, event.getEndDateTime());
	}
}
