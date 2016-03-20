package cs2103.v15_1j.jimjim.model;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DeadlineTask extends Task implements Comparable<Task> {

	private ObjectProperty<LocalDateTime> dateTime;

	public DeadlineTask(String name, LocalDateTime dateTime) {
	    super(name);
		this.dateTime = new SimpleObjectProperty<LocalDateTime>(dateTime);
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
	public void setDate(LocalDate date){
		if(dateTime.get() == null){
			LocalDateTime temp = LocalDateTime.of(date, dateTime.get().toLocalTime());
			this.dateTime = new SimpleObjectProperty<LocalDateTime>(temp);
		}
		
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return new SimpleObjectProperty<LocalDate>(dateTime.get().toLocalDate());	
	}

	public int compareTo(Task o) {
		LocalDateTime taskDateTime = dateTime.get();
		LocalDateTime otherDateTime = ((DeadlineTask) o).getDateTime();
		
		if(taskDateTime.compareTo(otherDateTime) == 0){
			return super.compareTo(o);
		} else {
			return taskDateTime.compareTo(otherDateTime);
		}

	}
}
