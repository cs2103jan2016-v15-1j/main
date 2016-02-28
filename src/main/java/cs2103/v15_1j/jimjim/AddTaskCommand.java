package cs2103.v15_1j.jimjim;

import java.time.LocalDateTime;
import java.util.List;

public class AddTaskCommand implements Command {
	
	private Task task;
	
	public AddTaskCommand(String name, LocalDateTime datetime) {
		this.task =new Task(name, datetime);
	}

	@Override
	public String undo(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
		return null;
	}

	@Override
	public String execute(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
		return null;
	}

	public Task getTask() {
		return task;
	}

}
