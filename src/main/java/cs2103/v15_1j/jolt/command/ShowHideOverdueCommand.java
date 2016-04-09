package cs2103.v15_1j.jolt.command;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.uifeedback.ShowHideOverdueFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class ShowHideOverdueCommand implements Command {

	private boolean willShow;

	public ShowHideOverdueCommand(boolean willShow) {
		this.willShow = willShow;
	}

	public boolean getWillShow() {
		return this.willShow;
	}

	/* @@author A0124995R */
	@Override
	public UIFeedback execute(ControllerStates conStates) {
		return new ShowHideOverdueFeedback(willShow);
	}

}
