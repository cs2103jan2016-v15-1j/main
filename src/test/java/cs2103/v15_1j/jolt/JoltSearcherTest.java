package cs2103.v15_1j.jolt;

/* @@author A0124995R */

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;
import cs2103.v15_1j.jolt.searcher.DateTimeFilter;
import cs2103.v15_1j.jolt.searcher.Filter;
import cs2103.v15_1j.jolt.searcher.JoltSearcher;
import cs2103.v15_1j.jolt.searcher.KeywordFilter;
import cs2103.v15_1j.jolt.searcher.TimeFilter;

public class JoltSearcherTest {
	JoltSearcher JJSearcher;

	@Before
	public void setUp() throws Exception {
		JJSearcher = new JoltSearcher();
	}

	@Test
	public void testDateTimeFilter() {
		List<DeadlineTask> deadlineTasksList = new ArrayList<DeadlineTask>();
		LocalDateTime deadline1 = LocalDateTime.of(2016, 4, 6, 4, 37);
		DeadlineTask deadlineTask1 = new DeadlineTask("deadlineTask1", deadline1);
		deadlineTasksList.add(deadlineTask1);
		
		List<FloatingTask> floatingTasksList = new ArrayList<FloatingTask>();
		FloatingTask floatingTask1 = new FloatingTask("floatingTask1");
		floatingTasksList.add(floatingTask1);
		
		List<Event> eventsList = new ArrayList<Event>();
		LocalDateTime eventStart = LocalDateTime.of(2016, 4, 6, 4, 37);
		LocalDateTime eventEnd = LocalDateTime.of(2016, 4, 6, 8, 37);
		Event event1 = new Event("event1", eventStart, eventEnd);
		eventsList.add(event1);
		
		DataLists masterLists = new DataLists(deadlineTasksList, floatingTasksList, eventsList);
		
		Filter filter1 = new DateTimeFilter(LocalDateTime.of(2016, 4, 6, 3, 37),
											LocalDateTime.of(2016, 4, 6, 9, 37));
		
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(filter1);
		
		DataLists result = JJSearcher.search(filters, masterLists);
		List<DeadlineTask> resultDeadlineTasksList = result.getDeadlineTasksList();
		List<FloatingTask> resultFloatingTasksList = result.getFloatingTasksList();
		List<Event> resultEventsList = result.getEventsList();
		
		assertEquals(0, resultFloatingTasksList.size());
		assertEquals(1, resultDeadlineTasksList.size());
		assertEquals(1, resultEventsList.size());
		
		assertEquals(deadlineTask1, resultDeadlineTasksList.get(0));
		assertEquals(event1, resultEventsList.get(0));
	}
	
	@Test
	public void testKeywordFilter() {
		List<DeadlineTask> deadlineTasksList = new ArrayList<DeadlineTask>();
		LocalDateTime deadline1 = LocalDateTime.now();
		// Test that a substring does not pass the filter
		DeadlineTask deadlineTask1 = new DeadlineTask("buy leggs", deadline1);
		deadlineTasksList.add(deadlineTask1);
		
		List<FloatingTask> floatingTasksList = new ArrayList<FloatingTask>();
		FloatingTask floatingTask1 = new FloatingTask("buy eggs");
		// Test that only single character keywords pass the filter
		FloatingTask floatingTask2 = new FloatingTask("Eenie meenie");
		floatingTasksList.add(floatingTask1);
		floatingTasksList.add(floatingTask2);
		
		List<Event> eventsList = new ArrayList<Event>();
		LocalDateTime eventStart = LocalDateTime.of(2016, 4, 6, 4, 37);
		LocalDateTime eventEnd = LocalDateTime.now();
		Event event1 = new Event("buying of eggs", eventStart, eventEnd);
		eventsList.add(event1);
		
		DataLists masterLists = new DataLists(deadlineTasksList, floatingTasksList, eventsList);
		
		List<String> keywords = new ArrayList<String>();
		keywords.add("Eggs"); // Test case-sensitivity
		Filter filter1 = new KeywordFilter(keywords);
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(filter1);
		
		DataLists result = JJSearcher.search(filters, masterLists);
		List<DeadlineTask> resultDeadlineTasksList = result.getDeadlineTasksList();
		List<FloatingTask> resultFloatingTasksList = result.getFloatingTasksList();
		List<Event> resultEventsList = result.getEventsList();
		
		assertEquals(0, resultDeadlineTasksList.size());
		assertEquals(1, resultFloatingTasksList.size());
		assertEquals(1, resultEventsList.size());
		
		assertEquals(floatingTask1, resultFloatingTasksList.get(0));
		assertEquals(event1, resultEventsList.get(0));
	}
	
	@Test
	public void testKeywordInitialCharacterIgnoreCase() {
		List<DeadlineTask> deadlineTasksList = new ArrayList<DeadlineTask>();
		LocalDateTime deadline1 = LocalDateTime.now();
		// Test that a substring does not pass the filter
		DeadlineTask deadlineTask1 = new DeadlineTask("buy leggs", deadline1);
		deadlineTasksList.add(deadlineTask1);
		
		List<FloatingTask> floatingTasksList = new ArrayList<FloatingTask>();
		FloatingTask floatingTask1 = new FloatingTask("buy eggs");
		floatingTasksList.add(floatingTask1);
		
		List<Event> eventsList = new ArrayList<Event>();
		LocalDateTime eventStart = LocalDateTime.of(2016, 4, 6, 4, 37);
		LocalDateTime eventEnd = LocalDateTime.now();
		Event event1 = new Event("Buying of eggs", eventStart, eventEnd);
		// Test that keyword filter does not match first character of word,
		// if word is not first word of TaskEvent name
		Event event2 = new Event("Eggs of buying", eventStart, eventEnd);
		eventsList.add(event1);
		eventsList.add(event2);
		
		DataLists masterLists = new DataLists(deadlineTasksList, floatingTasksList, eventsList);
		
		List<String> keywords = new ArrayList<String>();
		keywords.add("B"); // Test case-sensitivity
		Filter filter1 = new KeywordFilter(keywords);
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(filter1);
		
		DataLists result = JJSearcher.search(filters, masterLists);
		List<DeadlineTask> resultDeadlineTasksList = result.getDeadlineTasksList();
		List<FloatingTask> resultFloatingTasksList = result.getFloatingTasksList();
		List<Event> resultEventsList = result.getEventsList();
		
		assertEquals(1, resultDeadlineTasksList.size());
		assertEquals(1, resultFloatingTasksList.size());
		assertEquals(1, resultEventsList.size());
		
		assertEquals(floatingTask1, resultFloatingTasksList.get(0));
		assertEquals(event1, resultEventsList.get(0));
	}
	
	@Test
	public void testTimeFilter() {
		List<DeadlineTask> deadlineTasksList = new ArrayList<DeadlineTask>();
		LocalDateTime deadline1 = LocalDateTime.of(2016, 4, 6, 4, 37);
		DeadlineTask deadlineTask1 = new DeadlineTask("deadlineTask1", deadline1);
		deadlineTasksList.add(deadlineTask1);
		
		List<FloatingTask> floatingTasksList = new ArrayList<FloatingTask>();
		FloatingTask floatingTask1 = new FloatingTask("floatingTask1");
		floatingTasksList.add(floatingTask1);
		
		List<Event> eventsList = new ArrayList<Event>();
		LocalDateTime eventStart = LocalDateTime.of(2016, 4, 6, 4, 37);
		LocalDateTime eventEnd = LocalDateTime.of(2016, 4, 6, 8, 37);
		Event event1 = new Event("event1", eventStart, eventEnd);
		eventsList.add(event1);
		
		DataLists masterLists = new DataLists(deadlineTasksList, floatingTasksList, eventsList);
		
		Filter filter1 = new TimeFilter(LocalDateTime.of(2016, 4, 6, 3, 37).toLocalTime(),
										LocalDateTime.of(2016, 4, 6, 9, 37).toLocalTime());
		
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(filter1);
		
		DataLists result = JJSearcher.search(filters, masterLists);
		List<DeadlineTask> resultDeadlineTasksList = result.getDeadlineTasksList();
		List<FloatingTask> resultFloatingTasksList = result.getFloatingTasksList();
		List<Event> resultEventsList = result.getEventsList();
		
		assertEquals(0, resultFloatingTasksList.size());
		assertEquals(1, resultDeadlineTasksList.size());
		assertEquals(1, resultEventsList.size());
		
		assertEquals(deadlineTask1, resultDeadlineTasksList.get(0));
		assertEquals(event1, resultEventsList.get(0));
	}

}
