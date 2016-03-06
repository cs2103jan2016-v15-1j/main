package cs2103.v15_1j.jimjim.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event extends TaskEvent {
	public List<EventTime> dateTime;
	
	public Event(String name, LocalDateTime start, LocalDateTime end) {
	    setName(name);
	    this.dateTime = new ArrayList<EventTime>();
	    this.dateTime.add(new EventTime(start, end));
    }
	
	public List<EventTime> getDateTime() {
	    return this.dateTime;
	}
}
