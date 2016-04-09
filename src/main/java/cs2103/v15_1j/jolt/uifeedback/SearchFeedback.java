package cs2103.v15_1j.jolt.uifeedback;

import java.util.List;

import cs2103.v15_1j.jolt.searcher.Filter;
import cs2103.v15_1j.jolt.ui.MainViewController;

public class SearchFeedback implements UIFeedback {

	private List<Filter> filters;

	public SearchFeedback(List<Filter> filters) {
		this.filters = filters;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	@Override
	public void execute(MainViewController con) {
		con.showSearchResults();
	}

}
