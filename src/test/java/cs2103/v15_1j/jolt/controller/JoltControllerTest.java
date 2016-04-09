package cs2103.v15_1j.jolt.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.controller.Configuration;
import cs2103.v15_1j.jolt.controller.JoltController;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;
import cs2103.v15_1j.jolt.parser.JoltParser;
import cs2103.v15_1j.jolt.searcher.JoltSearcher;
import cs2103.v15_1j.jolt.storage.JoltStorage;
import cs2103.v15_1j.jolt.uifeedback.AddFeedback;
import cs2103.v15_1j.jolt.uifeedback.DeleteFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class JoltControllerTest {

    private final String SAVE_FILE_NAME = "save_data_test.json";
    private final String CONFIG_FILE_NAME = "config_test.json";

    private JoltController controller;
    private JoltStorage storage;
    private JoltParser parser;
    private JoltSearcher searcher;

    @Before
    public void setUp() throws Exception {
        this.controller = new JoltController();
        this.storage = new JoltStorage();
        this.parser = new JoltParser();
        this.searcher = new JoltSearcher();

        storage.setConfigFile(CONFIG_FILE_NAME);
        Configuration config = new Configuration();
        config.savePath = SAVE_FILE_NAME;
        storage.saveConfig(config);

        controller.setStorage(storage);
        controller.setParser(parser);
        controller.setSearcher(searcher);
        controller.init();
    }

    @After
    public void tearDown() throws IOException {
        // Delete test JSON files after every test
        storage.getSaveFile().delete();
        storage.getConfigFile().delete();
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
        FloatingTask floatingTask = controller.getMasterList().getFloatingTasksList().get(0);
        assertEquals("Prepare for German exams", floatingTask.getName());
        assertFalse(floatingTask.getCompleted());
        // it returns the right feedback
        assertTrue(feedback instanceof AddFeedback);
        AddFeedback addFeedback = (AddFeedback) feedback;
        assertEquals(floatingTask, addFeedback.getTaskEvent());
        // it is saved to disk
        DataLists listReadFromDisk = storage.load();
        assertEquals(1, listReadFromDisk.getFloatingTasksList().size());
        floatingTask = listReadFromDisk.getFloatingTasksList().get(0);
        assertEquals("Prepare for German exams", floatingTask.getName());
        assertFalse(floatingTask.getCompleted());

        feedback = controller.execute("Prepare for German exams by April 20th 2016");
        // it is added properly
        assertEquals(1, controller.getMasterList().getDeadlineTasksList().size());
        DeadlineTask deadlineTask = controller.getMasterList().getDeadlineTasksList().get(0);
        assertEquals("Prepare for German exams", floatingTask.getName());
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 4, 20), LocalTime.MAX), deadlineTask.getDateTime());
        assertFalse(deadlineTask.getCompleted());
        // it returns the right feedback
        assertTrue(feedback instanceof AddFeedback);
        addFeedback = (AddFeedback) feedback;
        assertEquals(deadlineTask, addFeedback.getTaskEvent());
        // it is saved to disk
        listReadFromDisk = storage.load();
        assertEquals(1, listReadFromDisk.getDeadlineTasksList().size());
        deadlineTask = listReadFromDisk.getDeadlineTasksList().get(0);
        assertEquals("Prepare for German exams", floatingTask.getName());
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 4, 20), LocalTime.MAX), deadlineTask.getDateTime());
        assertFalse(deadlineTask.getCompleted());

        feedback = controller.execute("German exams on 21/4/2016 10am to 11.30am");
        // it is added properly
        assertEquals(1, controller.getMasterList().getEventsList().size());
        Event event = controller.getMasterList().getEventsList().get(0);
        assertEquals("German exams", event.getName());
        assertEquals(LocalDateTime.of(2016, 4, 21, 10, 00), event.getStartDateTime());
        assertEquals(LocalDateTime.of(2016, 4, 21, 11, 30), event.getEndDateTime());
        // it returns the right feedback
        assertTrue(feedback instanceof AddFeedback);
        addFeedback = (AddFeedback) feedback;
        assertEquals(event, addFeedback.getTaskEvent());
        // it is saved to disk
        listReadFromDisk = storage.load();
        assertEquals(1, listReadFromDisk.getEventsList().size());
        event = listReadFromDisk.getEventsList().get(0);
        assertEquals("German exams", event.getName());
        assertEquals(LocalDateTime.of(2016, 4, 21, 10, 00), event.getStartDateTime());
        assertEquals(LocalDateTime.of(2016, 4, 21, 11, 30), event.getEndDateTime());
    }

    @Test
    public void testDelete() {
        controller.execute("Prepare for German exams");
        controller.execute("Buy flowers for her by Tuesday");
        
        for(DeadlineTask t: controller.getMasterList().getDeadlineTasksList()){
        	controller.states.displayList.add(t);
        }
        
        for(FloatingTask t: controller.getMasterList().getFloatingTasksList()){
        	controller.states.displayList.add(t);
        }
        
        UIFeedback feedback = controller.execute("DELETE F1");
        // it returns the right feedback
        assertTrue(feedback instanceof DeleteFeedback);
        DeleteFeedback deleteFeedback = (DeleteFeedback) feedback;
        assertEquals("Prepare for German exams", deleteFeedback.getTaskEvent().getName());
        // the item is deleted
        assertTrue(controller.getMasterList().getFloatingTasksList().isEmpty());
        // other items are not affected
        assertFalse(controller.getMasterList().getDeadlineTasksList().isEmpty());
    }
    
    /* @@author A0124995R */
    @Test
    public void testAliasAdd() {
    	controller.execute("alias add delete del");
    	controller.execute("Prepare for German exams");
    	List<FloatingTask> masterFloatingTasksList = controller.getMasterList().getFloatingTasksList();
    	assertEquals(1, masterFloatingTasksList.size());
    	controller.states.displayList.add(masterFloatingTasksList.get(0));
    	controller.execute("del F1");
    	assertEquals(0, controller.getMasterList().getFloatingTasksList().size());
    }
    
    @Test
    public void testAliasDelete() {
    	// Test that alias was added and it works
    	controller.execute("alias add delete del");
    	controller.execute("Prepare for German exams");
    	List<FloatingTask> masterFloatingTasksList = controller.getMasterList().getFloatingTasksList();
    	assertEquals(1, masterFloatingTasksList.size());
    	controller.states.displayList.add(masterFloatingTasksList.get(0));
    	controller.execute("del f1");
    	assertEquals(0, controller.getMasterList().getFloatingTasksList().size());
    	assertEquals(1, controller.states.config.aliases.size());
    	// Test that alias was deleted and no longer works
    	controller.execute("alias delete del");
    	assertEquals(0, controller.states.config.aliases.size());
    	controller.execute("Prepare for German exams");
    	assertEquals(1, masterFloatingTasksList.size());
    	controller.states.displayList.add(masterFloatingTasksList.get(0));
    	controller.execute("del F1");
    	assertEquals(2, controller.getMasterList().getFloatingTasksList().size());
    }
}
