package cs2103.v15_1j.jimjim.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event extends TaskEvent {
	private List<EventTime> dateTimes;
	
	public Event(String name, LocalDateTime start, LocalDateTime end) {
	    setName(name);
	    this.dateTimes = new ArrayList<EventTime>();
	    this.dateTimes.add(new EventTime(start, end));
    }
	
	public List<EventTime> getDateTime() {
	    return this.dateTimes;
	}
	
	public void addDateTime(LocalDateTime start, LocalDateTime end){
		dateTimes.add(new EventTime(start, end));
	}
}
