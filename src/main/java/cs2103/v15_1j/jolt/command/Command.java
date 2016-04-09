package cs2103.v15_1j.jolt.command;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public interface Command {
	public UIFeedback execute(ControllerStates conStates);
}
