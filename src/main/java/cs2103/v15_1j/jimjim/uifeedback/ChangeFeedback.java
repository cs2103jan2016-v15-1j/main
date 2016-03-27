package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class ChangeFeedback implements UIFeedback {

	String message = "";
	
	public ChangeFeedback(String message){
		this.message = message;
	}
	
	@Override
	public void execute(MainViewController con) {
		con.showNotification(message);
	}

}
