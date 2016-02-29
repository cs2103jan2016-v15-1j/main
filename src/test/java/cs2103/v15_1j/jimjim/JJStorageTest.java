package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JJStorageTest {
	private JJStorage storage;

	@Before
	public void setUp() throws Exception {
		storage = new JJStorage("test.xml");
	}
	
	@Test
	public void testCreate() throws FileNotFoundException {
		Task task1 = new Task("task1", LocalDateTime.now());
		storage.create(task1);
		
		List<TaskEvent> list = storage.getAll();
		assertEquals(1, list.size());
	}
}
