package cs2103.v15_1j.jolt.command;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.uifeedback.FailureFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class InvalidCommand implements Command {

	private String message;

	public InvalidCommand(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public UIFeedback execute(ControllerStates conStates) {
		return new FailureFeedback(this.message);
	}

}
