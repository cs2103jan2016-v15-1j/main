package cs2103.v15_1j.jimjim;

import java.time.LocalDateTime;
import java.util.List;

public class AddTaskCommand implements Command {
	
	private Task task;
	
	public AddTaskCommand(String name, LocalDateTime datetime) {
		this.task =new Task(name, datetime);
	}

	@Override
	public void undo(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
		// TODO Auto-generated method stub

	}

	public Task getTask() {
		return task;
	}

}
