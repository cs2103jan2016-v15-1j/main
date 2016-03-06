package cs2103.v15_1j.jimjim.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task extends TaskEvent {

	private LocalDateTime dateTime;
	private BooleanProperty completed;
	
	public Task(String name){
		setName(name);
		setID(0);
		this.dateTime = null;
		this.completed = new SimpleBooleanProperty(false);
	}
	
	public Task(String name, LocalDateTime datetime) {
		setName(name);
		setID(0);
		this.dateTime = datetime;
		this.completed = new SimpleBooleanProperty(false);
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
		return completed.get();
	}
	
	public void setCompleted(boolean completed){
		this.completed = new SimpleBooleanProperty(completed);;
	}
	
	public BooleanProperty completedProperty(){
		return completed;
	}
}
