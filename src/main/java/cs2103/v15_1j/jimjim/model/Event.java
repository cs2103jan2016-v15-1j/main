package cs2103.v15_1j.jimjim.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Event extends TaskEvent {
	private ObjectProperty<List<EventTime>> dateTimes;
	
	public Event(String name, LocalDateTime start, LocalDateTime end) {
	    setName(name);
	    this.dateTimes = new SimpleObjectProperty<List<EventTime>>(new ArrayList<EventTime>());
	    this.dateTimes.get().add(new EventTime(start, end));
    }
	
	public List<EventTime> getDateTimes() {
	    return this.dateTimes.get();
	}
	
	public void addDateTime(LocalDateTime start, LocalDateTime end){
		dateTimes.get().add(new EventTime(start, end));
	}
	
	public ObjectProperty<List<EventTime>> dateTimesProperty(){
		return dateTimes;
	}
}
