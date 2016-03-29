package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public interface UndoableCommand extends Command {
	public UIFeedback undo(ControllerStates conStates);

}
