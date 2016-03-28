package cs2103.v15_1j.jimjim.controller;

import java.util.Stack;

import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.command.UndoableCommand;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.parser.Parser;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class JJController implements Controller {

	private DataLists searchResultsList;
	private DataLists masterList;
	private Stack<UndoableCommand> undoCommandHistory = new Stack<UndoableCommand>();
	private Stack<UndoableCommand> redoCommandHistory = new Stack<UndoableCommand>();
	private Parser parser;
	private Searcher searcher;
	private Storage storage;

	@Override
	public UIFeedback execute(String userCommand) {
		assert userCommand != null;
		Command command = parser.parse(userCommand);
		assert command != null;
		return command.execute(searchResultsList, masterList, storage, searcher, undoCommandHistory, redoCommandHistory);
	}

	@Override
	public DataLists getSearchResultsList() {
		return searchResultsList;
	}
	
	@Override
	public DataLists getMasterList() {
        return masterList;
    }

	@Override
	public void setStorage(Storage storage) {
		this.storage = storage;
		this.masterList = storage.load();
		this.searchResultsList = new DataLists(masterList);
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
