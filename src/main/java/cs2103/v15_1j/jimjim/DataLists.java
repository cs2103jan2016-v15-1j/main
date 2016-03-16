package cs2103.v15_1j.jimjim;

import java.util.ArrayList;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.Event;

public class DataLists {
	private ArrayList<Task> tasksList;
	private ArrayList<FloatingTask> floatingTasksList;
	private ArrayList<Event> eventsList;
	
	public DataLists(ArrayList<Task> tasksList,
					 ArrayList<FloatingTask> floatingTasksList,
					 ArrayList<Event> eventsList) {
		this.tasksList = tasksList;
		this.floatingTasksList = floatingTasksList;
		this.eventsList = eventsList;
	}
	
	public ArrayList<Task> getTasksList() {
		return tasksList;
	}

	public ArrayList<FloatingTask> getFloatingTasksList() {
		return floatingTasksList;
	}

	public ArrayList<Event> getEventsList() {
		return eventsList;
	}
}
