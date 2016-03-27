package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class UndoFeedback implements UIFeedback {

	String message = "";
	
	public UndoFeedback(String message){
		this.message = message;
	}
	
	
	@Override
	public void execute(MainViewController con) {
		con.showNotification(message);
	}

}
