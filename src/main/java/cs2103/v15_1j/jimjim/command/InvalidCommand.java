package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
public class InvalidCommand implements Command {
	
	private String message;
	
	public InvalidCommand(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

	@Override
	public String undo(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
		return null;
	}

	@Override
	public String execute(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
		return getMessage();
	}

}
