package cs2103.v15_1j.jimjim.controller;

import java.util.Stack;

import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.parser.Parser;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class JJController implements Controller {

	private DataLists displayList;
	private DataLists masterList;
	private Stack<Command> undoCommandHistory = new Stack<Command>();
	private Parser parser;
	private Searcher searcher;
	private Storage storage;

	@Override
	public String execute(String userCommand) {
		assert userCommand != null;
		Command command = parser.parse(userCommand);
		assert command != null;
		return command.execute(displayList, masterList, storage, searcher, undoCommandHistory);
	}

	@Override
	public DataLists getDisplayList() {
		return displayList;
	}

	@Override
	public void setStorage(Storage storage) {
		this.storage = storage;
		this.masterList = storage.load();
		this.displayList = new DataLists(masterList);
	}

	@Override
	public void setParser(Parser parser) {
		this.parser = parser;
	}

	@Override
	public void setSearcher(Searcher searcher) {
		this.searcher = searcher;
	}
}
