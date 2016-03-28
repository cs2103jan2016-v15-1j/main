package cs2103.v15_1j.jimjim.command;

import java.time.LocalDateTime;
import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.AddFeedback;
import cs2103.v15_1j.jimjim.uifeedback.DeleteFeedback;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class AddCommand implements UndoableCommand {
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
    public UIFeedback undo(DataLists searchResultsList, DataLists masterList, 
    					   Storage storage, Searcher searcher, Stack<UndoableCommand> undoCommandHistory,
    					   Stack<UndoableCommand> redoCommandHistory) {
	    masterList.remove(taskEvent);
	    
	    if (storage.save(masterList)) {
	    	redoCommandHistory.push(this);
	        return new DeleteFeedback(taskEvent);
	    } else {
	        // If storage fails to save list
	        // add task/event back to masterList and displayList
	        masterList.add(taskEvent);
	        undoCommandHistory.push(this);
	        return new FailureFeedback("Some error has occured. Please try again.");
	    }
    }

    @Override
    public UIFeedback execute(DataLists searchResultsList, DataLists masterList, 
    						  Storage storage, Searcher searcher, Stack<UndoableCommand> undoCommandHistory,
    						  Stack<UndoableCommand> redoCommandHistory) {
        masterList.add(taskEvent);
        
        if (storage.save(masterList)) {
        	undoCommandHistory.push(this);
        	return new AddFeedback(taskEvent);
        } else {
            // If storage fails to save list
            // remove task
        	redoCommandHistory.push(this);
            masterList.remove(taskEvent);
            return new FailureFeedback("Some error has occured. Please try again.");
        }
    }

    public TaskEvent getTaskEvent() {
        return taskEvent;
    }

}
