package cs2103.v15_1j.jolt;

/* @@author A0124995R */

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

import cs2103.v15_1j.jolt.controller.Configuration;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;
import cs2103.v15_1j.jolt.storage.JoltStorage;

public class JoltStorageTest {
	private JoltStorage storage;

	@Before
	public void setUp() {
		storage = new JoltStorage();
		storage.setSaveFile("save_data_test.json");
		storage.setConfigFile("config_file_test.json");
	}
	
	@After
	public void tearDown() throws IOException {
		// Delete test JSON files after every test
		storage.getSaveFile().delete();
		storage.getConfigFile().delete();
	}
	
	/*
	 * Test that JJStorage is able to save file to disk
	 */
	@Test
	public void testSaveData() throws IOException {
		DeadlineTask task = new DeadlineTask("deadline_task", LocalDateTime.now());
		List<DeadlineTask> list = new ArrayList<DeadlineTask>();
		list.add(task);

		List<FloatingTask> floatingTasksList = new ArrayList<>();
		List<Event> eventsList = new ArrayList<>();
		DataLists dataLists = new DataLists(list, floatingTasksList, eventsList);
		
		if (storage.save(dataLists)) {
			BufferedReader bufferedReader = new BufferedReader(
			        new FileReader(storage.getSaveFile()));
			assertNotNull(bufferedReader.readLine());
			bufferedReader.close();
		}
	}
	
	/*
	 * Test that JJStorage is able to save config file
	 */
	@Test
	public void testSaveConfig() throws IOException {
		Configuration config = new Configuration();
		if (storage.saveConfig(config)) {
			BufferedReader bufferedReader = new BufferedReader(
			        new FileReader(storage.getConfigFile()));
			assertNotNull(bufferedReader.readLine());
			bufferedReader.close();
		}
	}
	
	/*
	 * Test that JJStorage is able to load config file
	 */
	@Test
	public void testLoadConfig() throws IOException {
		Configuration config = new Configuration();
		if (storage.saveConfig(config)) {
			Configuration savedConfig = storage.loadConfig();
			assertEquals(config, savedConfig);
		}
	}
	
	/*
	 * Test that JJStorage is able to successfully load data from disk
	 */
	@Test
	public void testLoadData() throws IOException {
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
