package cs2103.v15_1j.jimjim.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Task extends TaskEvent implements Comparable<Task> {
	private BooleanProperty completed;
	
	public Task(String name) {
	    super(name);
		this.completed = new SimpleBooleanProperty(false);
	}
	
	public BooleanProperty completedProperty() {
		return this.completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = new SimpleBooleanProperty(completed);
	}
	
	public boolean getCompleted() {
	    return this.completed.getValue();
	}

	@Override
	public int compareTo(Task o) {
		String name = this.getName().toLowerCase();
		String otherName = o.getName().toLowerCase();
		
		return name.compareTo(otherName);
	}
}
