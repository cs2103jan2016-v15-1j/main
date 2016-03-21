package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.AddCommand;
import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.command.DeleteCommand;
import cs2103.v15_1j.jimjim.command.InvalidCommand;
import cs2103.v15_1j.jimjim.command.MarkDoneCommand;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.parser.JJParser;

public class JJParserCommandTest {

	JJParser parser;

	@Before
	public void setUp() throws Exception {
		this.parser = new JJParser();
	}

	@Test
	public void testAddFloatingTask() {
		Command result = parser.parse("Learn 10 new words every day");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertEquals("Learn 10 new words every day", taskEvent.getName());
		assertTrue(taskEvent instanceof Task);
		Task castedTask = (Task) taskEvent;
		assertTrue(!castedTask.getCompleted());
	}
	
	@Test
	public void testAddDeadlineTask() {
		Command result = parser.parse("Submit assignment 2 by 31/12/2016");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(2016, 12, 31), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());
	}

	@Test
	public void testAddEventCommonDate() {
		Command result = parser.parse(
		        "Group meeting on 20 Feb from 1:30 pm to 3 pm");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof Event);
		Event event = (Event) taskEvent;
		assertEquals("Group meeting", event.getName());
		List<EventTime> resultDateTime = event.getDateTimes();
		assertEquals(1, resultDateTime.size());
		EventTime timing = resultDateTime.get(0);
		assertEquals(LocalDate.of(2016, 2, 20), timing.getStartDateTime().toLocalDate());
		assertEquals(LocalTime.of(13, 30), timing.getStartDateTime().toLocalTime());
		assertEquals(LocalDate.of(2016, 2, 20), timing.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.of(15, 00), timing.getEndDateTime().toLocalTime());

		result = parser.parse(
		        "Group meeting 20 Feb from 1:30 pm to 3 pm");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof Event);
		event = (Event) taskEvent;
		assertEquals("Group meeting", event.getName());
		resultDateTime = event.getDateTimes();
		assertEquals(1, resultDateTime.size());
		timing = resultDateTime.get(0);
		assertEquals(LocalDate.of(2016, 2, 20), timing.getStartDateTime().toLocalDate());
		assertEquals(LocalTime.of(13, 30), timing.getStartDateTime().toLocalTime());
		assertEquals(LocalDate.of(2016, 2, 20), timing.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.of(15, 00), timing.getEndDateTime().toLocalTime());
	}

	@Test
	public void testAddEventDiffDate() {
		Command result = parser.parse(
		        "Camping with friends from June 1 2016 9:00 am to June 3 5:00 pm");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof Event);
		Event event = (Event) taskEvent;
		assertEquals("Camping with friends", event.getName());
		List<EventTime> resultDateTime = event.getDateTimes();
		assertEquals(1, resultDateTime.size());
		EventTime timing = resultDateTime.get(0);
		assertEquals(LocalDate.of(2016, 6, 1), timing.getStartDateTime().toLocalDate());
		assertEquals(LocalTime.of(9, 00), timing.getStartDateTime().toLocalTime());
		assertEquals(LocalDate.of(2016, 6, 3), timing.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.of(17, 00), timing.getEndDateTime().toLocalTime());

	}

    @Test
    public void testDel() {
        Command result = this.parser.parse("DELETE d8");
        assertEquals(true, result instanceof DeleteCommand);
        DeleteCommand casted = (DeleteCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('d', casted.getPrefix());

        result = this.parser.parse("delete F10");
        assertEquals(true, result instanceof DeleteCommand);
        casted = (DeleteCommand) result;
        assertEquals(10, casted.getTaskNum());
        assertEquals('f', casted.getPrefix());
        
        result = this.parser.parse("delete e8");
        assertEquals(true, result instanceof DeleteCommand);
        casted = (DeleteCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
    }
    
    @Test
    public void testMarkDone() {
        Command result = this.parser.parse("mark d3 as done");
        assertEquals(true, result instanceof MarkDoneCommand);
        MarkDoneCommand casted = (MarkDoneCommand) result;
        assertEquals(3, casted.getTaskNum());
        assertEquals('d', casted.getPrefix());

        result = this.parser.parse("Mark F3");
        assertEquals(true, result instanceof MarkDoneCommand);
        casted = (MarkDoneCommand) result;
        assertEquals(3, casted.getTaskNum());
        assertEquals('f', casted.getPrefix());
    }

    @Test
    public void testMarkDoneEvent() {
        Command result = this.parser.parse("mark e3 as done");
        assertEquals(true, result instanceof InvalidCommand);
        InvalidCommand casted = (InvalidCommand) result;
        assertEquals("e3 is not a valid task!", casted.getMessage());
    }

}
