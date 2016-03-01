package cs2103.v15_1j.jimjim.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task extends TaskEvent {

	private LocalDateTime dateTime;
	private boolean completed;
	
	public Task(String name){
		setName(name);
		this.dateTime = null;
		this.completed = false;
	}
	
	public Task(String name, LocalDateTime datetime) {
		setName(name);
		this.dateTime = datetime;
		this.completed = false;
	}
	
	public LocalDateTime getDateTime() {
		return this.dateTime;
	}
	
	public void setDateTime(LocalDateTime dateTime){
		this.dateTime = dateTime;
	}
	
	public StringProperty dateTimeProperty() {
		if(dateTime != null){
			DateTimeFormatter dayTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			return new SimpleStringProperty(dayTimeFormatter.format(dateTime));
		}
		else {
			return new SimpleStringProperty("");
		}
		
	}
	
	public boolean getCompleted(){
		return completed;
	}
	
	public void setCompleted(boolean completed){
		this.completed = completed;
	}
	
	public BooleanProperty completedProperty(){
		return new SimpleBooleanProperty(completed);
	}
}
