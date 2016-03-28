package cs2103.v15_1j.jimjim.command;

import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.HideSearchFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class HideSearchCommand implements Command {
	@Override
	public UIFeedback execute(DataLists searchResultsList, DataLists masterList, Storage storage, 
							  Searcher searcher, Stack<UndoableCommand> undoCommandHistory,
							  Stack<UndoableCommand> redoCommandHistory) {
		searchResultsList.clear();
        return new HideSearchFeedback();
	}

}
