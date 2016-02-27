package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

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

}
