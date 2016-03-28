package cs2103.v15_1j.jimjim.command;

import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.MarkFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UnmarkFeedback;

public class UnmarkCommand implements UndoableCommand {
    private int taskNum;
    private char prefix;
    private Task backup;
    
    public UnmarkCommand(char prefix, int num) {
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
        backup.setCompleted(true);
        if (storage.save(masterList)) {
        	redoCommandHistory.push(this);
        	return new MarkFeedback(backup);
        } else {
        	backup.setCompleted(false);
        	undoCommandHistory.push(this);
        	return new FailureFeedback("Some error has occured. Please try again.");
        }
    }

    @Override
    public UIFeedback execute(DataLists searchResultsList, DataLists masterList, 
    						  Storage storage, Searcher searcher, Stack<UndoableCommand> undoCommandHistory,
    						  Stack<UndoableCommand> redoCommandHistory) {
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
            backup.setCompleted(false);
            if (storage.save(masterList)) {
            	undoCommandHistory.push(this);
                return new UnmarkFeedback(backup);
            } else {
            	redoCommandHistory.push(this);
                backup.setCompleted(true);
                return new FailureFeedback("Some error has occured. Please try again.");
            }
        } catch (IndexOutOfBoundsException e) {
            return new FailureFeedback(
                    "There is no item numbered " + this.prefix + this.taskNum);
        }
    }
}
