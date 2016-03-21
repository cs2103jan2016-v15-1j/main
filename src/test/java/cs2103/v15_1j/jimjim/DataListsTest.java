package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;

public class DataListsTest {

	DataLists displayList;
	DataLists masterList;

	@Before
	public void setUp() throws Exception {
		this.masterList = new DataLists();
		this.displayList = new DataLists();
	}

	@Test
	public void overloadedConstructorTest() {
		Event event1 = new Event("Event 1", LocalDateTime.of(2016, Month.MARCH, 30, 12, 00), 
				LocalDateTime.of(2016, Month.MARCH, 30, 13, 00));
		Event event2 = new Event("Event 2", LocalDateTime.of(2016, Month.MARCH, 31, 12, 00), 
				LocalDateTime.of(2016, Month.MARCH, 30, 13, 00));
		FloatingTask fTask1 = new FloatingTask("Task 1");
		FloatingTask fTask2 = new FloatingTask("Task 2");
		FloatingTask fTask3 = new FloatingTask("Task 3");

		masterList.add(event1);
		masterList.add(event2);
		masterList.add(fTask1);

		displayList = new DataLists(masterList);

		assertThat(displayList.getDeadlineTasksList(), IsEqual.equalTo(masterList.getDeadlineTasksList()));
		assertThat(displayList.getFloatingTasksList(), IsEqual.equalTo(masterList.getFloatingTasksList()));
		assertThat(displayList.getEventsList(), IsEqual.equalTo(masterList.getEventsList()));

		masterList.add(fTask2);
		displayList.add(fTask3);

		assertThat(displayList.getFloatingTasksList(), IsNot.not(IsEqual.equalTo(masterList.getFloatingTasksList())));
	}

	@Test
	public void sortedEventsTest() {
		Event event1 = new Event("C Event", LocalDateTime.of(2016, Month.MARCH, 30, 12, 00), 
				LocalDateTime.of(2016, Month.MARCH, 30, 13, 00));
		Event event2 = new Event("B Event", LocalDateTime.of(2016, Month.APRIL, 20, 12, 00), 
				LocalDateTime.of(2016, Month.MARCH, 30, 13, 00));
		Event event3 = new Event("A Event", LocalDateTime.of(2016, Month.MARCH, 30, 12, 00), 
				LocalDateTime.of(2016, Month.MARCH, 30, 13, 00));
		Event event4 = new Event("D Event", LocalDateTime.of(2016, Month.MARCH, 31, 12, 00), 
				LocalDateTime.of(2016, Month.MARCH, 30, 13, 00));

		masterList.add(event1);
		masterList.add(event2);
		masterList.add(event3);
		masterList.add(event4);

		List<Event> eventsUnsortedList = new ArrayList<Event>();

		eventsUnsortedList.add(event1);
		eventsUnsortedList.add(event2);
		eventsUnsortedList.add(event3);
		eventsUnsortedList.add(event4);

		assertThat(masterList.getEventsList(), IsNot.not(IsEqual.equalTo(eventsUnsortedList)));

		List<Event> eventsSortedList = new ArrayList<Event>();

		eventsSortedList.add(event3);
		eventsSortedList.add(event1);
		eventsSortedList.add(event4);
		eventsSortedList.add(event2);

		assertThat(masterList.getEventsList(), IsEqual.equalTo(eventsSortedList));
	}

	@Test
	public void sortedDeadlineTasksTest() {
		DeadlineTask task1 = new DeadlineTask("C Task", LocalDateTime.of(2016, Month.MARCH, 30, 12, 00));
		DeadlineTask task2 = new DeadlineTask("B Task", LocalDateTime.of(2016, Month.JANUARY, 30, 12, 00));
		DeadlineTask task3 = new DeadlineTask("A Task", LocalDateTime.of(2016, Month.MARCH, 30, 12, 00));
		DeadlineTask task4 = new DeadlineTask("D Task", LocalDateTime.of(2016, Month.APRIL, 30, 12, 00));

		masterList.add(task1);
		masterList.add(task2);
		masterList.add(task3);
		masterList.add(task4);

		List<DeadlineTask> dTasksUnsortedList = new ArrayList<DeadlineTask>();

		dTasksUnsortedList.add(task1);
		dTasksUnsortedList.add(task2);
		dTasksUnsortedList.add(task3);
		dTasksUnsortedList.add(task4);

		assertThat(masterList.getDeadlineTasksList(), IsNot.not(IsEqual.equalTo(dTasksUnsortedList)));

		List<DeadlineTask> dTasksSortedList = new ArrayList<DeadlineTask>();

		dTasksSortedList.add(task2);
		dTasksSortedList.add(task3);
		dTasksSortedList.add(task1);
		dTasksSortedList.add(task4);

		assertThat(masterList.getDeadlineTasksList(), IsEqual.equalTo(dTasksSortedList));
	}

	@Test
	public void sortedFloatingTasksTest() {
		FloatingTask task1 = new FloatingTask("C Task");
		FloatingTask task2 = new FloatingTask("B Task");
		FloatingTask task3 = new FloatingTask("A Task");
		FloatingTask task4 = new FloatingTask("D Task");

		masterList.add(task1);
		masterList.add(task2);
		masterList.add(task3);
		masterList.add(task4);

		List<FloatingTask> fTasksUnsortedList = new ArrayList<FloatingTask>();

		fTasksUnsortedList.add(task1);
		fTasksUnsortedList.add(task2);
		fTasksUnsortedList.add(task3);
		fTasksUnsortedList.add(task4);

		assertThat(masterList.getFloatingTasksList(), IsNot.not(IsEqual.equalTo(fTasksUnsortedList)));

		List<FloatingTask> fTasksSortedList = new ArrayList<FloatingTask>();

		fTasksSortedList.add(task3);
		fTasksSortedList.add(task2);
		fTasksSortedList.add(task1);
		fTasksSortedList.add(task4);

		assertThat(masterList.getFloatingTasksList(), IsEqual.equalTo(fTasksSortedList));
	}

}
