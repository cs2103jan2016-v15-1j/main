package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class ClearCommand implements Command {

    @Override
    public String undo(DataLists searchResultsList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String execute(DataLists searchResultsList, DataLists masterList, Storage storage, Searcher searcher) {
        searchResultsList.clear();
        return null;
    }

}
