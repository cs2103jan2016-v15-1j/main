package cs2103.v15_1j.jimjim.model;

import java.time.LocalDateTime;

public class EventTime {

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	public EventTime(LocalDateTime start, LocalDateTime end) {
	    this.setStartDateTime(start);
	    this.setEndDateTime(end);
    }

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}
}
