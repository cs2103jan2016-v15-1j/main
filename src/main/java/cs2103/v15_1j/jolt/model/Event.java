package cs2103.v15_1j.jolt.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Event extends TaskEvent implements Comparable<Event> {
	private BooleanProperty isFullDay;
	private ObjectProperty<LocalDateTime> startDateTime;
	private ObjectProperty<LocalDateTime> endDateTime;

	// @@author A0139963N
	public Event(String name, LocalDateTime start, LocalDateTime end) {
		super(name);
		this.setStartDateTime(start);
		this.setEndDateTime(end);
		this.setIsFullDay(false);
	}
	
	public Event(String name, LocalDate date){
		this(name, date.atStartOfDay(), date.atTime(LocalTime.MAX));
		this.setIsFullDay(true);
	}

	/* @@author A0124995R */
	public Event(Event e) {
		this(e.getName(), e.getStartDateTime(), e.getEndDateTime());
        this.setIsFullDay(e.getIsFullDay());
	}

	/* @@author A0139963N */
	public void setIsFullDay(boolean isFullDay) {
		this.isFullDay = new SimpleBooleanProperty(isFullDay);
	}

	public boolean getIsFullDay() {
		return isFullDay.get();
	}

	public BooleanProperty isFullDayProperty() {
		return isFullDay;
	}
	
	public LocalDateTime getStartDateTime() {
		return startDateTime.get();
	}

	public void setStartDate(LocalDate startDate) {
		this.startDateTime = new SimpleObjectProperty<LocalDateTime>(this.startDateTime.get().with(startDate));
	}

	public void setStartTime(LocalTime startTime) {
		this.startDateTime = new SimpleObjectProperty<LocalDateTime>(this.startDateTime.get().with(startTime));
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = new SimpleObjectProperty<LocalDateTime>(startDateTime);
	}

	public ObjectProperty<LocalDateTime> startDateTimeProperty() {
		return startDateTime;
	}

	public ObjectProperty<LocalDate> startDateProperty() {
		return new SimpleObjectProperty<LocalDate>(startDateTime.get().toLocalDate());
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime.get();
	}

	public void setEndDate(LocalDate endDate) {
		this.endDateTime = new SimpleObjectProperty<LocalDateTime>(this.startDateTime.get().with(endDate));
	}

	public void setEndTime(LocalTime endTime) {
		this.endDateTime = new SimpleObjectProperty<LocalDateTime>(this.startDateTime.get().with(endTime));
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = new SimpleObjectProperty<LocalDateTime>(endDateTime);
	}

	public ObjectProperty<LocalDateTime> endDateTimeProperty() {
		return startDateTime;
	}

	public ObjectProperty<LocalDate> endDateProperty() {
		return new SimpleObjectProperty<LocalDate>(endDateTime.get().toLocalDate());
	}

	public String toDateTimeString() {
		DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd MMM h:mm a");
		DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mm a");

		if (startDateTime.get().toLocalDate().equals(endDateTime.get().toLocalDate())) {
			return startDateTime.get().format(dateFmt) + " - " + endDateTime.get().format(timeFmt);
		}

		return startDateTime.get().format(dateFmt) + " - " + endDateTime.get().format(dateFmt);
	}

	public String toTimeString() {
		DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("h:mm a");

		return startDateTime.get().format(dateFmt) + " - " + endDateTime.get().format(dateFmt);
	}

	@Override
	public int compareTo(Event o) {

		if (startDateTime.get().compareTo(o.startDateTime.get()) == 0) {
			if(isFullDay.get() && !o.getIsFullDay()){
				return -1;
			} else if(!isFullDay.get() && o.getIsFullDay()){
				return 1;
			}
			
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
		return this.getName().equals(other.getName()) && this.getStartDateTime().equals(other.getStartDateTime())
				&& this.getEndDateTime().equals(other.getEndDateTime());
	}
}
