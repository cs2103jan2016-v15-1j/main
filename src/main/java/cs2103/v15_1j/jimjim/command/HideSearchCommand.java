package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.HideSearchFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class HideSearchCommand implements Command {
	@Override
	public UIFeedback execute(ControllerStates conStates) {
		conStates.searchResultsList.clear();
		return new HideSearchFeedback();
	}

}
