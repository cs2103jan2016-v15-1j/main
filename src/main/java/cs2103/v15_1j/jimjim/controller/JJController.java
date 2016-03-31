package cs2103.v15_1j.jimjim.controller;

import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.parser.Parser;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class JJController implements Controller {

    ControllerStates states = new ControllerStates();

	@Override
	public UIFeedback execute(String userCommand) {
		assert userCommand != null;
		Command command = states.parser.parse(userCommand);
		assert command != null;
		return command.execute(states);
	}

	@Override
	public DataLists getSearchResultsList() {
		return states.searchResultsList;
	}
	
	@Override
	public DataLists getMasterList() {
        return states.masterList;
    }

	@Override
	public DataLists getDisplayList() {
		return states.displayList;
	}

	@Override
	public void setStorage(Storage storage) {
	    // also doing initialization here
		this.states.storage = storage;
		this.states.masterList = storage.load();
		this.states.displayList = new DataLists();
		this.states.searchResultsList = new DataLists();
	}

	@Override
	public void setParser(Parser parser) {
		this.states.parser = parser;
	}

	@Override
	public void setSearcher(Searcher searcher) {
		this.states.searcher = searcher;
	}
}
