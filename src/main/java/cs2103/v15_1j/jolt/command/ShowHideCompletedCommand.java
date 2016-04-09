package cs2103.v15_1j.jolt.command;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.uifeedback.ShowHideCompletedFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class ShowHideCompletedCommand implements Command {

	private boolean willShow;

	public ShowHideCompletedCommand(boolean willShow) {
		this.willShow = willShow;
	}

	public boolean getWillShow() {
		return this.willShow;
	}

	@Override
	public UIFeedback execute(ControllerStates conStates) {
		return new ShowHideCompletedFeedback(willShow);
	}

}
