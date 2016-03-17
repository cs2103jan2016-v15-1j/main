package cs2103.v15_1j.jimjim;

import java.util.ArrayList;
import java.util.List;

import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.Task;

public class JJSearcher implements Searcher {
	@Override
	public DataLists search(List<Filter> filters, DataLists masterLists) {
		DataLists resultsList = new DataLists(); // Result to be returned
		
		List<Task> masterTasksList = masterLists.getTasksList();
		List<FloatingTask> masterFloatingTasksList = masterLists.getFloatingTasksList();
		List<Event> masterEventsList = masterLists.getEventsList();
		
		List<Task> searchResultsTasksList = resultsList.getTasksList();
		getTasksSearchResults(filters, masterTasksList, searchResultsTasksList);
		
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

	private void getTasksSearchResults(List<Filter> filters, 
									   List<Task> masterTasksList,
									   List<Task> resultsTasksList) {
		for (Task task : masterTasksList) {
			if (checkFilters(task, filters)) {
				resultsTasksList.add(task);
			}
		}
	}
	
	private boolean checkFilters(Task task, List<Filter> filters) {
		for (Filter filter : filters) {
			if (!filter.check(task)) return false;
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
