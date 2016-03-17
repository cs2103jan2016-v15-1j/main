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

import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.storage.JJStorage;

public class JJStorageTest {
	private JJStorage storage;

	@Before
	public void setUp() {
		storage = new JJStorage();
		storage.setSaveFiles("tasks-test.json", "events-test.json");
	}
	
	@After
	public void tearDown() throws IOException {
		// Delete test JSON files after every test
		storage.getSavedTasksFile().delete();
		storage.getSavedEventsFile().delete();
	}
	
	/*
	 * Test that JJStorage is able to save file to disk
	 */
	@Test
	public void testSave() throws IOException {
		DeadlineTask task = new DeadlineTask("task", LocalDateTime.now());
		List<TaskEvent> list = new ArrayList<TaskEvent>();
		list.add(task);
		
		if (storage.save(list)) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(storage.getSavedTasksFile()));
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
		DeadlineTask task1 = new DeadlineTask("task1", dateTime1);
		DeadlineTask task2 = new DeadlineTask("task2", dateTime2);
		List<TaskEvent> list = new ArrayList<TaskEvent>();
		list.add(task1);
		list.add(task2);
		
		if (storage.save(list)) {
			List<TaskEvent> savedList = storage.load();
			assertEquals(2, savedList.size());
			
			DeadlineTask savedTask1 = (DeadlineTask) savedList.get(0);
			DeadlineTask savedTask2 = (DeadlineTask) savedList.get(1);
			
			// Assert that tasks returned are instances of Task
			assertEquals(true, savedTask1 instanceof DeadlineTask);
			assertEquals(true, savedTask2 instanceof DeadlineTask);

			// Assert that loaded tasks are identical to the tasks that were saved
			assertEquals("task1", savedTask1.getName());
			assertEquals(dateTime1, savedTask1.getDateTime());

			assertEquals("task2", savedList.get(1).getName());
			assertEquals(dateTime2, savedTask2.getDateTime());
		} else {
			fail("JJStorage was unable to save file.");
		}
	}
}
