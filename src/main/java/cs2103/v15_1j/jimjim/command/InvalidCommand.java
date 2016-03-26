package cs2103.v15_1j.jimjim.command;

import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
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
	public String execute(DataLists displayList, DataLists masterList, 
						  Storage storage, Searcher searcher, Stack<Command> undoCommandHistory) {
		return getMessage();
	}

}
