package cs2103.v15_1j.jimjim.command;

import java.util.List;
import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Filter;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.SearchFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class SearchCommand implements Command {
    
    private List<Filter> filters;

    public SearchCommand(List<Filter> filters) {
        this.filters = filters;
    }
    
    public List<Filter> getFilters() {
        return filters;
    }
    
	@Override
	public UIFeedback execute(DataLists searchResultsList, DataLists masterList, Storage storage, Searcher searcher,
			Stack<Command> undoCommandHistory) {
		try {
    		DataLists searchResults = searcher.search(filters, masterList);
        	searchResultsList.copy(searchResults);
        	return new SearchFeedback(filters);
    	} catch (Exception e) {
    		return new FailureFeedback(
    		        "Some error has occurred. Please try again.");
    	}
	}
}
