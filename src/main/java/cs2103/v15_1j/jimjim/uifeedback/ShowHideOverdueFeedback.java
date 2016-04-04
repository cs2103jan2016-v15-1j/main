package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class ShowHideOverdueFeedback implements UIFeedback {
	private boolean showOverdue;
	
	public ShowHideOverdueFeedback(boolean showOverdue) {
		this.showOverdue = showOverdue;
	}
	
	public boolean getShowCompleted() {
		return showOverdue;
	}

	@Override
	public void execute(MainViewController con) {
		con.setShowOverdue(showOverdue);
		
		if(showOverdue){
			con.showNotification("Showing Overdue Task and Events.");
		}
		else {
			con.showNotification("Hiding Overdue Task and Events.");
		}
		
	}

}
