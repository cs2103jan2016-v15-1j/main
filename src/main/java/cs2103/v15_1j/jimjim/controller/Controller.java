package cs2103.v15_1j.jimjim.controller;
import java.util.List;

import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.parser.Parser;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public interface Controller {
	public String execute(String userCommand);
	public List<TaskEvent> getDisplayList();
	public void setStorage(Storage storage);
	public void setParser(Parser parser);
	public void setSearcher(Searcher searcher);
}
