package cs2103.v15_1j.jimjim.uifeedback;

/* @@author A0124995R */

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class ShowHideOverdueFeedback implements UIFeedback {
	private boolean willShow;
	
	public ShowHideOverdueFeedback(boolean willShow) {
		this.willShow = willShow;
	}
	
	public boolean getWillShow() {
		return willShow;
	}

	@Override
	public void execute(MainViewController con) {
		// TODO
	}

}
