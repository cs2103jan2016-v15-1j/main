package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

public class JJParserTest {
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
}
