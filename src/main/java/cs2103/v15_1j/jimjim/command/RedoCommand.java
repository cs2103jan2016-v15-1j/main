package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class RedoCommand implements Command {
	/* @@author A0124995R */
	@Override
	public UIFeedback execute(ControllerStates conStates) {
		if (conStates.redoCommandHistory.empty()) {
			return new FailureFeedback("Nothing to redo!");
		}
		UndoableCommand topCommand = conStates.redoCommandHistory.pop();
		UIFeedback feedback = topCommand.execute(conStates);
		return feedback;
	}

}
