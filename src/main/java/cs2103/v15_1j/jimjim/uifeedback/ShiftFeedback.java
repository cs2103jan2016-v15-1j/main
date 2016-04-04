package cs2103.v15_1j.jimjim.uifeedback;

import java.time.LocalDateTime;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class ShiftFeedback implements UIFeedback {
	private String name;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	
	public ShiftFeedback(String name, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.name = name;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	@Override
	public void execute(MainViewController con) {
		con.showNotification("\"" + name + "\" is now from: " + startDateTime.toString() + " to " + endDateTime.toString());
	}

	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof ShiftFeedback)) {
			return false;
		}
		ShiftFeedback other = (ShiftFeedback) t;
		return this.name.equals(other.name) && 
			   this.startDateTime.equals(other.startDateTime) &&
			   this.endDateTime.equals(other.endDateTime);
	}
}
