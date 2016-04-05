package cs2103.v15_1j.jimjim.searcher;

/* @@author A0124995R */

import java.util.List;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;

public class JJSearcher implements Searcher {
	@Override
	public DataLists search(List<Filter> filters, DataLists masterLists) {
		DataLists resultsList = new DataLists(); // Result to be returned
		
		List<DeadlineTask> masterDeadlineTasksList = masterLists.getDeadlineTasksList();
		List<FloatingTask> masterFloatingTasksList = masterLists.getFloatingTasksList();
		List<Event> masterEventsList = masterLists.getEventsList();
		
		List<DeadlineTask> searchResultsDeadlineTasksList = resultsList.getDeadlineTasksList();
		getDeadlineTasksSearchResults(filters, masterDeadlineTasksList, searchResultsDeadlineTasksList);
		
		List<FloatingTask> searchResultsFloatingTasksList = resultsList.getFloatingTasksList();
		getFloatingTasksSearchResult(filters, masterFloatingTasksList, searchResultsFloatingTasksList);
		
		List<Event> searchResultsEventsList = resultsList.getEventsList();
		getEventsSearchResult(filters, masterEventsList, searchResultsEventsList);
		
		return resultsList;
	}

	private void getEventsSearchResult(List<Filter> filters, 
									   List<Event> masterEventsList,
									   List<Event> resultsEventsList) {
		for (Event event : masterEventsList) {
			if (checkFilters(event, filters)) {
				resultsEventsList.add(event);
			}
		}
	}

	private void getFloatingTasksSearchResult(List<Filter> filters,
											  List<FloatingTask> masterFloatingTasksList, 
											  List<FloatingTask> resultsFloatingTasksList) {
		for (FloatingTask floatingTask : masterFloatingTasksList) {
			if (checkFilters(floatingTask, filters)) {
				resultsFloatingTasksList.add(floatingTask);
			}
		}
	}

	private void getDeadlineTasksSearchResults(List<Filter> filters, 
									   List<DeadlineTask> masterDeadlineTasksList,
									   List<DeadlineTask> resultsDeadlineTasksList) {
		for (DeadlineTask task : masterDeadlineTasksList) {
			if (checkFilters(task, filters)) {
				resultsDeadlineTasksList.add(task);
			}
		}
	}
	
	private boolean checkFilters(DeadlineTask deadlineTask, List<Filter> filters) {
		for (Filter filter : filters) {
			if (!filter.check(deadlineTask)) return false;
		}
		return true;
	}
	
	private boolean checkFilters(FloatingTask floatingTask, List<Filter> filters) {
		for (Filter filter : filters) {
			if (!filter.check(floatingTask)) return false;
		}
		return true;
	}
	
	private boolean checkFilters(Event event, List<Filter> filters) {
		for (Filter filter : filters) {
			if (!filter.check(event)) return false;
		}
		return true;
	}
}
