package cs2103.v15_1j.jimjim.command;

import java.util.List;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Filter;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class SearchCommand implements Command {
    
    private List<Filter> filters;

    public SearchCommand(List<Filter> filters) {
        this.filters = filters;
    }
    
    public List<Filter> getFilters() {
        return filters;
    }

    @Override
    public String undo(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String execute(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

}
