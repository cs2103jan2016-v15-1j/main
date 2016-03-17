package cs2103.v15_1j.jimjim.model;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DeadlineTask extends FloatingTask implements Comparable<DeadlineTask> {

	private ObjectProperty<LocalDateTime> dateTime;

	public DeadlineTask(String name, LocalDateTime dateTime) {
	    super(name);
		this.dateTime = new SimpleObjectProperty<LocalDateTime>(dateTime);
	}

	public LocalDateTime getDateTime() {
		return this.dateTime.get();
	}

	public void setDateTime(LocalDateTime dateTime){
		this.dateTime = new SimpleObjectProperty<LocalDateTime>(dateTime);
	}

	public ObjectProperty<LocalDateTime> dateTimeProperty() {
		return dateTime;	
	}
	public void setDate(LocalDate date){
		if(dateTime.get() == null){
			LocalDateTime temp = LocalDateTime.of(date, dateTime.get().toLocalTime());
			this.dateTime = new SimpleObjectProperty<LocalDateTime>(temp);
		}
		
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return new SimpleObjectProperty<LocalDate>(dateTime.get().toLocalDate());	
	}

	@Override
	public int compareTo(DeadlineTask o) {
		if(((DeadlineTask) o).getDateTime() != null){
			return dateTime.get().compareTo(((DeadlineTask) o).getDateTime());
		}
		else {
			return 0;
		}

	}
}
