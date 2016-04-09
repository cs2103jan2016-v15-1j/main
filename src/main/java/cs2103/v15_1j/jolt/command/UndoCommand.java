package cs2103.v15_1j.jolt.command;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.uifeedback.FailureFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

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
