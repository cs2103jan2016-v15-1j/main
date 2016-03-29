package cs2103.v15_1j.jimjim.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Event extends TaskEvent implements Comparable<Event> {
	private ObjectProperty<List<EventTime>> dateTimes;

	public Event(String name, LocalDateTime start, LocalDateTime end) {
		super(name);
		this.dateTimes = new SimpleObjectProperty<List<EventTime>>(new ArrayList<EventTime>());
		this.dateTimes.get().add(new EventTime(start, end));
	}
	
	public Event(Event other) {
		this(other.getName(), other.getEarliestDateTime(), other.getLatestDateTime());
//		this.dateTimes = other.dateTimes;
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
	
	public LocalDateTime getEarliestDateTime(){
		return dateTimes.get().get(0).getStartDateTime();
	}
	
	public LocalDateTime getLatestDateTime(){
		return dateTimes.get().get(dateTimes.get().size() - 1).getEndDateTime();
	}

	@Override
	public int compareTo(Event o) {
		LocalDateTime firstStartDateTime = dateTimes.get().get(0).getStartDateTime();
		LocalDateTime otherStartDateTime = o.getDateTimes().get(0).getStartDateTime();

		if(firstStartDateTime.compareTo(otherStartDateTime) == 0){
			String eventName = this.getName().toLowerCase();
			String otherName = o.getName().toLowerCase();

			return eventName.compareTo(otherName);
		} else {
			return firstStartDateTime.compareTo(otherStartDateTime);
		}
	}
	
	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof Event)) {
			return false;
		}
		Event other = (Event) t;
		return this.getName().equals(other.getName())
			&& this.getDateTimes().equals(other.getDateTimes());
	}
}
