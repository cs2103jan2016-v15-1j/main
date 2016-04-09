package cs2103.v15_1j.jolt.command;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.uifeedback.HideSearchFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class HideSearchCommand implements Command {
	@Override
	public UIFeedback execute(ControllerStates conStates) {
		conStates.searchResultsList.clear();
		return new HideSearchFeedback();
	}

}
