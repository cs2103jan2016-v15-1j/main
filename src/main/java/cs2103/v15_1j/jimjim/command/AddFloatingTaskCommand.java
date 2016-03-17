package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.DataLists;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class AddFloatingTaskCommand implements Command {

	private Task task;
	
	public AddFloatingTaskCommand(String name) {
		this.task = new Task(name);
	}

	@Override
	public String undo(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
		// TODO
		return null;
	}

	@Override
	public String execute(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
		// Add to display list first to make it seem more responsive
		displayList.getFloatingTasksList().add(task);
	    masterList.getFloatingTasksList().add(task);
	    
	    if (storage.save(masterList)) {
	    	return "Task added";
	    } else {
	    	// If storage fails to save list
	    	// remove task from list and displayList
	    	masterList.getFloatingTasksList().remove(task);
	    	displayList.getFloatingTasksList().remove(task);
	    	return "Some error has occured. Please try again.";
	    }
	}

	public Task getTask() {
		return task;
	}

}
