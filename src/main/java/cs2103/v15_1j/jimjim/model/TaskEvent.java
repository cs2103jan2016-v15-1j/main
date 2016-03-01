package cs2103.v15_1j.jimjim.model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class TaskEvent {
	private String name;
	private int id;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public StringProperty taskNameProperty() {
        return new SimpleStringProperty(name);
    }
	
	public int getID(){
		return id;
	}
}
