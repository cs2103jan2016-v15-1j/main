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

	public DataLists(List<DeadlineTask> deadlineTasks, List<FloatingTask> floatingTasks, List<Event> events) {
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
	
	/* @@author A0124995R */
	public TaskEvent getTaskEvent(int num, char prefix) {
		TaskEvent result;
		switch (prefix) {
		case 'f':
			result = floatingTasksList.get(num);
			break;
		case 'd':
			result = deadlineTasksList.get(num);
			break;
		case 'e':
			result = eventsList.get(num);
			break;
		default:
			assert false;    // shouldn't happen
			result = null;
			break;
		}
		return result;
	}
	
	public TaskEvent remove(int num, char prefix) {
		TaskEvent result;
        switch (prefix) {
	        case 'f':
	            result = floatingTasksList.remove(num);
	            break;
	        case 'd':
	            result = deadlineTasksList.remove(num);
	            break;
	        case 'e':
	            result = eventsList.remove(num);
	            break;
	        default:
	            assert false;    // shouldn't happen
	            result = null;
	            break;
        }
        return result;
	}

	//@@author
	public TaskEvent remove(TaskEvent taskEvent) {
		TaskEvent result;
		int index = indexOf(taskEvent);

		if (taskEvent instanceof DeadlineTask) {
			result = deadlineTasksList.remove(index);
		} else if (taskEvent instanceof FloatingTask) {
			result = floatingTasksList.remove(index);
		} else if (taskEvent instanceof Event) {
			result = eventsList.remove(index);
		} else {
			assert false;    // shouldn't happen
			result = null;
		}

		return result;
	}

	//@@author
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

	//@@author A0139963N
	public void addWithoutSorting(TaskEvent taskEvent) {
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

	//@@author 
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

	//@@author 
	public boolean contains(TaskEvent taskEvent) {
		return floatingTasksList.contains(taskEvent)
				|| deadlineTasksList.contains(taskEvent)
				|| eventsList.contains(taskEvent);
	}

	//@@author 
	public int size() {
		return floatingTasksList.size() + deadlineTasksList.size() + eventsList.size();
	}

	//@@author
	public int size(char prefix) {
		int size;
		switch (prefix) {
		case 'f':
			size = floatingTasksList.size();
			break;
		case 'd':
			size = deadlineTasksList.size();
			break;
		case 'e':
			size = eventsList.size();
			break;
		default:
			assert false;    // shouldn't happen
			size = 0;
			break;
		}
		return size;
	}

	//@@author
	public boolean isEmpty(){
		return floatingTasksList.isEmpty() && deadlineTasksList.isEmpty() && eventsList.isEmpty();
	}

	//@@author
	public void copy(DataLists masterList) {
		this.clear();
		floatingTasksList.addAll(masterList.getFloatingTasksList());
		deadlineTasksList.addAll(masterList.getDeadlineTasksList());
		eventsList.addAll(masterList.getEventsList());
	}

	//@@author A0139963N
	public void clear() {
		floatingTasksList.clear();
		deadlineTasksList.clear();
		eventsList.clear();
	}

	//@@author A0139963N
	public void sort(){
		Collections.sort(deadlineTasksList);
		Collections.sort(floatingTasksList);
		Collections.sort(eventsList);
	}
}
