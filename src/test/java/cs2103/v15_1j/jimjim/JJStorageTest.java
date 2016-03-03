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

public class JJStorageTest {
	private JJStorage storage;

	@Before
	public void setUp() {
		storage = new JJStorage();
		storage.setSaveFile("test.json");
	}
	
	@After
	public void tearDown() throws IOException {
		// Delete test.json file after every test
		storage.getSaveFile().delete();
	}
	
	/*
	 * Test that JJStorage is able to save file to disk
	 */
	@Test
	public void testSave() throws IOException {
		Task task = new Task("task", LocalDateTime.now());
		List<TaskEvent> list = new ArrayList<TaskEvent>();
		list.add(task);
		
		if (storage.save(list)) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(storage.getSaveFile()));
			assertNotNull(bufferedReader.readLine());
			bufferedReader.close();
		}
	}
	
	/*
	 * Test that JJStorage is able to successfully load data from disk
	 */
	@Test
	public void testLoad() throws IOException {
		Task task1 = new Task("task1", LocalDateTime.now());
		Task task2 = new Task("task2", LocalDateTime.now());
		List<TaskEvent> list = new ArrayList<TaskEvent>();
		list.add(task1);
		list.add(task2);
		
		if (storage.save(list)) {
			List<TaskEvent> savedList = storage.loadFromDisk();
			assertEquals(2, savedList.size());
		} else {
			fail("JJStorage was unable to save file.");
		}
	}
}
