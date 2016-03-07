package cs2103.v15_1j.jimjim;

import java.time.LocalDateTime;
import java.util.List;

public class AddTaskCommand implements Command {
	
	private Task task;
	
	public AddTaskCommand(String name, LocalDateTime datetime) {
		this.task = new Task(name, datetime);
	}

	@Override
	public String undo(List<TaskEvent> displayList, List<TaskEvent> list, Storage storage, Searcher searcher) {
		// TODO
		return null;
	}

	@Override
	public String execute(List<TaskEvent> displayList, List<TaskEvent> list, Storage storage, Searcher searcher) {
		// Add to display list first to make it seem more responsive
		displayList.add(task);
	    list.add(task);
	    
	    if (storage.save(list)) {
	    	return "Task added";
	    } else {
	    	// If storage fails to save list
	    	// remove task from list and displayList
	    	list.remove(task);
	    	displayList.remove(task);
	    	return "Some error has occured. Please try again.";
	    }
	}

	public Task getTask() {
		return task;
	}

}
