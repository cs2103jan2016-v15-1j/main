package cs2103.v15_1j.jimjim.command;

import java.util.List;
import java.util.Stack;

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
    public String execute(DataLists displayList, DataLists masterList, 
    					  Storage storage, Searcher searcher, Stack<Command> undoCommandHistory) {
    	try {
    		DataLists searchResults = searcher.search(filters, masterList);
        	displayList.copy(searchResults);
        	return "Done!";
    	} catch (Exception e) {
    		return "Some error has occurred. Please try again.";
    	}
    	
    }

}
