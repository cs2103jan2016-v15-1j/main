package cs2103.v15_1j.jimjim;

import java.util.ArrayList;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.Event;

public class DataLists {
	private ArrayList<Task> tasksList;
	private ArrayList<FloatingTask> floatingTasksList;
	private ArrayList<Event> eventsList;
	
	public ArrayList<Task> getTasksList() {
		return tasksList;
	}
	
	public void setTasksList(ArrayList<Task> tasksList) {
		this.tasksList = tasksList;
	}
	
	public ArrayList<FloatingTask> getFloatingTasksList() {
		return floatingTasksList;
	}
	
	public void setFloatingTasksList(ArrayList<FloatingTask> floatingTasksList) {
		this.floatingTasksList = floatingTasksList;
	}
	
	public ArrayList<Event> getEventsList() {
		return eventsList;
	}
	
	public void setEventsList(ArrayList<Event> eventsList) {
		this.eventsList = eventsList;
	}
}
