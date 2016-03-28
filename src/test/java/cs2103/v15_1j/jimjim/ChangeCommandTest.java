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
    TaskEvent added;

    @Before
    public void setUp() throws Exception {
        this.masterList = new DataLists();
        this.storage = new StubStorage();
        this.undoCommandHistory = new Stack<UndoableCommand>();
        this.redoCommandHistory = new Stack<UndoableCommand>();
        AddCommand addCommand = new AddCommand("buy eggs", LocalDateTime.of(2016, 3, 29, 0, 0), 
        										LocalDateTime.of(2016, 4, 29, 0, 0));
        added = addCommand.getTaskEvent();
        addCommand.execute(null, masterList, storage, null, undoCommandHistory, redoCommandHistory);
    }

    
	@Test
	public void testChangeStartDate() {
		LocalDateTime newStartDate = LocalDateTime.of(2016, 2, 29, 0, 0);
		ChangeCommand changeCommand = new ChangeCommand('e', 1, null, newStartDate.toLocalDate(),
														null, null, null);
		changeCommand.execute(null, masterList, storage, null, undoCommandHistory, redoCommandHistory);
		Event temp = (Event) added;
		assertEquals(newStartDate.toLocalDate(), temp.getDateTimes().get(0).getStartDateTime().toLocalDate());
	}

}
