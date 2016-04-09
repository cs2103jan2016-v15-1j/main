package cs2103.v15_1j.jolt.uifeedback;

import cs2103.v15_1j.jolt.ui.MainViewController;

public class HelpFeedback implements UIFeedback {

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
}
