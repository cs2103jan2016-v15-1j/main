package cs2103.v15_1j.jimjim.command;

import java.time.LocalDateTime;

import cs2103.v15_1j.jimjim.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class AddTaskCommand implements Command {
	
	private DeadlineTask task;
	
	public AddTaskCommand(String name, LocalDateTime datetime) {
		this.task = new DeadlineTask(name, datetime);
	}

	@Override
	public String undo(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
		// TODO
		return null;
	}

	@Override
	public String execute(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
		// Add to display list first to make it seem more responsive
		displayList.getTasksList().add(task);
	    masterList.getTasksList().add(task);
	    
	    if (storage.save(masterList)) {
	    	return "Task added";
	    } else {
	    	// If storage fails to save list
	    	// remove task from list and displayList
	    	masterList.getTasksList().remove(task);
	    	displayList.getTasksList().remove(task);
	    	return "Some error has occured. Please try again.";
	    }
	}

	public DeadlineTask getTask() {
		return task;
	}

}
