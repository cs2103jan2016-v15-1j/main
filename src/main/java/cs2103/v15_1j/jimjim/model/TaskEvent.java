package cs2103.v15_1j.jimjim.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class TaskEvent {
	private StringProperty name;
	private BooleanProperty completed;
	
	public TaskEvent(String name) {
	    setName(name);
	    setCompleted(false);
    }

	public String getName() {
		return name.get();
	}

	public void setName(String name){
		this.name = new SimpleStringProperty(name);
	}

	public StringProperty taskNameProperty() {
		return name;
	}

	public BooleanProperty completedProperty() {
		return this.completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = new SimpleBooleanProperty(completed);
	}
	
	public boolean getCompleted() {
	    return this.completed.get();
	}
	
}
