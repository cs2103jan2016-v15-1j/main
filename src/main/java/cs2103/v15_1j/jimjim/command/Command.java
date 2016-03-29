package cs2103.v15_1j.jimjim.command;
import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public interface Command {
	public UIFeedback execute(ControllerStates conStates);
}
