package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class ShowHideOverdueFeedback implements UIFeedback {
	private boolean showCompleted;
	
	public ShowHideOverdueFeedback(boolean showCompleted) {
		this.showCompleted = showCompleted;
	}
	
	public boolean getShowCompleted() {
		return showCompleted;
	}

	@Override
	public void execute(MainViewController con) {
		con.setShowCompleted(showCompleted);
		
		if(showCompleted){
			con.showNotification("Showing Completed Task and Events.");
		}
		else {
			con.showNotification("Hiding Completed Task and Events.");
		}
		
	}

}
