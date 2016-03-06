package cs2103.v15_1j.jimjim;

import java.time.LocalDateTime;
import java.util.List;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;

public class AddTaskCommand implements Command {
	
	private Task task;
	
	public AddTaskCommand(String name, LocalDateTime datetime) {
		this.task = new Task(name, datetime);
	}

	@Override
	public String undo(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
	    //TODO
		return null;
	}

	@Override
	public String execute(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
	    if (storage.create(task)) {
	        // success
	        displayList.add(task);
	        return "Task added";
	    } else {
	        return "Some error has occured. Please try again.";
	    }
	}

	public Task getTask() {
		return task;
	}

}
