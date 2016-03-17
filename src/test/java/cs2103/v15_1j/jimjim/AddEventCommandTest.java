package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.AddEventCommand;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;

public class AddEventCommandTest {

    DataLists displayList;
    DataLists masterList;
    StubStorage storage;

    @Before
    public void setUp() throws Exception {
        this.displayList = new DataLists();
        this.masterList = new DataLists();
        this.storage = new StubStorage();
    }
    
    @Test
    public void testExecute() {
    	LocalDateTime startDateTime = LocalDateTime.of(2016, 4, 30, 12, 00);
    	LocalDateTime endDateTime = LocalDateTime.of(2016, 4, 30, 16,00);
        AddEventCommand command =
                new AddEventCommand("Meeting with boss", startDateTime, endDateTime);
        String result = command.execute(displayList, masterList, storage, null);
        
        assertEquals("Event added", result);
        assertEquals(1, displayList.getEventsList().size());
        
        assertEquals("Meeting with boss", displayList.getEventsList().get(0).getName());
        assertTrue(displayList.getEventsList().get(0) instanceof Event);
        Event event = (Event) displayList.getEventsList().get(0);
        List<EventTime> eventTimeList = event.getDateTimes();
        
        assertEquals(startDateTime, eventTimeList.get(0).getStartDateTime());
        assertEquals(endDateTime, eventTimeList.get(0).getEndDateTime());
    }
}
