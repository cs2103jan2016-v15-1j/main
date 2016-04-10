package cs2103.v15_1j.jolt.uifeedback;

import cs2103.v15_1j.jolt.ui.MainViewController;

public class HelpFeedback implements UIFeedback {

	// @@author A0139963N
	private String page;

	public HelpFeedback(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}

	@Override
	public void execute(MainViewController con) {
		con.showHelp(page);
	}

	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof HelpFeedback)) {
			return false;
		}
		HelpFeedback other = (HelpFeedback) t;
		return this.page.equals(other.getPage());
	}
}
