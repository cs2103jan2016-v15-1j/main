package cs2103.v15_1j.jimjim.model;

import java.util.ArrayList;
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
	    } else if (taskEvent instanceof FloatingTask) {
	        FloatingTask floatingTask = (FloatingTask) taskEvent;
	        this.floatingTasksList.add(floatingTask);
	    } else if (taskEvent instanceof Event) {
	        Event event = (Event) taskEvent;
	        this.eventsList.add(event);
	    }
	}

    public void add(int i, TaskEvent taskEvent) {
	    if (taskEvent instanceof DeadlineTask) {
	        DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
	        this.deadlineTasksList.add(i, deadlineTask);
	    } else if (taskEvent instanceof FloatingTask) {
	        FloatingTask floatingTask = (FloatingTask) taskEvent;
	        this.floatingTasksList.add(i, floatingTask);
	    } else if (taskEvent instanceof Event) {
	        Event event = (Event) taskEvent;
	        this.eventsList.add(i, event);
	    } else {
	        assert false;
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

    public int indexOf(TaskEvent taskEvent) {
	    if (taskEvent instanceof DeadlineTask) {
	        DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
	        return this.deadlineTasksList.indexOf(deadlineTask);
	    } else if (taskEvent instanceof FloatingTask) {
	        FloatingTask floatingTask = (FloatingTask) taskEvent;
	        return this.floatingTasksList.indexOf(floatingTask);
	    } else if (taskEvent instanceof Event) {
	        Event event = (Event) taskEvent;
	        return this.eventsList.indexOf(event);
	    } else {
	        assert false;
	        return 0;
	    }
    }

    public boolean contains(TaskEvent taskEvent) {
        return floatingTasksList.contains(taskEvent)
                || deadlineTasksList.contains(taskEvent)
                || eventsList.contains(taskEvent);
    }
}
