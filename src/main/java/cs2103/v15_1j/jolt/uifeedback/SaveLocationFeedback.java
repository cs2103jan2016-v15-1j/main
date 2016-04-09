package cs2103.v15_1j.jolt.uifeedback;

import cs2103.v15_1j.jolt.ui.MainViewController;

public class SaveLocationFeedback implements UIFeedback {
	private String savePath;

	public SaveLocationFeedback(String savePath) {
		this.savePath = savePath;
	}

	public String getSavePath() {
		return savePath;
	}

	@Override
	public void execute(MainViewController con) {
		con.showNotification("\"" + savePath + "\" has been set as the save path.");
	}

	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof SaveLocationFeedback)) {
			return false;
		}
		SaveLocationFeedback other = (SaveLocationFeedback) t;
		return this.savePath.equals(other.savePath);
	}
}
