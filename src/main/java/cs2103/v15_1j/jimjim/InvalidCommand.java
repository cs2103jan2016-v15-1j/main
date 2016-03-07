package cs2103.v15_1j.jimjim;

import java.util.List;

public class InvalidCommand implements Command {
	
	private String message;
	
	public InvalidCommand(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

	@Override
	public String undo(List<TaskEvent> displayList, List<TaskEvent> list, Storage storage, Searcher searcher) {
		return null;
	}

	@Override
	public String execute(List<TaskEvent> displayList, List<TaskEvent> list, Storage storage, Searcher searcher) {
		return getMessage();
	}

}
