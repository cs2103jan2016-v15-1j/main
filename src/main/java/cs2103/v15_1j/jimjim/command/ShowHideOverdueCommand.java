package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.ShowHideOverdueFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

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
