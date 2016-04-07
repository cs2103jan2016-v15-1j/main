package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class UndoCommand implements Command {
	/* @@author A0124995R */
	@Override
	public UIFeedback execute(ControllerStates conStates) {
		if (conStates.undoCommandHistory.empty()) {
			return new FailureFeedback("Nothing to undo!");
		}
		UndoableCommand topCommand = conStates.undoCommandHistory.pop();
		UIFeedback feedback = topCommand.undo(conStates);
		return feedback;
	}
}
