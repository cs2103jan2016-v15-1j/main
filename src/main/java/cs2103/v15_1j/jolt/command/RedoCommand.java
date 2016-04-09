package cs2103.v15_1j.jolt.command;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.uifeedback.FailureFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

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
