package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;
public class InvalidCommand implements Command {
	
	private String message;
	
	public InvalidCommand(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

	@Override
	public UIFeedback undo(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
		return null;
	}

	@Override
	public UIFeedback execute(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
		return new FailureFeedback(this.message);
	}

}
