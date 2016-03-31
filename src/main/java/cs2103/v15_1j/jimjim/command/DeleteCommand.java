package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.AddFeedback;
import cs2103.v15_1j.jimjim.uifeedback.DeleteFeedback;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class DeleteCommand implements UndoableCommand {
    private int taskNum;
    private char prefix;
    private TaskEvent backup;
    
    public DeleteCommand(char prefix, int num) {
        this.taskNum = num;
        this.prefix = prefix;
    }
    
    public int getTaskNum() {
        return this.taskNum;
    }
    
    public char getPrefix() {
        return this.prefix;
    }
    
    @Override
    public UIFeedback undo(ControllerStates conStates) {
		// Add task/event back at former position
	    conStates.masterList.add(backup);
	    conStates.searchResultsList.add(backup);
	    if (conStates.storage.save(conStates.masterList)) {
	    	conStates.redoCommandHistory.push(this);
	    	return new AddFeedback(backup);
	    } else {
	    	// failed, remove task
	    	conStates.undoCommandHistory.push(this);
	        conStates.masterList.remove(backup);
		    conStates.searchResultsList.remove(backup);
	        return new FailureFeedback("Some error has occured. Please try again.");
	    }
    }

    @Override
    public UIFeedback execute(ControllerStates conStates) {
        try {
    		TaskEvent displayTemp = conStates.displayList.getTaskEvent(taskNum-1, prefix);
            backup = conStates.masterList.remove(displayTemp);
            conStates.searchResultsList.remove(displayTemp);
            if (conStates.storage.save(conStates.masterList)) {
            	conStates.undoCommandHistory.push(this);
                return new DeleteFeedback(backup);
            } else {
                // failed to delete, add the item back in the old position
            	conStates.redoCommandHistory.push(this);
                conStates.masterList.add(taskNum-1, backup);
                conStates.searchResultsList.add(backup);
                return new FailureFeedback("Some error has occured. Please try again.");
            }
        } catch (IndexOutOfBoundsException e) {
            return new FailureFeedback(
                    "There is no item numbered " + this.prefix + this.taskNum);
        }
    }
}
