package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.AddCommand;
import cs2103.v15_1j.jimjim.command.ChangeCommand;
import cs2103.v15_1j.jimjim.command.UndoableCommand;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.TaskEvent;

public class ChangeCommandTest {
    DataLists masterList;
    StubStorage storage;
    Stack<UndoableCommand> undoCommandHistory;
    Stack<UndoableCommand> redoCommandHistory;
    TaskEvent addedTaskEvent;

    @Before
    public void setUp() throws Exception {
        this.masterList = new DataLists();
        this.storage = new StubStorage();
        this.undoCommandHistory = new Stack<UndoableCommand>();
        this.redoCommandHistory = new Stack<UndoableCommand>();
        AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.of(2016, 3, 29, 0, 0), 
        										LocalDateTime.of(2016, 4, 29, 0, 0));
        addedTaskEvent = addCommand.getTaskEvent();
        addCommand.execute(null, masterList, storage, null, undoCommandHistory, redoCommandHistory);
    }

    @Test
	public void testChangeName() {
		String newName = "buy ham";
		ChangeCommand changeCommand = new ChangeCommand('e', 1, newName, null,
														null, null, null);
		changeCommand.execute(null, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		Event temp = (Event) addedTaskEvent;
		assertEquals(newName, temp.getName());
	}
    
	@Test
	public void testChangeStartDate() {
		LocalDate newStartDate = LocalDateTime.of(2016, 2, 29, 0, 0).toLocalDate();
		ChangeCommand changeCommand = new ChangeCommand('e', 1, null, newStartDate,
														null, null, null);
		changeCommand.execute(null, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		Event temp = (Event) addedTaskEvent;
		assertEquals(newStartDate, temp.getDateTimes().get(0).getStartDateTime().toLocalDate());
	}
	
	@Test
	public void testChangeStartTime() {
		LocalTime newStartTime = LocalDateTime.of(2016, 3, 29, 1, 0).toLocalTime();
		ChangeCommand changeCommand = new ChangeCommand('e', 1, null, null,
														newStartTime, null, null);
		changeCommand.execute(null, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		Event temp = (Event) addedTaskEvent;
		assertEquals(newStartTime, temp.getDateTimes().get(0).getStartDateTime().toLocalTime());
	}

	@Test
	public void testChangeStartDateAndStartTime() {
		LocalDateTime newStartDateTime = LocalDateTime.of(2016, 2, 29, 0, 0);
		LocalDate newStartDate = newStartDateTime.toLocalDate();
		LocalTime newStartTime = newStartDateTime.toLocalTime();
		ChangeCommand changeCommand = new ChangeCommand('e', 1, null, newStartDate,
														newStartTime, null, null);
		changeCommand.execute(null, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		Event temp = (Event) addedTaskEvent;
		assertEquals(newStartDate, temp.getDateTimes().get(0).getStartDateTime().toLocalDate());
		assertEquals(newStartTime, temp.getDateTimes().get(0).getStartDateTime().toLocalTime());
	}
}
