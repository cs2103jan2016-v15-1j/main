package cs2103.v15_1j.jimjim.command;

import java.time.LocalDateTime;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;
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
    public UIFeedback undo(ControllerStates conStates) {
	    conStates.masterList.remove(taskEvent);
	    
	    if (conStates.storage.save(conStates.masterList)) {
	    	conStates.redoCommandHistory.push(this);
	        return new DeleteFeedback(taskEvent);
	    } else {
	        // If conStates.storage fails to save list
	        // add task/event back to conStates.masterList and displayList
	        conStates.masterList.add(taskEvent);
	        conStates.undoCommandHistory.push(this);
	        return new FailureFeedback("Some error has occured. Please try again.");
	    }
    }

    @Override
    public UIFeedback execute(ControllerStates conStates) {
        conStates.masterList.add(taskEvent);
       
        if (conStates.storage.save(conStates.masterList)) {
        	conStates.undoCommandHistory.push(this);
        	return new AddFeedback(taskEvent);
        } else {
            // If conStates.storage fails to save list
            // remove task
        	conStates.redoCommandHistory.push(this);
            conStates.masterList.remove(taskEvent);
            return new FailureFeedback("Some error has occured. Please try again.");
        }
    }

    public TaskEvent getTaskEvent() {
        return taskEvent;
    }

}
