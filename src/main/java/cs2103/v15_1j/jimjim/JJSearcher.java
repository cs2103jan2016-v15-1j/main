package cs2103.v15_1j.jimjim;

import java.util.ArrayList;

import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.Task;

public class JJSearcher implements Searcher {
	@Override
	public DataLists search(ArrayList<Filter> filters, DataLists masterLists) {
		ArrayList<Task> masterTasksList = masterLists.getTasksList();
		ArrayList<FloatingTask> masterFloatingTasksList = masterLists.getFloatingTasksList();
		ArrayList<Event> masterEventsList = masterLists.getEventsList();
		
		ArrayList<Task> searchResultsTasksList = new ArrayList<Task>();
		getTasksSearchResults(filters, masterTasksList, searchResultsTasksList);
		
		ArrayList<FloatingTask> searchResultsFloatingTasksList = new ArrayList<FloatingTask>();
		getFloatingTasksSearchResult(filters, masterFloatingTasksList, searchResultsFloatingTasksList);
		
		ArrayList<Event> searchResultsEventsList = new ArrayList<Event>();
		getEventsSearchResult(filters, masterEventsList, searchResultsEventsList);
		
		DataLists resultsList = new DataLists(searchResultsTasksList, 
											  searchResultsFloatingTasksList,
											  searchResultsEventsList); // Result to be returned
		return resultsList;
	}

	private void getEventsSearchResult(ArrayList<Filter> filters, 
									   ArrayList<Event> masterEventsList,
									   ArrayList<Event> resultsEventsList) {
		for (Event event : masterEventsList) {
			if (checkFilters(event, filters)) {
				resultsEventsList.add(event);
			}
		}
	}

	private void getFloatingTasksSearchResult(ArrayList<Filter> filters,
											  ArrayList<FloatingTask> masterFloatingTasksList, 
											  ArrayList<FloatingTask> resultsFloatingTasksList) {
		for (FloatingTask floatingTask : masterFloatingTasksList) {
			if (checkFilters(floatingTask, filters)) {
				resultsFloatingTasksList.add(floatingTask);
			}
		}
	}

	private void getTasksSearchResults(ArrayList<Filter> filters, 
									   ArrayList<Task> masterTasksList,
									   ArrayList<Task> resultsTasksList) {
		for (Task task : masterTasksList) {
			if (checkFilters(task, filters)) {
				resultsTasksList.add(task);
			}
		}
	}
	
	private boolean checkFilters(Task task, ArrayList<Filter> filters) {
		for (Filter filter : filters) {
			if (!filter.check(task)) return false;
		}
		return true;
	}
	
	private boolean checkFilters(FloatingTask floatingTask, ArrayList<Filter> filters) {
		for (Filter filter : filters) {
			if (!filter.check(floatingTask)) return false;
		}
		return true;
	}
	
	private boolean checkFilters(Event event, ArrayList<Filter> filters) {
		for (Filter filter : filters) {
			if (!filter.check(event)) return false;
		}
		return true;
	}

}
