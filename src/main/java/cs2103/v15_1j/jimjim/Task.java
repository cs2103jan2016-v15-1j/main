package cs2103.v15_1j.jimjim;
import java.time.LocalDateTime;

public class Task extends TaskEvent {

	public LocalDateTime dateTime;
	public boolean completed;
	
	public Task(String name, LocalDateTime datetime) {
		this.name = name;
		this.dateTime = datetime;
	}
	
	public LocalDateTime getDateTime() {
		return this.dateTime;
	}
}
