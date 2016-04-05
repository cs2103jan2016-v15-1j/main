package cs2103.v15_1j.jimjim.command;

import java.util.List;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Filter;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.SearchFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

/* @@author A0124995R */
public class SearchCommand implements Command {
    
    private List<Filter> filters;

    public SearchCommand(List<Filter> filters) {
        this.filters = filters;
    }
    
    public List<Filter> getFilters() {
        return filters;
    }
    
	@Override
	public UIFeedback execute(ControllerStates conStates) {
		try {
    		DataLists searchResults = conStates.searcher.search(filters, conStates.masterList);
        	conStates.searchResultsList.copy(searchResults);
        	return new SearchFeedback(filters);
    	} catch (Exception e) {
    		return new FailureFeedback(
    		        "Some error has occurred. Please try again.");
    	}
	}
}
