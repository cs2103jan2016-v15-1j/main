package cs2103.v15_1j.jimjim.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.model.Event;

public class DataLists {
	private List<DeadlineTask> deadlineTasksList;
	private List<FloatingTask> floatingTasksList;
	private List<Event> eventsList;

	public DataLists() {
		this.deadlineTasksList = new ArrayList<DeadlineTask>();
		this.floatingTasksList = new ArrayList<FloatingTask>();
		this.eventsList = new ArrayList<Event>();
	}

	public DataLists(List<DeadlineTask> deadlineTasks,
	                 List<FloatingTask> floatingTasks,
	                 List<Event> events) {
		this.deadlineTasksList = deadlineTasks;
		this.floatingTasksList = floatingTasks;
		this.eventsList = events;
	}

	public DataLists(DataLists lists) {
		this.deadlineTasksList = new ArrayList<DeadlineTask>(lists.getDeadlineTasksList());
		this.floatingTasksList = new ArrayList<FloatingTask>(lists.getFloatingTasksList());
		this.eventsList = new ArrayList<Event>(lists.getEventsList());
	}

	public DataLists(ArrayList<DeadlineTask> deadlineTasksList, ArrayList<FloatingTask> floatingTasksList, 
			ArrayList<Event> eventsList) {
		this.deadlineTasksList = deadlineTasksList;
		this.floatingTasksList = floatingTasksList;
		this.eventsList = eventsList;
	}

	public List<DeadlineTask> getDeadlineTasksList() {
		return deadlineTasksList;
	}
	
	public List<FloatingTask> getFloatingTasksList() {
		return floatingTasksList;
	}
	public List<Event> getEventsList() {
		return eventsList;
	}

	public void add(TaskEvent taskEvent) {
		if (taskEvent instanceof DeadlineTask) {
			DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
			this.deadlineTasksList.add(deadlineTask);
			Collections.sort(deadlineTasksList);
		} else if (taskEvent instanceof FloatingTask) {
			FloatingTask floatingTask = (FloatingTask) taskEvent;
			this.floatingTasksList.add(floatingTask);
			Collections.sort(floatingTasksList);
		} else if (taskEvent instanceof Event) {
			Event event = (Event) taskEvent;
			this.eventsList.add(event);
			Collections.sort(eventsList);
		}
	}

	public void remove(TaskEvent taskEvent) {
	    if (taskEvent instanceof DeadlineTask) {
	        DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
	        this.deadlineTasksList.remove(deadlineTask);
	    } else if (taskEvent instanceof FloatingTask) {
	        FloatingTask floatingTask = (FloatingTask) taskEvent;
	        this.floatingTasksList.remove(floatingTask);
	    } else if (taskEvent instanceof Event) {
	        Event event = (Event) taskEvent;
	        this.eventsList.remove(event);
	    }
	}
}
