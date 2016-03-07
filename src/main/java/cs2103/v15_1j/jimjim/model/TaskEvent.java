package cs2103.v15_1j.jimjim.model;
import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class TaskEvent {
	private StringProperty name;
	private IntegerProperty id;

	public String getName() {
		return name.get();
	}

	public void setName(String name){
		this.name = new SimpleStringProperty(name);
	}

	public StringProperty taskNameProperty() {
		return name;
	}

	public void setID(int id){
		this.id = new SimpleIntegerProperty(id);
	}

	public int getID(){
		return id.get();
	}

	public ObjectProperty<LocalDateTime> dateTimeProperty(){
		return null;
	}

}
