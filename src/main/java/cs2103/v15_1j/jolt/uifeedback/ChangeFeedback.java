package cs2103.v15_1j.jolt.uifeedback;

import cs2103.v15_1j.jolt.ui.MainViewController;

public class ChangeFeedback implements UIFeedback {
	String message = "";

	public ChangeFeedback(String message) {
		this.message = message;
	}

	@Override
	public void execute(MainViewController con) {
		con.showNotification(message);
	}

	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof ChangeFeedback)) {
			return false;
		}
		ChangeFeedback other = (ChangeFeedback) t;
		return this.message.equals(other.message);
	}
}
