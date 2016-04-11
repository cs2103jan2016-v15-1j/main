//@@author A0131140E
package cs2103.v15_1j.jolt.parser;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.antlr4.UserCommandLexer;
import cs2103.v15_1j.jolt.command.*;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;
import cs2103.v15_1j.jolt.model.TaskEvent;
import cs2103.v15_1j.jolt.parser.JoltParser;
import cs2103.v15_1j.jolt.searcher.DateTimeFilter;
import cs2103.v15_1j.jolt.searcher.Filter;
import cs2103.v15_1j.jolt.searcher.KeywordFilter;
import cs2103.v15_1j.jolt.searcher.TimeFilter;

public class JoltParserCommandTest {

	JoltParser parser;

	@Before
	public void setUp() throws Exception {
		this.parser = new JoltParser();
	}

	@Test
	public void testAddFloatingTask() {
		Command result = parser.parse("Learn 10 new words every day");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertEquals("Learn 10 new words every day", taskEvent.getName());
		assertTrue(taskEvent instanceof FloatingTask);
		FloatingTask castedTask = (FloatingTask) taskEvent;
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
		assertEquals(LocalDate.of(2016, 2, 20), event.getStartDateTime().toLocalDate());
		assertEquals(LocalTime.of(13, 30), event.getStartDateTime().toLocalTime());
		assertEquals(LocalDate.of(2016, 2, 20), event.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.of(15, 00), event.getEndDateTime().toLocalTime());

		result = parser.parse(
		        "Group meeting from 1:30 pm to 3 pm");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof Event);
		event = (Event) taskEvent;
		assertEquals("Group meeting", event.getName());
		LocalDate today = LocalDate.now();
		assertEquals(today, event.getStartDateTime().toLocalDate());
		assertEquals(LocalTime.of(13, 30), event.getStartDateTime().toLocalTime());
		assertEquals(today, event.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.of(15, 00), event.getEndDateTime().toLocalTime());
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
		assertEquals(LocalDate.of(2016, 2, 20), event.getStartDateTime().toLocalDate());
		assertEquals(LocalTime.of(13, 30), event.getStartDateTime().toLocalTime());
		assertEquals(LocalDate.of(2016, 2, 20), event.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.of(15, 00), event.getEndDateTime().toLocalTime());
	}

	@Test
	public void testAddFullDayEvent() {
		Command result = parser.parse(
		        "My birthday on April 5th 2016");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof Event);
		Event event = (Event) taskEvent;
		assertEquals("My birthday", event.getName());
		assertEquals(LocalDate.of(2016, 4, 5), event.getStartDateTime().toLocalDate());
		assertEquals(LocalTime.MIN, event.getStartDateTime().toLocalTime());
		assertEquals(LocalDate.of(2016, 4, 5), event.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.MAX, event.getEndDateTime().toLocalTime());
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
		assertEquals(LocalDate.of(2016, 6, 1), event.getStartDateTime().toLocalDate());
		assertEquals(LocalTime.of(9, 00), event.getStartDateTime().toLocalTime());
		assertEquals(LocalDate.of(2016, 6, 1), event.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.of(10, 00), event.getEndDateTime().toLocalTime());
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
		assertEquals(LocalDate.of(2016, 6, 1), event.getStartDateTime().toLocalDate());
		assertEquals(LocalTime.of(9, 00), event.getStartDateTime().toLocalTime());
		assertEquals(LocalDate.of(2016, 6, 3), event.getEndDateTime().toLocalDate());
		assertEquals(LocalTime.of(17, 00), event.getEndDateTime().toLocalTime());
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

        result = this.parser.parse("UNMark e3");
        assertEquals(true, result instanceof UnmarkCommand);
        casted = (UnmarkCommand) result;
        assertEquals(3, casted.getTaskNum());
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

        result = this.parser.parse("Mark e3");
        assertEquals(true, result instanceof MarkDoneCommand);
        casted = (MarkDoneCommand) result;
        assertEquals(3, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
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
        Command result = this.parser.parse("search pretty flowers, tomorrow, after 10.00");
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
        HelpCommand casted = (HelpCommand) result;
        assertEquals("index", casted.getPage());

        result = this.parser.parse("help DELETE");
        assertEquals(true, result instanceof HelpCommand);
        casted = (HelpCommand) result;
        assertEquals("delete", casted.getPage());
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
    public void testChangeOtherKeyword() {
        Command result = this.parser.parse("SHIFT e8 21st April 2016");
        assertEquals(true, result instanceof ShiftCommand);
        ShiftCommand shift = (ShiftCommand) result;
        assertEquals(8, shift.getTaskNum());
        assertEquals('e', shift.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 21), shift.getNewDate());
    }

    @Test
    public void testChangeEnd() {
        Command result = this.parser.parse("Change end e8 21st April 2016");
        assertEquals(true, result instanceof ChangeCommand);
        ChangeCommand casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 21), casted.getNewEndDate());

        result = this.parser.parse("CHANGE END E10 TO 5.30pm");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(10, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalTime.of(17, 30), casted.getNewEndTime());

        result = this.parser.parse("Change end e8 to 3:00pm 5/4/2016");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 5), casted.getNewEndDate());
        assertEquals(LocalTime.of(15, 0), casted.getNewEndTime());
    }

    @Test
    public void testInvalidChangeEnd() {
        Command result = this.parser.parse("Change end f8 21st April 2016");
        assertEquals(true, result instanceof InvalidCommand);
        assertEquals("Changing the ending date/time is only for event",
                ((InvalidCommand)result).getMessage());
        result = this.parser.parse("Change end d8 21st April 2016");
        assertEquals(true, result instanceof InvalidCommand);
        assertEquals("Changing the ending date/time is only for event",
                ((InvalidCommand)result).getMessage());
    }

    @Test
    public void testChangeStart() {
        Command result = this.parser.parse("Change start e8 21st April 2016");
        assertEquals(true, result instanceof ChangeCommand);
        ChangeCommand casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 21), casted.getNewStartDate());

        result = this.parser.parse("CHANGE START E10 TO 5.30pm");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(10, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalTime.of(17, 30), casted.getNewStartTime());

        result = this.parser.parse("Change start e8 to 3:00pm 5/4/2016");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 5), casted.getNewStartDate());
        assertEquals(LocalTime.of(15, 0), casted.getNewStartTime());
    }

    @Test
    public void testChangeStartEnd() {
        Command result = this.parser.parse("Change e8 to 21st April 2016 4pm to 6pm");
        assertEquals(true, result instanceof ChangeCommand);
        ChangeCommand casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 21), casted.getNewStartDate());
        assertEquals(LocalTime.of(16, 00), casted.getNewStartTime());
        assertEquals(LocalDate.of(2016, 4, 21), casted.getNewEndDate());
        assertEquals(LocalTime.of(18, 00), casted.getNewEndTime());

        result = this.parser.parse("CHANGE E10 TO 5.30pm to 10pm");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(10, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalTime.of(17, 30), casted.getNewStartTime());
        assertEquals(LocalTime.of(22, 00), casted.getNewEndTime());

        result = this.parser.parse("Change e8 to 21st April 2016 4pm to 22nd April 6pm");
        assertEquals(true, result instanceof ChangeCommand);
        casted = (ChangeCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
        assertEquals(LocalDate.of(2016, 4, 21), casted.getNewStartDate());
        assertEquals(LocalTime.of(16, 00), casted.getNewStartTime());
        assertEquals(LocalDate.of(2016, 4, 22), casted.getNewEndDate());
        assertEquals(LocalTime.of(18, 00), casted.getNewEndTime());
    }
    
    @Test
    public void testAliasAdd() {
        Command result = this.parser.parse("ALIas adD dElEtE DEL");
        assertEquals(true, result instanceof AliasAddCommand);
        AliasAddCommand casted = (AliasAddCommand) result;
        assertEquals("del", casted.getAlias());
        assertEquals("dElEtE", casted.getKeywordString());
        assertEquals(UserCommandLexer.DELETE, casted.getKeyword());
    }

    @Test
    public void testAliasAddInvalid() {
        Command result = this.parser.parse("ALIas adD MARK DELete");
        assertEquals(true, result instanceof InvalidCommand);
        InvalidCommand casted = (InvalidCommand) result;
        assertEquals("\"DELete\" is already a keyword and cannot be made an alias!",
                casted.getMessage());
    }
    
    @Test
    public void testAliasDelete() {
        Command result = this.parser.parse("ALIas dElEtE DeL");
        assertEquals(true, result instanceof AliasDeleteCommand);
        AliasDeleteCommand casted = (AliasDeleteCommand) result;
        assertEquals("del", casted.getAlias());
    }

    @Test
    public void testAliasList() {
        Command result = this.parser.parse("ALIas LiSt");
        assertEquals(true, result instanceof AliasListCommand);
        result = this.parser.parse("ALIas shoW");
        assertEquals(true, result instanceof AliasListCommand);
    }

    public void testShowHideOverdue() {
        Command result = this.parser.parse("show overdue");
        assertEquals(true, result instanceof ShowHideOverdueCommand);
        assertTrue(((ShowHideOverdueCommand)result).getWillShow());
        result = this.parser.parse("hide Overdue");
        assertEquals(true, result instanceof ShowHideOverdueCommand);
        assertFalse(((ShowHideOverdueCommand)result).getWillShow());
    }

    public void testShowHideCompleted() {
        Command result = this.parser.parse("show COMPLETED");
        assertEquals(true, result instanceof ShowHideCompletedCommand);
        assertTrue(((ShowHideCompletedCommand)result).getWillShow());
        result = this.parser.parse("hide Done");
        assertEquals(true, result instanceof ShowHideCompletedCommand);
        assertFalse(((ShowHideCompletedCommand)result).getWillShow());
    }
    
    public void testSaveLocation() {
        Command result = this.parser.parse("sAvE to save/data.json");
        assertTrue(result instanceof SaveLocationCommand);
        SaveLocationCommand casted = (SaveLocationCommand) result;
        assertEquals("save/data.json", casted.getSavePath());
    }
}
