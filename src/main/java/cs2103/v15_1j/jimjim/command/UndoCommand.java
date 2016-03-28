package cs2103.v15_1j.jimjim.command;

import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class UndoCommand implements Command {
	@Override
	public UIFeedback execute(DataLists searchResultsList, DataLists masterList, 
						  Storage storage, Searcher searcher, Stack<Command> undoCommandHistory) { 
		if (undoCommandHistory.empty()) {
			return new FailureFeedback("Nothing to undo!");
		}
		Command topCommand = undoCommandHistory.pop();
		assert topCommand instanceof UndoableCommand;
		UndoableCommand latestCommand = (UndoableCommand) topCommand;
		UIFeedback feedback = latestCommand.undo(searchResultsList, masterList, storage, searcher, undoCommandHistory);
		return feedback;
	}
}
