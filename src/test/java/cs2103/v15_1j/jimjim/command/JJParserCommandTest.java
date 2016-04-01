package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.*;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.parser.JJParser;
import cs2103.v15_1j.jimjim.searcher.DateTimeFilter;
import cs2103.v15_1j.jimjim.searcher.Filter;
import cs2103.v15_1j.jimjim.searcher.KeywordFilter;
import cs2103.v15_1j.jimjim.searcher.TimeFilter;

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
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());
	}

	@Test
	public void testAddEventCommonDate() {
		Command result = parser.parse(
		        "Group meeting on 20 Feb 1:30 pm to 3 pm");
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
		        "Group meeting from 1:30 pm to 3 pm");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof Event);
		event = (Event) taskEvent;
		assertEquals("Group meeting", event.getName());
		resultDateTime = event.getDateTimes();
		assertEquals(1, resultDateTime.size());
		timing = resultDateTime.get(0);
		LocalDate today = LocalDate.now();
		assertEquals(today, timing.getStartDateTime().toLocalDate());
		assertEquals(LocalTime.of(13, 30), timing.getStartDateTime().toLocalTime());
		assertEquals(today, timing.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.of(15, 00), timing.getEndDateTime().toLocalTime());
	}

	@Test
	public void testAddEventMissingEndDate() {
		Command result = parser.parse(
		        "Group meeting from 20 Feb 1:30 pm to 3 pm");
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
	}

	@Test
	public void testAddEventWithoutEndTime() {
		Command result = parser.parse(
		        "Camping with friends at 9.00 am June 1 2016");
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
		assertEquals(LocalDate.of(2016, 6, 1), timing.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.of(10, 00), timing.getEndDateTime().toLocalTime());
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
	public void testInvalidEventEndingTime() {
		Command result = parser.parse(
		        "Group meeting on 20 Feb 1:30 pm to 1 pm");
		assertEquals(true, result instanceof InvalidCommand);
		InvalidCommand casted = (InvalidCommand) result;
		assertEquals("Please ensure that the event's"
		            + " ending time is after its starting time", casted.getMessage());

		result = parser.parse(
		        "Camping with friends from June 1 2016 9:00 am to May 3 5:00 pm");
		assertEquals(true, result instanceof InvalidCommand);
		casted = (InvalidCommand) result;
		assertEquals("Please ensure that the event's"
		            + " ending time is after its starting time", casted.getMessage());

		result = parser.parse(
		        "Group meeting from 20 Feb 1:30 pm to 1 pm");
		assertEquals(true, result instanceof InvalidCommand);
		casted = (InvalidCommand) result;
		assertEquals("Please ensure that the event's"
		            + " ending time is after its starting time", casted.getMessage());
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
    public void testUnmark() {
        Command result = this.parser.parse("unmark d3");
        assertEquals(true, result instanceof UnmarkCommand);
        UnmarkCommand casted = (UnmarkCommand) result;
        assertEquals(3, casted.getTaskNum());
        assertEquals('d', casted.getPrefix());

        result = this.parser.parse("UNMark F3");
        assertEquals(true, result instanceof UnmarkCommand);
        casted = (UnmarkCommand) result;
        assertEquals(3, casted.getTaskNum());
        assertEquals('f', casted.getPrefix());
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
    
    @Test
    public void testSearchOneFilter() {
        Command result = this.parser.parse("search pretty flowers");
        assertTrue(result instanceof SearchCommand);
        SearchCommand casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        Filter filter = casted.getFilters().get(0);
        assertTrue(filter instanceof KeywordFilter);
        KeywordFilter castedFilter = (KeywordFilter) filter;
        assertEquals(2, castedFilter.getKeywords().size());
        assertEquals("pretty", castedFilter.getKeywords().get(0));
        assertEquals("flowers", castedFilter.getKeywords().get(1));

        result = this.parser.parse("SEARCH CONTAINS pretty flowers");
        assertTrue(result instanceof SearchCommand);
        casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        filter = casted.getFilters().get(0);
        assertTrue(filter instanceof KeywordFilter);
        castedFilter = (KeywordFilter) filter;
        assertEquals(2, castedFilter.getKeywords().size());
        assertEquals("pretty", castedFilter.getKeywords().get(0));
        assertEquals("flowers", castedFilter.getKeywords().get(1));

        result = this.parser.parse("SEARCH contain pretty flowers");
        assertTrue(result instanceof SearchCommand);
        casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        filter = casted.getFilters().get(0);
        assertTrue(filter instanceof KeywordFilter);
        castedFilter = (KeywordFilter) filter;
        assertEquals(2, castedFilter.getKeywords().size());
        assertEquals("pretty", castedFilter.getKeywords().get(0));
        assertEquals("flowers", castedFilter.getKeywords().get(1));
    }

    @Test
    public void testSearchManyFilters() {
        Command result = this.parser.parse("search pretty flowers, tomorrow, after 10");
        assertTrue(result instanceof SearchCommand);
        SearchCommand casted = (SearchCommand) result;
        assertEquals(3, casted.getFilters().size());

        Filter filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        DateTimeFilter dateTimeFilter = (DateTimeFilter) filter;
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        assertEquals(tomorrow.atTime(LocalTime.MIN), dateTimeFilter.getStart());
        assertEquals(tomorrow.atTime(LocalTime.MAX), dateTimeFilter.getEnd());

        filter = casted.getFilters().get(1);
        assertTrue(filter instanceof TimeFilter);
        TimeFilter timeFilter = (TimeFilter) filter;
        assertEquals(LocalTime.of(10, 0), timeFilter.getStart());
        assertEquals(LocalTime.MAX, timeFilter.getEnd());

        filter = casted.getFilters().get(2);
        assertTrue(filter instanceof KeywordFilter);
        KeywordFilter keywordFilter = (KeywordFilter) filter;
        assertEquals(2, keywordFilter.getKeywords().size());
        assertEquals("pretty", keywordFilter.getKeywords().get(0));
        assertEquals("flowers", keywordFilter.getKeywords().get(1));
    }

    @Test
    public void testHideSearch() {
        Command result = this.parser.parse("HIDe sEaRCH");
        assertEquals(true, result instanceof HideSearchCommand);
    }

    @Test
    public void testUndo() {
        Command result = this.parser.parse("undo");
        assertEquals(true, result instanceof UndoCommand);
    }

    @Test
    public void testRedo() {
        Command result = this.parser.parse("Redo");
        assertEquals(true, result instanceof RedoCommand);
    }

    @Test
    public void testHelp() {
        Command result = this.parser.parse("help");
        assertEquals(true, result instanceof HelpCommand);
    }

    @Test
    public void testRename() {
        Command result = this.parser.parse("Rename d8 happy birthday");
        assertEquals(true, result instanceof ChangeCommand);
        ChangeCommand casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('d', casted.getPrefix());
        assertEquals("happy birthday", casted.getNewName());

        result = this.parser.parse("CHANGE F10 TO happy birthday");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(10, casted.getTaskNum());
        assertEquals('f', casted.getPrefix());
        assertEquals("happy birthday", casted.getNewName());
    }

    @Test
    public void testReschedule() {
        Command result = this.parser.parse("Reschedule e8 21st April 2016");
        assertEquals(true, result instanceof ChangeCommand);
        ChangeCommand casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 21), casted.getNewDate());

        result = this.parser.parse("CHANGE D10 TO 5.30pm");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(10, casted.getTaskNum());
        assertEquals('d', casted.getPrefix());
        assertEquals(LocalTime.of(17, 30), casted.getNewTime());

        result = this.parser.parse("Reschedule e8 to 3:00pm 5/4/2016");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 5), casted.getNewDate());
        assertEquals(LocalTime.of(15, 0), casted.getNewTime());
    }

    @Test
    public void testExtend() {
        Command result = this.parser.parse("Extend e8 21st April 2016");
        assertEquals(true, result instanceof ChangeCommand);
        ChangeCommand casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 21), casted.getNewEndDate());

        result = this.parser.parse("EXTEND E10 TO 5.30pm");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(10, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalTime.of(17, 30), casted.getNewEndTime());

        result = this.parser.parse("Extend e8 to 3:00pm 5/4/2016");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 5), casted.getNewEndDate());
        assertEquals(LocalTime.of(15, 0), casted.getNewEndTime());
    }
}
