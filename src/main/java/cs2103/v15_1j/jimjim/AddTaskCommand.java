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
	    //TODO
		return null;
	}

	@Override
	public String execute(List<TaskEvent> displayList, List<TaskEvent> list, Storage storage, Searcher searcher) {
		displayList.add(task);
	    if (storage.save(displayList)) {
	        // success
	        return "Task added";
	    } else {
	        return "Some error has occured. Please try again.";
	    }
	}

	public Task getTask() {
		return task;
	}

}
