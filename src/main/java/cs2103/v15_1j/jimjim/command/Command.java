package cs2103.v15_1j.jimjim.command;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public interface Command {
	public String undo(DataLists searchResultsList,
					 DataLists masterList, Storage storage, Searcher searcher);
	public String execute(DataLists searchResultsList,
						DataLists masterList, Storage storage, Searcher searcher);
}
