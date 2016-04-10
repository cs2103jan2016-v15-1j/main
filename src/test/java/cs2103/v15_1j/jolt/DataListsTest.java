package cs2103.v15_1j.jolt;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;

//@@author A0139963N
public class DataListsTest {

	DataLists displayList;
	DataLists masterList;

	@Before
	public void setUp() throws Exception {
		this.masterList = new DataLists();
		this.displayList = new DataLists();
	}

	@Test
	public void copyTest() {
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

        displayList = new DataLists();
        displayList.copy(masterList);

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

		List<Event> eventsSortedList = new ArrayList<Event>();

		eventsSortedList.add(event3);
		eventsSortedList.add(event1);
		eventsSortedList.add(event4);
		eventsSortedList.add(event2);

		assertThat(masterList.getEventsList(), IsEqual.equalTo(eventsSortedList));
	}

	@Test
	public void unsortedEventsTest() {
		Event event1 = new Event("C Event", LocalDateTime.of(2016, Month.MARCH, 30, 12, 00), 
				LocalDateTime.of(2016, Month.MARCH, 30, 13, 00));
		Event event2 = new Event("B Event", LocalDateTime.of(2016, Month.APRIL, 20, 12, 00), 
				LocalDateTime.of(2016, Month.MARCH, 30, 13, 00));
		Event event3 = new Event("A Event", LocalDateTime.of(2016, Month.MARCH, 30, 12, 00), 
				LocalDateTime.of(2016, Month.MARCH, 30, 13, 00));

		masterList.addWithoutSorting(event1);
		masterList.addWithoutSorting(event2);
		masterList.addWithoutSorting(event3);

		List<Event> eventsSortedList = new ArrayList<Event>();

		eventsSortedList.add(event1);
		eventsSortedList.add(event2);
		eventsSortedList.add(event3);

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

		List<DeadlineTask> dTasksSortedList = new ArrayList<DeadlineTask>();

		dTasksSortedList.add(task2);
		dTasksSortedList.add(task3);
		dTasksSortedList.add(task1);
		dTasksSortedList.add(task4);

		assertThat(masterList.getDeadlineTasksList(), IsEqual.equalTo(dTasksSortedList));
	}

	@Test
	public void unsortedDeadlineTasksTest() {
		DeadlineTask task1 = new DeadlineTask("C Task", LocalDateTime.of(2016, Month.MARCH, 30, 12, 00));
		DeadlineTask task2 = new DeadlineTask("B Task", LocalDateTime.of(2016, Month.JANUARY, 30, 12, 00));
		DeadlineTask task3 = new DeadlineTask("A Task", LocalDateTime.of(2016, Month.MARCH, 30, 12, 00));

		masterList.addWithoutSorting(task1);
		masterList.addWithoutSorting(task2);
		masterList.addWithoutSorting(task3);

		List<DeadlineTask> dTasksUnsortedList = new ArrayList<DeadlineTask>();

		dTasksUnsortedList.add(task1);
		dTasksUnsortedList.add(task2);
		dTasksUnsortedList.add(task3);

		assertThat(masterList.getDeadlineTasksList(), IsEqual.equalTo(dTasksUnsortedList));
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

		List<FloatingTask> fTasksSortedList = new ArrayList<FloatingTask>();

		fTasksSortedList.add(task3);
		fTasksSortedList.add(task2);
		fTasksSortedList.add(task1);
		fTasksSortedList.add(task4);

		assertThat(masterList.getFloatingTasksList(), IsEqual.equalTo(fTasksSortedList));
	}
	
	@Test
	public void unsortedFloatingTasksTest() {
		FloatingTask task1 = new FloatingTask("C Task");
		FloatingTask task2 = new FloatingTask("B Task");
		FloatingTask task3 = new FloatingTask("A Task");

		masterList.addWithoutSorting(task1);
		masterList.addWithoutSorting(task2);
		masterList.addWithoutSorting(task3);

		List<FloatingTask> fTasksUnsortedList = new ArrayList<FloatingTask>();

		fTasksUnsortedList.add(task1);
		fTasksUnsortedList.add(task2);
		fTasksUnsortedList.add(task3);

		assertThat(masterList.getFloatingTasksList(), IsEqual.equalTo(fTasksUnsortedList));
	}
	
	@Test
	public void removeTest() {
		FloatingTask fTask1 = new FloatingTask("A Task");
		FloatingTask fTask2 = new FloatingTask("B Task");
		DeadlineTask dTask1 = new DeadlineTask("A Task", LocalDate.now().atTime(LocalTime.MAX));
		DeadlineTask dTask2 = new DeadlineTask("B Task", LocalDate.now().atTime(LocalTime.MAX));
		DeadlineTask dTask3 = new DeadlineTask("C Task", LocalDate.now().atTime(LocalTime.MAX));
		Event event1 = new Event("A Event", LocalDate.now().atTime(LocalTime.MIN), LocalDate.now().atTime(LocalTime.MAX));
		Event event2 = new Event("B Event", LocalDate.now().atTime(LocalTime.MIN), LocalDate.now().atTime(LocalTime.MAX));

		masterList.add(fTask1);
		masterList.add(fTask2);
		masterList.add(dTask1);
		masterList.add(dTask2);
		masterList.add(dTask3);
		masterList.add(event1);
		masterList.add(event2);
		
		masterList.remove(0, 'f');
		masterList.remove(1, 'd');
		masterList.remove(1, 'e');

		DataLists deletedList = new DataLists();

		deletedList.add(fTask2);
		deletedList.add(dTask1);
		deletedList.add(dTask3);
		deletedList.add(event1);

		assertThat(masterList.getFloatingTasksList(), IsEqual.equalTo(deletedList.getFloatingTasksList()));
		assertThat(masterList.getDeadlineTasksList(), IsEqual.equalTo(deletedList.getDeadlineTasksList()));
		assertThat(masterList.getEventsList(), IsEqual.equalTo(deletedList.getEventsList()));
	}
	
	@Test
	public void containsTest() {
		FloatingTask fTask1 = new FloatingTask("A Task");
		FloatingTask fTask2 = new FloatingTask("B Task");
		DeadlineTask dTask1 = new DeadlineTask("A Task", LocalDate.now().atTime(LocalTime.MAX));
		Event event1 = new Event("A Event", LocalDate.now().atTime(LocalTime.MIN), LocalDate.now().atTime(LocalTime.MAX));

		masterList.add(fTask1);
		masterList.add(dTask1);
		masterList.add(event1);

		assertThat(masterList.contains(fTask1), IsEqual.equalTo(true));
		assertThat(masterList.contains(fTask2), IsEqual.equalTo(false));
		assertThat(masterList.contains(dTask1), IsEqual.equalTo(true));
		assertThat(masterList.contains(event1), IsEqual.equalTo(true));
	}
	

	@Test
	public void sizeTest() {
		FloatingTask fTask1 = new FloatingTask("A Task");
		FloatingTask fTask2 = new FloatingTask("B Task");
		DeadlineTask dTask1 = new DeadlineTask("A Task", LocalDate.now().atTime(LocalTime.MAX));
		DeadlineTask dTask2 = new DeadlineTask("B Task", LocalDate.now().atTime(LocalTime.MAX));
		DeadlineTask dTask3 = new DeadlineTask("C Task", LocalDate.now().atTime(LocalTime.MAX));
		Event event1 = new Event("A Event", LocalDate.now().atTime(LocalTime.MIN), LocalDate.now().atTime(LocalTime.MAX));

		masterList.add(fTask1);
		masterList.add(fTask2);
		masterList.add(dTask1);
		masterList.add(dTask2);
		masterList.add(dTask3);
		masterList.add(event1);

		assertThat(masterList.size(), IsEqual.equalTo(6));
		assertThat(masterList.size('f'), IsEqual.equalTo(2));
		assertThat(masterList.size('d'), IsEqual.equalTo(3));
		assertThat(masterList.size('e'), IsEqual.equalTo(1));
	}

}
