package cs2103.v15_1j.jolt.command;

/* @@author A0124995R */

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.command.AddCommand;
import cs2103.v15_1j.jolt.command.ChangeCommand;
import cs2103.v15_1j.jolt.command.UndoableCommand;
import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;
import cs2103.v15_1j.jolt.model.TaskEvent;

public class ChangeCommandTest {
    ControllerStates conStates;
    DataLists masterList;
    DataLists displayList;
    StubStorage storage;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;
    TaskEvent addedTaskEvent;
    TaskEvent addedTaskEvent2;

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
        

        AddCommand addCommand2 = new AddCommand("buy milk");
        addedTaskEvent2 = addCommand2.getTaskEvent();
        addCommand2.execute(conStates);
        
        conStates.displayList = new DataLists();
        conStates.displayList.copy(conStates.masterList);
    }

    @Test
	public void testChangeName() {
		String newName = "buy ham";
		ChangeCommand changeCommand = new ChangeCommand('e', 1, newName, null,
														null, null, null);
		changeCommand.execute(conStates);
        displayList = masterList;
		Event temp = (Event) addedTaskEvent;
		assertEquals(newName, temp.getName());
	}
    
	@Test
	public void testChangeStartDate() {
		LocalDate newStartDate = LocalDateTime.of(2016, 2, 29, 0, 0).toLocalDate();
		ChangeCommand changeCommand = new ChangeCommand('e', 1, null, newStartDate,
														null, null, null);
		changeCommand.execute(conStates);
		Event temp = (Event) addedTaskEvent;
		assertEquals(newStartDate, temp.getStartDateTime().toLocalDate());
	}
	
	@Test
	public void testChangeStartTime() {
		LocalTime newStartTime = LocalDateTime.of(2016, 3, 29, 1, 0).toLocalTime();
		ChangeCommand changeCommand = new ChangeCommand('e', 1, null, null,
														newStartTime, null, null);
		changeCommand.execute(conStates);
		Event temp = (Event) addedTaskEvent;
		assertEquals(newStartTime, temp.getStartDateTime().toLocalTime());
	}

	@Test
	public void testChangeStartDateAndStartTime() {
		LocalDateTime newStartDateTime = LocalDateTime.of(2016, 2, 29, 0, 0);
		LocalDate newStartDate = newStartDateTime.toLocalDate();
		LocalTime newStartTime = newStartDateTime.toLocalTime();
		ChangeCommand changeCommand = new ChangeCommand('e', 1, null, newStartDate,
														newStartTime, null, null);
		changeCommand.execute(conStates);
		Event temp = (Event) addedTaskEvent;
		assertEquals(newStartDate, temp.getStartDateTime().toLocalDate());
		assertEquals(newStartTime, temp.getStartDateTime().toLocalTime());
	}
	
	@Test
	public void testUndoChangeStartDateAndStartTime() {
		Event temp = (Event) addedTaskEvent;
		LocalDateTime oldStartDateTime = temp.getStartDateTime();
		LocalDate oldStartDate = oldStartDateTime.toLocalDate();
		LocalTime oldStartTime = oldStartDateTime.toLocalTime();
		
		LocalDateTime newStartDateTime = LocalDateTime.of(2016, 2, 29, 0, 0);
		LocalDate newStartDate = newStartDateTime.toLocalDate();
		LocalTime newStartTime = newStartDateTime.toLocalTime();
		ChangeCommand changeCommand = new ChangeCommand('e', 1, null, newStartDate,
														newStartTime, null, null);
		changeCommand.execute(conStates);
		
		assertEquals(newStartDate, temp.getStartDateTime().toLocalDate());
		assertEquals(newStartTime, temp.getStartDateTime().toLocalTime());
		
		changeCommand.undo(conStates);
		Event currentEvent = ((Event) masterList.getTaskEvent(0,  'e'));
		assertEquals(oldStartDate, currentEvent.getStartDateTime().toLocalDate());
		assertEquals(oldStartTime, currentEvent.getStartDateTime().toLocalTime());
	}

	/* @@author A0139963N */
	@Test
	public void testChangeDeadlineToFloatingTask() {
		LocalDateTime newStartDateTime = LocalDateTime.of(2016, 2, 29, 0, 0);
		LocalDate newStartDate = newStartDateTime.toLocalDate();
		LocalTime newStartTime = newStartDateTime.toLocalTime();
		ChangeCommand changeCommand = new ChangeCommand('f', 1, null, newStartDate,
														newStartTime, null, null);
		changeCommand.execute(conStates);
		
		DeadlineTask temp = (DeadlineTask) masterList.getTaskEvent(0, 'd');
		assertEquals(newStartDate, temp.getDateTime().toLocalDate());
		assertEquals(newStartTime, temp.getDateTime().toLocalTime());
	}
	
	@Test
	public void testUndoChangeDeadlineToFloatingTask() {
		LocalDateTime newStartDateTime = LocalDateTime.of(2016, 2, 29, 0, 0);
		LocalDate newStartDate = newStartDateTime.toLocalDate();
		LocalTime newStartTime = newStartDateTime.toLocalTime();
		ChangeCommand changeCommand = new ChangeCommand('f', 1, null, newStartDate,
														newStartTime, null, null);
		changeCommand.execute(conStates);
		
		DeadlineTask temp = (DeadlineTask) masterList.getTaskEvent(0, 'd');
		assertEquals(newStartDate, temp.getDateTime().toLocalDate());
		assertEquals(newStartTime, temp.getDateTime().toLocalTime());
		
		changeCommand.undo(conStates);
		FloatingTask currentTask = ((FloatingTask) masterList.getTaskEvent(0,  'f'));
		assertEquals(currentTask, ((FloatingTask) addedTaskEvent2));
	}
}
