package cs2103.v15_1j.jimjim.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {

	private final StringProperty taskName;
	private final BooleanProperty completed;
	
	public Task(){
		this(null);
	}
	
	public Task(String taskName){
		this.taskName = new SimpleStringProperty(taskName);
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
	
	
}
