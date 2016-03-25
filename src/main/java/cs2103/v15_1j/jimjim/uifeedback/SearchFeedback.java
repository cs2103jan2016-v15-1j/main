package cs2103.v15_1j.jimjim.uifeedback;

import java.util.List;

import cs2103.v15_1j.jimjim.searcher.Filter;
import cs2103.v15_1j.jimjim.ui.MainViewController;

public class SearchFeedback implements UIFeedback {

    private List<Filter> filters;
	
	public SearchFeedback(List<Filter> filters){
		this.filters = filters;
	}
	
	public List<Filter> getFilters() {
        return filters;
    }
	
	@Override
	public String execute(MainViewController con) {
		// TODO Auto-generated method stub
		return null;
	}

}
