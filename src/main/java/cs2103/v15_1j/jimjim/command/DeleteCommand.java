package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.model.TaskEvent;
import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
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
    public UIFeedback undo(DataLists searchResultsList, DataLists masterList, 
    					   Storage storage, Searcher searcher, Stack<UndoableCommand> undoCommandHistory,
    					   Stack<UndoableCommand> redoCommandHistory) {
		// Add task/event back at former position
	    masterList.add(taskNum-1, backup);
	    if (storage.save(masterList)) {
	    	redoCommandHistory.push(this);
	    	return new AddFeedback(backup);
	    } else {
	    	// failed, remove task
	    	undoCommandHistory.push(this);
	        masterList.remove(backup);
	        return new FailureFeedback("Some error has occured. Please try again.");
	    }
    }

    @Override
    public UIFeedback execute(DataLists searchResultsList, DataLists masterList, 
    						  Storage storage, Searcher searcher, Stack<UndoableCommand> undoCommandHistory,
    						  Stack<UndoableCommand> redoCommandHistory) {
        try {
            backup = masterList.removeTaskEvent(taskNum-1, prefix);
            if (storage.save(masterList)) {
            	undoCommandHistory.push(this);
                return new DeleteFeedback(backup);
            } else {
                // failed to delete, add the item back in the old position
            	redoCommandHistory.push(this);
                masterList.add(taskNum-1, backup);
                return new FailureFeedback("Some error has occured. Please try again.");
            }
        } catch (IndexOutOfBoundsException e) {
            return new FailureFeedback(
                    "There is no item numbered " + this.prefix + this.taskNum);
        }
    }
}
