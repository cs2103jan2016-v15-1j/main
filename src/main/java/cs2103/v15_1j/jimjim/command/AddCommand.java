package cs2103.v15_1j.jimjim.command;

import java.time.LocalDateTime;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class AddCommand implements Command {
    private TaskEvent taskEvent;
    
    public AddCommand(String name, LocalDateTime datetime) {
        this.taskEvent = new DeadlineTask(name, datetime);
    }

	public AddCommand(String name) {
		this.taskEvent = new FloatingTask(name);
	}

    public AddCommand(String name, LocalDateTime start, LocalDateTime end) {
        this.taskEvent = new Event(name, start, end);
    }

    @Override
    public String undo(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO
        return null;
    }

    @Override
    public String execute(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        // Add to display list first to make it seem more responsive
        displayList.add(taskEvent);
        masterList.add(taskEvent);
        
        if (storage.save(masterList)) {
            return "Task/Event added";
        } else {
            // If storage fails to save list
            // remove task from list and displayList
            masterList.remove(taskEvent);
            displayList.remove(taskEvent);
            return "Some error has occured. Please try again.";
        }
    }

    public TaskEvent getTaskEvent() {
        return taskEvent;
    }

}
