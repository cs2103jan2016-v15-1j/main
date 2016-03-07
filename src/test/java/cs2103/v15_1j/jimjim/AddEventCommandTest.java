package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import cs2103.v15_1j.jimjim.model.TaskEvent;

public class AddEventCommandTest {

    ArrayList<TaskEvent> displayList;
    ArrayList<TaskEvent> list;
    StubStorage storage;

    @Before
    public void setUp() throws Exception {
        this.displayList = new ArrayList<TaskEvent>();
        this.list = new ArrayList<TaskEvent>();
        this.storage = new StubStorage();
    }
    
    @Test
    public void testExecute() {
    	LocalDateTime startDateTime = LocalDateTime.of(2016, 4, 30, 12, 00);
    	LocalDateTime endDateTime = LocalDateTime.of(2016, 4, 30, 16,00);
        AddEventCommand command =
                new AddEventCommand("Meeting with boss", startDateTime, endDateTime);
        String result = command.execute(displayList, list, storage, null);
        
        assertEquals("Event added", result);
        assertEquals(1, displayList.size());
        
        assertEquals("Meeting with boss", displayList.get(0).getName());
        assertTrue(displayList.get(0) instanceof Event);
        Event event = (Event) displayList.get(0);
        List<EventTime> eventTimeList = event.getDateTime();
        
        assertEquals(startDateTime, eventTimeList.get(0).getStartDateTime());
        assertEquals(endDateTime, eventTimeList.get(0).getEndDateTime());
    }
}
