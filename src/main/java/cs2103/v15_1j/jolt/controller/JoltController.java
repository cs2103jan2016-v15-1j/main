package cs2103.v15_1j.jolt.controller;

import cs2103.v15_1j.jolt.command.Command;
import cs2103.v15_1j.jolt.command.SaveLocationCommand;
import cs2103.v15_1j.jolt.command.UndoCommand;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.parser.Parser;
import cs2103.v15_1j.jolt.searcher.Searcher;
import cs2103.v15_1j.jolt.storage.Storage;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class JoltController implements Controller {

	ControllerStates states = new ControllerStates();

	@Override
	public UIFeedback execute(String userCommand) {
		assert userCommand != null;
		Command command = states.parser.parse(userCommand);
		assert command != null;
		if (!(command instanceof UndoCommand)) {
			states.resetRedoHistory();
		}
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
	public String getFilePath() {
		return this.states.config.savePath;
	}

	@Override
	public UIFeedback setFilePath(String filePath) {
		return (new SaveLocationCommand(filePath)).execute(this.states);
	}

	@Override
	public void setStorage(Storage storage) {
		this.states.storage = storage;
	}

	@Override
	public void setParser(Parser parser) {
		this.states.parser = parser;
	}

	@Override
	public void setSearcher(Searcher searcher) {
		this.states.searcher = searcher;
	}

	@Override
	public void init() {
		this.states.config = this.states.storage.loadConfig();
		this.states.parser.setAliases(this.states.config.aliases);
		this.states.storage.setSaveFile(this.states.config.savePath);
		this.states.masterList = this.states.storage.load();
		if (this.states.masterList != null) {
			// everything's fine
			this.states.displayList = new DataLists();
			this.states.searchResultsList = new DataLists();
		} else {
			// TODO save file is corrupted / wrongly formatted
			// notify user and exit
		}
	}
}
