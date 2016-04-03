package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class ShiftFeedback implements UIFeedback {
	String message = "";
	
	public ShiftFeedback(String message) {
		this.message = message;
	}
	@Override
	public void execute(MainViewController con) {
		con.showNotification(message);
	}

	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof ShiftFeedback)) {
			return false;
		}
		ShiftFeedback other = (ShiftFeedback) t;
		return this.message.equals(other.message);
	}
}
