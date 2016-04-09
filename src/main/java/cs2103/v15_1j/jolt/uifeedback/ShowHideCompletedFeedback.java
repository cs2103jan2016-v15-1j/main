package cs2103.v15_1j.jolt.uifeedback;

import cs2103.v15_1j.jolt.ui.MainViewController;

public class ShowHideCompletedFeedback implements UIFeedback {
	private boolean showCompleted;

	public ShowHideCompletedFeedback(boolean showCompleted) {
		this.showCompleted = showCompleted;
	}

	public boolean getShowCompleted() {
		return showCompleted;
	}

	@Override
	public void execute(MainViewController con) {
		con.setShowCompleted(showCompleted);

		if (showCompleted) {
			con.showNotification("Showing Completed Task and Events.");
		} else {
			con.showNotification("Hiding Completed Task and Events.");
		}

	}

}
