package cs2103.v15_1j.jimjim.controller;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.parser.Parser;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public interface Controller {
	public UIFeedback execute(String userCommand);
	public DataLists getDisplayList();
	public void setStorage(Storage storage);
	public void setParser(Parser parser);
	public void setSearcher(Searcher searcher);
}
