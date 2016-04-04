package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class InvalidFeedback implements UIFeedback {
	private String message;
	
	public InvalidFeedback(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	@Override
	public void execute(MainViewController con) {
		con.showNotification(message);
	}

}
