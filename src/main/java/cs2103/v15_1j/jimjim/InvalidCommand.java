package cs2103.v15_1j.jimjim;

import java.util.List;

import cs2103.v15_1j.jimjim.model.TaskEvent;

public class InvalidCommand implements Command {
	
	private String message;
	
	public InvalidCommand(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

	@Override
	public String undo(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
		return null;
	}

	@Override
	public String execute(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
		return getMessage();
	}

}
