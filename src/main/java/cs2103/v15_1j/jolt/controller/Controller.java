package cs2103.v15_1j.jolt.controller;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.parser.Parser;
import cs2103.v15_1j.jolt.searcher.Searcher;
import cs2103.v15_1j.jolt.storage.Storage;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public interface Controller {
	public UIFeedback execute(String userCommand);
	public DataLists getSearchResultsList();
    public DataLists getMasterList();
    public DataLists getDisplayList();
    public String getFilePath();
	public UIFeedback setFilePath(String filePath);
	public void setStorage(Storage storage);
	public void setParser(Parser parser);
	public void setSearcher(Searcher searcher);
	public void init();
}
