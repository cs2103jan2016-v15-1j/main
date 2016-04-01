package cs2103.v15_1j.jimjim.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Event extends TaskEvent implements Comparable<Event> {
	private ObjectProperty<LocalDateTime> startDateTime;
	private ObjectProperty<LocalDateTime> endDateTime;

	public Event(String name, LocalDateTime start, LocalDateTime end) {
		super(name);
		this.setStartDateTime(start);
		this.setEndDateTime(end);
	}
	
	public Event(Event e){
		this(e.getName(), e.getStartDateTime(), e.getEndDateTime());
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime.get();
	}

	public void setStartDate(LocalDate startDate){
		LocalDateTime temp = LocalDateTime.of(startDate, startDateTime.get().toLocalTime());
		this.startDateTime = new SimpleObjectProperty<LocalDateTime>(temp);
	}
	
	public void setStartTime(LocalTime startTime) {
		LocalDateTime temp = LocalDateTime.of(startDateTime.get().toLocalDate(), startTime);
		this.startDateTime = new SimpleObjectProperty<LocalDateTime>(temp);
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = new SimpleObjectProperty<LocalDateTime>(startDateTime);
	}

	public ObjectProperty<LocalDateTime> startDateTimeProperty(){
		return startDateTime;
	}

	public ObjectProperty<LocalDate> startDateProperty(){
		return new SimpleObjectProperty<LocalDate>(startDateTime.get().toLocalDate());
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime.get();
	}

	public void setEndDate(LocalDate endDate){
		LocalDateTime temp = LocalDateTime.of(endDate, startDateTime.get().toLocalTime());
		this.endDateTime = new SimpleObjectProperty<LocalDateTime>(temp);
	}
	
	public void setEndTime(LocalTime endTime){
		LocalDateTime temp = LocalDateTime.of(startDateTime.get().toLocalDate(), endTime);
		this.endDateTime = new SimpleObjectProperty<LocalDateTime>(temp);
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = new SimpleObjectProperty<LocalDateTime>(endDateTime);
	}

	public ObjectProperty<LocalDateTime> endDateTimeProperty(){
		return startDateTime;
	}

	public ObjectProperty<LocalDate> endDateProperty(){
		return new SimpleObjectProperty<LocalDate>(endDateTime.get().toLocalDate());
	}

	public String toDateTimeString(){
		DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd MMM h:mm a");
		DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mm a");

		if(startDateTime.get().toLocalDate().equals(endDateTime.get().toLocalDate())){
			return startDateTime.get().format(dateFmt) + " - " + endDateTime.get().format(timeFmt);
		}

		return startDateTime.get().format(dateFmt) + " - " + endDateTime.get().format(dateFmt);
	}

	public String toTimeString(){
		DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("h:mm a");

		return startDateTime.get().format(dateFmt) + " - " + endDateTime.get().format(dateFmt);
	}

	@Override
	public int compareTo(Event o) {

		if(startDateTime.get().compareTo(o.startDateTime.get()) == 0){
			String eventName = this.getName().toLowerCase();
			String otherName = o.getName().toLowerCase();

			return eventName.compareTo(otherName);
		} else {
			return startDateTime.get().compareTo(o.startDateTime.get());
		}
	}
	
	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof Event)) {
			return false;
		}
		Event other = (Event) t;
		return this.getName().equals(other.getName()) && this.getStartDateTime().equals(other.getStartDateTime()) &&
			   this.getEndDateTime().equals(other.getEndDateTime());
	}
}
