//@@author A0131140E
package cs2103.v15_1j.jolt.parser;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.command.AddCommand;
import cs2103.v15_1j.jolt.command.Command;
import cs2103.v15_1j.jolt.command.InvalidCommand;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.TaskEvent;
import cs2103.v15_1j.jolt.parser.JoltParser;

public class JoltParserDateTimeTest {
	JoltParser parser;

	@Before
	public void setUp() throws Exception {
		this.parser = new JoltParser();
	}

	@Test
	public void testHour() {
		Command result = parser.parse("Go to sleep by 11 o'clock");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Go to sleep", deadlineTask.getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(11, 0), resultDateTime.toLocalTime());
	}

	@Test
	public void testInvalidHour() {
		Command result = parser.parse("Go to sleep by 25 o'clock");
		assertEquals(true, result instanceof InvalidCommand);
		InvalidCommand casted = (InvalidCommand) result;
		assertEquals("Invalid value for HourOfDay (valid values 0 - 23): 25",
				casted.getMessage());
	}
	
	@Test
	public void testHourMinute() {
		Command result = parser.parse("Go to bed by 11.30");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Go to bed", deadlineTask.getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(11, 30), resultDateTime.toLocalTime());
	}
	
	@Test
	public void testTodayTomorrow() {
		Command result = parser.parse("Finish CS2106 homework by today");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Finish CS2106 homework", deadlineTask.getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());
		
		result = parser.parse("Finish CS2106 homework by tomorrow");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Finish CS2106 homework", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate().plusDays(1), resultDateTime.toLocalDate());
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());
	}
	
	@Test
	public void testDateDayOfWeek() {
		Command result = parser.parse("Submit assignment 2 by Sunday");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		// ensure it's first sunday after today
		assertEquals(DayOfWeek.SUNDAY, resultDateTime.getDayOfWeek());
		assertEquals(true, resultDateTime.isAfter(now));
		assertTrue(resultDateTime.toLocalDate().minusDays(8)
		        .isBefore(now.toLocalDate()));
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());
		
		result = parser.parse("Submit assignment 2 by THIS mon");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		// ensure it's first monday after today
		assertEquals(DayOfWeek.MONDAY, resultDateTime.getDayOfWeek());
		assertEquals(true, resultDateTime.isAfter(now));
		assertTrue(resultDateTime.toLocalDate().minusDays(8)
		        .isBefore(now.toLocalDate()));
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by next mon");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		// ensure it's monday of next week
		assertEquals(DayOfWeek.MONDAY, resultDateTime.getDayOfWeek());
		assertEquals(true, resultDateTime.isAfter(now));
		assertEquals(true, resultDateTime.toLocalDate().minusDays(8)
		        .isBefore(now.toLocalDate()));
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by next SUNDAy");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		// ensure it's sunday of next week
		System.out.println(resultDateTime);
		assertEquals(DayOfWeek.SUNDAY, resultDateTime.getDayOfWeek());
		assertEquals(true, resultDateTime.toLocalDate().minusDays(14)
		        .isBefore(now.toLocalDate()));
		assertEquals(true, resultDateTime.toLocalDate().minusDays(6)
		        .isAfter(now.toLocalDate()));
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());
	}
	
	@Test
	public void testFullDate() {
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
	public void testInvalidFullDate() {
		Command result = parser.parse("Submit assignment 2 by 31/11/2016");
		assertEquals(true, result instanceof InvalidCommand);
		InvalidCommand casted = (InvalidCommand) result;
		assertEquals("Invalid date 'NOVEMBER 31'", casted.getMessage());
	}
	
	@Test
	public void testDayMonth() {
		Command result = parser.parse("Submit assignment 2 by 31/12");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(LocalDate.now().getYear(), 12, 31), resultDateTime.toLocalDate());
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());
	}
	
	@Test
	public void testDateAndTime() {
		Command result = parser.parse("Submit assignment 2 by 17.00 31/12");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(LocalDate.now().getYear(), 12, 31), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(17, 00), resultDateTime.toLocalTime());
		
		result = parser.parse("Submit assignment 2 by Friday 14.00");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		LocalDateTime now = LocalDateTime.now();
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(DayOfWeek.FRIDAY, resultDateTime.getDayOfWeek());
		assertEquals(true, resultDateTime.isAfter(now));
		assertEquals(LocalTime.of(14, 00), resultDateTime.toLocalTime());
	}

	@Test
	public void test12HourFormat() {
		Command result = parser.parse("Go to sleep by 11 pm");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Go to sleep", deadlineTask.getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 0), resultDateTime.toLocalTime());

		result = parser.parse("Go to sleep by 11.45 a.m.");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Go to sleep", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(11, 45), resultDateTime.toLocalTime());

		result = parser.parse("Go to sleep by 12:45 a.m.");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Go to sleep", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(0, 45), resultDateTime.toLocalTime());

		result = parser.parse("Go to sleep by 12:45 pm");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Go to sleep", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(12, 45), resultDateTime.toLocalTime());
	}
	
	@Test
	public void testInvalid12HourFormat() {
		Command result = parser.parse("Go to sleep by 13 a.m.");
		assertEquals(true, result instanceof InvalidCommand);
		InvalidCommand casted = (InvalidCommand) result;
		assertEquals("Invalid time, please use correct 12-hour format: 13",
				casted.getMessage());
	}

	@Test
	public void test12HourFormatWithoutSpace() {
		Command result = parser.parse("Go to sleep by 11pm");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Go to sleep", deadlineTask.getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 0), resultDateTime.toLocalTime());

		result = parser.parse("Go to sleep by 11.45am");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Go to sleep", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(11, 45), resultDateTime.toLocalTime());

		result = parser.parse("Go to sleep by 12:45am");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Go to sleep", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(0, 45), resultDateTime.toLocalTime());

		result = parser.parse("Go to sleep by 12:45pm");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Go to sleep", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(12, 45), resultDateTime.toLocalTime());
	}

	@Test
	public void testFullDateMonthWord() {
		Command result = parser.parse("Submit assignment 2 by 31-May-2016");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(2016, 5, 31), resultDateTime.toLocalDate());
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by 31 DECEMBER, 2016");
		System.out.println();
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(2016, 12, 31), resultDateTime.toLocalDate());
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by 30 apr");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(LocalDateTime.now().getYear(), 4, 30),
		        resultDateTime.toLocalDate());
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());
	}

	@Test
	public void testFullDateMonthWordMonthFirst() {
		Command result = parser.parse("Submit assignment 2 by FEB/20/2016");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(2016, 2, 20), resultDateTime.toLocalDate());
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by ocToBEr 15, 2016");
		System.out.println();
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(2016, 10, 15), resultDateTime.toLocalDate());
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by july 4");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(LocalDateTime.now().getYear(), 7, 4),
		        resultDateTime.toLocalDate());
		assertEquals(LocalTime.MAX, resultDateTime.toLocalTime());
	}

	@Test
	public void testFullDateTimeMonthWord() {
		Command result = parser.parse("Submit assignment 2 by 20 Feb 2016 5 pm");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(2016, 2, 20), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(17, 00), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by 11.50 ocToBEr 15, 2016");
		System.out.println();
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(2016, 10, 15), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(11, 50), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by july 4 12.00");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(LocalDateTime.now().getYear(), 7, 4),
		        resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(12, 0), resultDateTime.toLocalTime());
	}

	@Test
	public void testOrdinalEnding() {
		Command result = parser.parse("Submit assignment 2 by 20th Feb 2016 5 pm");
		assertEquals(true, result instanceof AddCommand);
		AddCommand casted = (AddCommand) result;
		TaskEvent taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		LocalDateTime resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(2016, 2, 20), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(17, 00), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by 11.50 ocToBEr 21st, 2016");
		System.out.println();
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(2016, 10, 21), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(11, 50), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by july 2nd 12.00");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(LocalDateTime.now().getYear(), 7, 2),
		        resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(12, 0), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by july 23rd 12.00");
		assertEquals(true, result instanceof AddCommand);
		casted = (AddCommand) result;
		taskEvent = casted.getTaskEvent();
		assertTrue(taskEvent instanceof DeadlineTask);
		deadlineTask = (DeadlineTask) taskEvent;
		assertEquals("Submit assignment 2", deadlineTask.getName());
		resultDateTime = deadlineTask.getDateTime();
		assertEquals(LocalDate.of(LocalDateTime.now().getYear(), 7, 23),
		        resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(12, 0), resultDateTime.toLocalTime());
	}

}
