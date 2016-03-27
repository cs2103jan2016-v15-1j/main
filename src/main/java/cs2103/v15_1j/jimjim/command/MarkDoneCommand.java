package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.model.Task;

import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.MarkFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UnmarkFeedback;

public class MarkDoneCommand implements UndoableCommand {
    private int taskNum;
    private char prefix;
    private Task backup;
    
    public MarkDoneCommand(char prefix, int num) {
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
    				   Storage storage, Searcher searcher, Stack<Command> undoCommandHistory) {
        backup.setCompleted(false);
        if (storage.save(masterList)) {
        	return new UnmarkFeedback(backup);
        } else {
        	backup.setCompleted(true);
        	undoCommandHistory.push(this);
        	return new FailureFeedback("Some error has occured. Please try again.");
        }
    }

    @Override
    public UIFeedback execute(DataLists searchResultsList, DataLists masterList, 
    						  Storage storage, Searcher searcher, Stack<Command> undoCommandHistory) {
        try {
            switch (this.prefix) {
                case 'f':
                    backup = masterList.getFloatingTasksList().get(taskNum-1);
                    break;
                case 'd':
                    backup = masterList.getDeadlineTasksList().get(taskNum-1);
                    break;
                default:
                    assert false;    // shouldn't happen
                    backup = null;
                    break;
            }
            backup.setCompleted(true);
            if (storage.save(masterList)) {
            	undoCommandHistory.push(this);
                return new MarkFeedback(backup);
            } else {
                // failed to save, add the item back
                backup.setCompleted(false);
                return new FailureFeedback("Some error has occured. Please try again.");
            }
        } catch (IndexOutOfBoundsException e) {
            return new FailureFeedback(
                    "There is no item numbered " + this.prefix + this.taskNum);
        }
    }

}
