package cs2103.v15_1j.jimjim.model;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Task extends TaskEvent implements Comparable<Task> {

	private ObjectProperty<LocalDateTime> dateTime;
	private BooleanProperty completed;

	public Task(String name){
		setName(name);
		setID(0);
		this.dateTime = null;
		this.completed = new SimpleBooleanProperty(false);
	}

	public Task(String name, LocalDateTime dateTime) {
		setName(name);
		setID(0);
		this.dateTime = new SimpleObjectProperty<LocalDateTime>(dateTime);
		this.completed = new SimpleBooleanProperty(false);
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
		LocalDateTime temp = LocalDateTime.of(date, dateTime.get().toLocalTime());
		this.dateTime = new SimpleObjectProperty<LocalDateTime>(temp);
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return new SimpleObjectProperty<LocalDate>(dateTime.get().toLocalDate());	
	}

	public boolean getCompleted(){
		return completed.get();
	}

	public void setCompleted(boolean completed){
		this.completed = new SimpleBooleanProperty(completed);;
	}

	public BooleanProperty completedProperty(){
		return completed;
	}

	@Override
	public int compareTo(Task o) {
		if(((Task) o).getDateTime() != null){
			return dateTime.get().compareTo(((Task) o).getDateTime());
		}
		else {
			return 0;
		}

	}
}
