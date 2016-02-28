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
	public void undo(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
		// TODO Auto-generated method stub

	}

}
