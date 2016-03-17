package cs2103.v15_1j.jimjim.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class FloatingTask extends TaskEvent {
	private BooleanProperty completed;
	
	public FloatingTask(String name) {
		setName(name);
		setID(0);
		
		this.completed = new SimpleBooleanProperty(false);
	}
	
	public BooleanProperty completedProperty() {
		return this.completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = new SimpleBooleanProperty(completed);
	}
}
