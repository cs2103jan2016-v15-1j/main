package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class HelpFeedback implements UIFeedback {

	String message = "";
	
	public HelpFeedback(String message){
		this.message = message;
	}
	
	@Override
	public void execute(MainViewController con) {
		con.showHelp();
	}
	
	public boolean equals(FailureFeedback other) {
		return this.message.equals(other.message);
	}
}
