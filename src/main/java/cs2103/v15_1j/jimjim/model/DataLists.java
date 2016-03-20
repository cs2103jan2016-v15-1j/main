package cs2103.v15_1j.jimjim.model;

import java.util.ArrayList;
import java.util.List;

public class DataLists {
	private List<DeadlineTask> deadlineTasksList;
	private List<Task> floatingTasksList;
	private List<Event> eventsList;
	
	public DataLists() {
		this.deadlineTasksList = new ArrayList<DeadlineTask>();
		this.floatingTasksList = new ArrayList<Task>();
		this.eventsList = new ArrayList<Event>();
	}
	
	public List<DeadlineTask> getDeadlineTasksList() {
		return deadlineTasksList;
	}

	public List<Task> getFloatingTasksList() {
		return floatingTasksList;
	}

	public List<Event> getEventsList() {
		return eventsList;
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
