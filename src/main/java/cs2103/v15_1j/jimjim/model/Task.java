package cs2103.v15_1j.jimjim.model;

import java.time.format.DateTimeFormatter;

import cs2103.v15_1j.jimjim.TaskEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {

	private final StringProperty taskName;
	private final StringProperty dateTime;
	private final BooleanProperty completed;
	
	public Task(){
		this.taskName = null;
		this.completed = null;
		this.dateTime = null;
	}
	
	public Task(TaskEvent te){
		cs2103.v15_1j.jimjim.Task t = (cs2103.v15_1j.jimjim.Task) te;
		this.taskName = new SimpleStringProperty(t.getName());
		DateTimeFormatter dayTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		this.dateTime = new SimpleStringProperty(dayTimeFormatter.format(t.getDateTime()));
		this.completed = new SimpleBooleanProperty(false);
	}
	
	public Task(String taskName){
		this.taskName = new SimpleStringProperty(taskName);
		this.dateTime = null;
		this.completed = new SimpleBooleanProperty(false);
	}
	
	public String getTaskName(){
		return taskName.get();
	}
	
	public void setTaskName(String taskName){
		this.taskName.set(taskName);
	}

    public StringProperty taskNameProperty() {
        return taskName;
    }
	
	public boolean getCompleted(){
		return completed.get();
	}
	
	public void setCompleted(boolean completed){
		this.completed.set(completed);
	}
	
	public BooleanProperty completedProperty(){
		return completed;
	}

	public StringProperty dateTimeProperty() {
		return dateTime;
	}
	
	
}
