package cs2103.v15_1j.jimjim.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.model.Event;

public class DataLists {
	private List<DeadlineTask> deadlineTasksList;
	private List<Task> floatingTasksList;
	private List<Event> eventsList;
	
	public DataLists() {
		this.deadlineTasksList = new ArrayList<DeadlineTask>();
		this.floatingTasksList = new ArrayList<Task>();
		this.eventsList = new ArrayList<Event>();
	}
	
	public DataLists(DataLists lists) {
		this.deadlineTasksList = new ArrayList<DeadlineTask>(lists.getDeadlineTasksList());
		this.floatingTasksList = new ArrayList<Task>(lists.getTasksList());
		this.eventsList = new ArrayList<Event>(lists.getEventsList());
	}
	
	public DataLists(ArrayList<DeadlineTask> deadlineTasksList, ArrayList<Task> floatingTasksList, 
			ArrayList<Event> eventsList) {
		this.deadlineTasksList = deadlineTasksList;
		this.floatingTasksList = floatingTasksList;
		this.eventsList = eventsList;
	}
	
	public List<DeadlineTask> getDeadlineTasksList() {
		return deadlineTasksList;
	}
	
	public void setDeadlineTasksList(List<DeadlineTask> list) {
		this.deadlineTasksList = list;
	}

	public List<Task> getTasksList() {
		return floatingTasksList;
	}
	
	public void setTasksList(List<Task> list) {
		this.floatingTasksList = list;
	}

	public List<Event> getEventsList() {
		return eventsList;
	}
	
	public void setEventsList(List<Event> list) {
		this.eventsList = list;
	}
	
	public void add(TaskEvent taskEvent) {
	    if (taskEvent instanceof DeadlineTask) {
	        DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
	        this.deadlineTasksList.add(deadlineTask);
	    } else if (taskEvent instanceof Task) {
	        Task floatingTask = (Task) taskEvent;
	        this.floatingTasksList.add(floatingTask);
	    } else if (taskEvent instanceof Event) {
	        Event event = (Event) taskEvent;
	        this.eventsList.add(event);
	    }
	    
	    Collections.sort(deadlineTasksList);
	    Collections.sort(floatingTasksList);
	    Collections.sort(eventsList);
	}

	public void remove(TaskEvent taskEvent) {
	    if (taskEvent instanceof DeadlineTask) {
	        DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
	        this.deadlineTasksList.remove(deadlineTask);
	    } else if (taskEvent instanceof Task) {
	        Task floatingTask = (Task) taskEvent;
	        this.floatingTasksList.remove(floatingTask);
	    } else if (taskEvent instanceof Event) {
	        Event event = (Event) taskEvent;
	        this.eventsList.remove(event);
	    }
	}
}
