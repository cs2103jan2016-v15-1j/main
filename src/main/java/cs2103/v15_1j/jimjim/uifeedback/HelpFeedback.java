package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class HelpFeedback implements UIFeedback {

	@Override
	public void execute(MainViewController con) {
		con.showHelp();
	}

}
