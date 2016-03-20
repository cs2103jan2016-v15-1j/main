package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.storage.JJStorage;

public class JJStorageTest {
	private JJStorage storage;

	@Before
	public void setUp() {
		storage = new JJStorage();
		storage.setSaveFiles("floating_tasks_test.json", "deadline_tasks_test.json", "events_test.json");
	}
	
	@After
	public void tearDown() throws IOException {
		// Delete test JSON files after every test
		storage.getSavedFloatingTasksFile().delete();
		storage.getSavedDeadlineTasksFile().delete();
		storage.getSavedEventsFile().delete();
	}
	
	/*
	 * Test that JJStorage is able to save file to disk
	 */
	@Test
	public void testSave() throws IOException {
		DeadlineTask task = new DeadlineTask("deadline_task", LocalDateTime.now());
		List<DeadlineTask> list = new ArrayList<DeadlineTask>();
		list.add(task);

		List<FloatingTask> floatingTasksList = new ArrayList<>();
		List<Event> eventsList = new ArrayList<>();
		DataLists dataLists = new DataLists(list, floatingTasksList, eventsList);
		
		if (storage.save(dataLists)) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(storage.getSavedDeadlineTasksFile()));
			assertNotNull(bufferedReader.readLine());
			bufferedReader.close();
		}
	}
	
	/*
	 * Test that JJStorage is able to successfully load data from disk
	 */
	@Test
	public void testLoad() throws IOException {
		LocalDateTime dateTime1 = LocalDateTime.of(2016, 3, 6, 4, 37);
		LocalDateTime dateTime2 = LocalDateTime.of(2015, 3, 6, 4, 37);
		DeadlineTask deadlineTask1 = new DeadlineTask("task1", dateTime1);
		DeadlineTask deadlineTask2 = new DeadlineTask("task2", dateTime2);
		List<DeadlineTask> list = new ArrayList<DeadlineTask>();
		list.add(deadlineTask1);
		list.add(deadlineTask2);
		
		List<FloatingTask> floatingTasksList = new ArrayList<>();
		List<Event> eventsList = new ArrayList<>();
		DataLists result = new DataLists(list, floatingTasksList, eventsList);
		
		if (storage.save(result)) {
//			List<TaskEvent> savedList = storage.load();
			DataLists savedDataLists = storage.load();
			List<DeadlineTask> savedDeadlineTasksList = savedDataLists.getDeadlineTasksList();
			assertEquals(2, savedDeadlineTasksList.size());
			
			DeadlineTask savedDeadlineTask1 = (DeadlineTask) savedDeadlineTasksList.get(0);
			DeadlineTask savedDeadlineTask2 = (DeadlineTask) savedDeadlineTasksList.get(1);
			
			// Assert that tasks returned are instances of Task
			assertEquals(true, savedDeadlineTask1 instanceof DeadlineTask);
			assertEquals(true, savedDeadlineTask2 instanceof DeadlineTask);

			// Assert that loaded tasks are identical to the tasks that were saved
			assertEquals("task1", savedDeadlineTask1.getName());
			assertEquals(dateTime1, savedDeadlineTask1.getDateTime());

			assertEquals("task2", savedDeadlineTask2.getName());
			assertEquals(dateTime2, savedDeadlineTask2.getDateTime());
		} else {
			fail("JJStorage was unable to save file.");
		}
	}
}
