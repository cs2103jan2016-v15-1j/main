package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JJParserAddTest {
	JJParser parser;

	@Before
	public void setUp() throws Exception {
		this.parser = new JJParser();
	}

	@Test
	public void testAddFloatingTask() {
		Command result = parser.parse("Learn 10 new words every day");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Learn 10 new words every day", casted.getTask().getName());
		assertEquals(null, casted.getTask().getDateTime());
	}
	
	@Test
	public void testAddTaskWithHour() {
		Command result = parser.parse("Go to sleep by 11");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Go to sleep", casted.getTask().getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(11, 0), resultDateTime.toLocalTime());
	}
	
	@Test
	public void testInvalidHour() {
		Command result = parser.parse("Go to sleep by 25");
		assertEquals(true, result instanceof InvalidCommand);
		InvalidCommand casted = (InvalidCommand) result;
		assertEquals("Invalid value for HourOfDay (valid values 0 - 23): 25",
				casted.getMessage());
	}
	
	@Test
	public void testAddTaskWithHourMinute() {
		Command result = parser.parse("Go to bed by 11.30");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Go to bed", casted.getTask().getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(11, 30), resultDateTime.toLocalTime());
	}
	
	@Test
	public void testAddTaskTodayTomorrow() {
		Command result = parser.parse("Finish CS2106 homework by today");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Finish CS2106 homework", casted.getTask().getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());
		
		result = parser.parse("Finish CS2106 homework by tomorrow");
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Finish CS2106 homework", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(now.toLocalDate().plusDays(1), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());
	}
	
	@Test
	public void testDateDayOfWeek() {
		Command result = parser.parse("Submit assignment 2 by Sunday");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(DayOfWeek.SUNDAY, resultDateTime.getDayOfWeek());
		assertEquals(true, resultDateTime.isAfter(now));
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());
		
		result = parser.parse("Submit assignment 2 by mon");
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(DayOfWeek.MONDAY, resultDateTime.getDayOfWeek());
		assertEquals(true, resultDateTime.isAfter(now));
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());
	}
	
	@Test
	public void testFullDate() {
		Command result = parser.parse("Submit assignment 2 by 31/12/2016");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(2016, 12, 31), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());
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
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(LocalDate.now().getYear(), 12, 31), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());
	}
	
	@Test
	public void testAddTaskWithDateAndTime() {
		Command result = parser.parse("Submit assignment 2 by 17.00 31/12");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(LocalDate.now().getYear(), 12, 31), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(17, 00), resultDateTime.toLocalTime());
		
		result = parser.parse("Submit assignment 2 by Friday 14");
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		LocalDateTime now = LocalDateTime.now();
		assertEquals("Submit assignment 2", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(DayOfWeek.FRIDAY, resultDateTime.getDayOfWeek());
		assertEquals(true, resultDateTime.isAfter(now));
		assertEquals(LocalTime.of(14, 00), resultDateTime.toLocalTime());
	}

	@Test
	public void test12HourFormat() {
		Command result = parser.parse("Go to sleep by 11 pm");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Go to sleep", casted.getTask().getName());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 0), resultDateTime.toLocalTime());

		result = parser.parse("Go to sleep by 11.45 a.m.");
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Go to sleep", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(11, 45), resultDateTime.toLocalTime());

		result = parser.parse("Go to sleep by 12:45 a.m.");
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Go to sleep", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(now.toLocalDate(), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(0, 45), resultDateTime.toLocalTime());

		result = parser.parse("Go to sleep by 12:45 pm");
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Go to sleep", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
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
	public void testFullDateMonthWord() {
		Command result = parser.parse("Submit assignment 2 by 31-May-2016");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(2016, 5, 31), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by 31 DECEMBER, 2016");
		System.out.println();
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(2016, 12, 31), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by 30 apr");
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(LocalDateTime.now().getYear(), 4, 30),
		        resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());
	}

	@Test
	public void testFullDateMonthWordMonthFirst() {
		Command result = parser.parse("Submit assignment 2 by FEB/20/2016");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(2016, 2, 20), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by ocToBEr 15, 2016");
		System.out.println();
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(2016, 10, 15), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by july 4");
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(LocalDateTime.now().getYear(), 7, 4),
		        resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(23, 59), resultDateTime.toLocalTime());
	}

	@Test
	public void testFullDateTimeMonthWord() {
		Command result = parser.parse("Submit assignment 2 by 20 Feb 2016 5 pm");
		assertEquals(true, result instanceof AddTaskCommand);
		AddTaskCommand casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		LocalDateTime resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(2016, 2, 20), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(17, 00), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by 11.50 ocToBEr 15, 2016");
		System.out.println();
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(2016, 10, 15), resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(11, 50), resultDateTime.toLocalTime());

		result = parser.parse("Submit assignment 2 by july 4 12.00");
		assertEquals(true, result instanceof AddTaskCommand);
		casted = (AddTaskCommand) result;
		assertEquals("Submit assignment 2", casted.getTask().getName());
		resultDateTime = casted.getTask().getDateTime();
		assertEquals(LocalDate.of(LocalDateTime.now().getYear(), 7, 4),
		        resultDateTime.toLocalDate());
		assertEquals(LocalTime.of(12, 0), resultDateTime.toLocalTime());
	}

	@Test
	public void testEventCommonDate() {
		Command result = parser.parse(
		        "Group meeting on 20 Feb from 1:30 pm to 3 pm");
		assertEquals(true, result instanceof AddEventCommand);
		AddEventCommand casted = (AddEventCommand) result;
		assertEquals("Group meeting", casted.getEvent().getName());
		List<EventTime> resultDateTime = casted.getEvent().getDateTime();
		assertEquals(1, resultDateTime.size());
		EventTime timing = resultDateTime.get(0);
		assertEquals(LocalDate.of(2016, 2, 20), timing.start.toLocalDate());
		assertEquals(LocalTime.of(13, 30), timing.start.toLocalTime());
		assertEquals(LocalDate.of(2016, 2, 20), timing.end.toLocalDate());
		assertEquals(LocalTime.of(15, 00), timing.end.toLocalTime());

		result = parser.parse(
		        "Group meeting 20 Feb from 1:30 pm to 3 pm");
		assertEquals(true, result instanceof AddEventCommand);
		casted = (AddEventCommand) result;
		assertEquals("Group meeting", casted.getEvent().getName());
		resultDateTime = casted.getEvent().getDateTime();
		assertEquals(1, resultDateTime.size());
		timing = resultDateTime.get(0);
		assertEquals(LocalDate.of(2016, 2, 20), timing.start.toLocalDate());
		assertEquals(LocalTime.of(13, 30), timing.start.toLocalTime());
		assertEquals(LocalDate.of(2016, 2, 20), timing.end.toLocalDate());
		assertEquals(LocalTime.of(15, 00), timing.end.toLocalTime());
	}

	@Test
	public void testEventDiffDate() {
		Command result = parser.parse(
		        "Camping with friends from June 1 2016 9:00 am to June 3 5:00 pm");
		assertEquals(true, result instanceof AddEventCommand);
		AddEventCommand casted = (AddEventCommand) result;
		assertEquals("Camping with friends", casted.getEvent().getName());
		List<EventTime> resultDateTime = casted.getEvent().getDateTime();
		assertEquals(1, resultDateTime.size());
		EventTime timing = resultDateTime.get(0);
		assertEquals(LocalDate.of(2016, 6, 1), timing.start.toLocalDate());
		assertEquals(LocalTime.of(9, 00), timing.start.toLocalTime());
		assertEquals(LocalDate.of(2016, 6, 3), timing.end.toLocalDate());
		assertEquals(LocalTime.of(17, 00), timing.end.toLocalTime());

	}
}
