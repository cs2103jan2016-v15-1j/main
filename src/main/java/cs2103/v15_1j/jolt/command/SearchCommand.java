package cs2103.v15_1j.jolt.command;

import java.util.List;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.searcher.Filter;
import cs2103.v15_1j.jolt.uifeedback.FailureFeedback;
import cs2103.v15_1j.jolt.uifeedback.SearchFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

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
			return new FailureFeedback("Some error has occurred. Please try again.");
		}
	}
}
