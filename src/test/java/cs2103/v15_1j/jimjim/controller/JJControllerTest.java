package cs2103.v15_1j.jimjim.controller;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.parser.JJParser;
import cs2103.v15_1j.jimjim.searcher.JJSearcher;
import cs2103.v15_1j.jimjim.storage.JJStorage;
import cs2103.v15_1j.jimjim.uifeedback.AddFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class JJControllerTest {

	private final String DEADLINE_TASK_FILE_NAME = "deadline_tasks_test.json";
	private final String TASK_FILE_NAME = "floating_tasks_test.json";
	private final String EVENT_FILE_NAME = "events_test.json";

    private JJController controller;
    private JJStorage storage;
    private JJParser parser;
    private JJSearcher searcher;
    
    @Before
    public void setUp() throws Exception {
        this.controller = new JJController();
		this.storage = new JJStorage();
		this.parser = new JJParser();
		this.searcher = new JJSearcher();

		storage.setSaveFiles(TASK_FILE_NAME, DEADLINE_TASK_FILE_NAME, EVENT_FILE_NAME);
		controller.setParser(parser);
		controller.setStorage(storage);
		controller.setSearcher(searcher);
    }

	@After
	public void tearDown() throws IOException {
		// Delete test JSON files after every test
		storage.getSavedFloatingTasksFile().delete();
		storage.getSavedDeadlineTasksFile().delete();
		storage.getSavedEventsFile().delete();
	}

    @Test
    public void testEmptyInit() {
        assertTrue(controller.getMasterList().getDeadlineTasksList().isEmpty());
        assertTrue(controller.getMasterList().getFloatingTasksList().isEmpty());
        assertTrue(controller.getMasterList().getEventsList().isEmpty());

        assertTrue(controller.getSearchResultsList().getDeadlineTasksList().isEmpty());
        assertTrue(controller.getSearchResultsList().getFloatingTasksList().isEmpty());
        assertTrue(controller.getSearchResultsList().getEventsList().isEmpty());
    }
    
    @Test
    public void testAdd() {
        UIFeedback feedback = controller.execute("Prepare for German exams");
        // it is added properly
        assertEquals(1, controller.getMasterList().getFloatingTasksList().size());
        FloatingTask floatingTask = controller.getMasterList()
                .getFloatingTasksList().get(0);
        assertEquals("Prepare for German exams", floatingTask.getName());
        // it returns the right feedback
        assertTrue(feedback instanceof AddFeedback);
        AddFeedback addFeedback = (AddFeedback) feedback;
        assertEquals(floatingTask, addFeedback.getTaskEvent());
        // it is saved to disk
        DataLists listReadFromDisk = storage.load();
        assertEquals(1, listReadFromDisk.getFloatingTasksList().size());
        floatingTask = listReadFromDisk
                .getFloatingTasksList().get(0);
        assertEquals("Prepare for German exams", floatingTask.getName());
    }

}
