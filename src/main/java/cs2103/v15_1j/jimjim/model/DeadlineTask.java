package cs2103.v15_1j.jimjim.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DeadlineTask extends TaskEvent implements Comparable<DeadlineTask> {

	private ObjectProperty<LocalDateTime> dateTime;

	public DeadlineTask(String name, LocalDateTime dateTime) {
		super(name);
		this.dateTime = new SimpleObjectProperty<LocalDateTime>(dateTime);
	}

	public DeadlineTask(DeadlineTask other) {
		this(other.getName(), other.getDateTime());
	}
	public LocalDateTime getDateTime() {
		return this.dateTime.get();
	}

	public void setDateTime(LocalDateTime dateTime){
		this.dateTime = new SimpleObjectProperty<LocalDateTime>(dateTime);
	}

	public ObjectProperty<LocalDateTime> dateTimeProperty() {
		return dateTime;	
	}

	public void setDate(LocalDate date) {
		if (dateTime.get() != null) {
			LocalDateTime temp = LocalDateTime.of(date, dateTime.get().toLocalTime());
			this.dateTime = new SimpleObjectProperty<LocalDateTime>(temp);
		}
	}

	public void setTime(LocalTime time) {
		if (dateTime.get() != null) {
			LocalDateTime temp = LocalDateTime.of(dateTime.get().toLocalDate(), time);
			this.dateTime = new SimpleObjectProperty<LocalDateTime>(temp);
		}
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return new SimpleObjectProperty<LocalDate>(dateTime.get().toLocalDate());	
	}

	//@@author A0139963N
	public int compareTo(DeadlineTask o) {
		LocalDateTime taskDateTime = dateTime.get();
		LocalDateTime otherDateTime = o.getDateTime();

		if(taskDateTime.compareTo(otherDateTime) == 0) {
			String name = this.getName().toLowerCase();
			String otherName = o.getName().toLowerCase();

			return name.compareTo(otherName);
		} else {
			return taskDateTime.compareTo(otherDateTime);
		}
	}

	//@@author A0139963N
	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof DeadlineTask)) {
			return false;
		}
		DeadlineTask other = (DeadlineTask) t;
		return this.getName().equals(other.getName()) 
				&& this.getDateTime().equals(other.getDateTime());
	}
}
