package cs2103.v15_1j.jimjim.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class TaskEvent {
	private StringProperty name;
	
	public TaskEvent(String name) {
	    setName(name);
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
	
	@Override
	public boolean equals(Object t) {
		TaskEvent other = (TaskEvent) t;
		return this.name.equals(other.name);
	}
}
