package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.controller.ControllerStates;
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
    public UIFeedback undo(ControllerStates conStates) {
        backup.setCompleted(false);
        if (conStates.storage.save(conStates.masterList)) {
        	conStates.redoCommandHistory.push(this);
        	return new UnmarkFeedback(backup);
        } else {
        	backup.setCompleted(true);
        	conStates.undoCommandHistory.push(this);
        	return new FailureFeedback("Some error has occured. Please try again.");
        }
    }

    @Override
    public UIFeedback execute(ControllerStates conStates) {
        try {
            backup = (Task) conStates.displayList.getTaskEvent(taskNum-1, prefix);
            backup.setCompleted(true);
            if (conStates.storage.save(conStates.masterList)) {
            	conStates.undoCommandHistory.push(this);
                return new MarkFeedback(backup);
            } else {
                // failed to save, add the item back
            	conStates.redoCommandHistory.push(this);
                backup.setCompleted(false);
                return new FailureFeedback("Some error has occured. Please try again.");
            }
        } catch (IndexOutOfBoundsException e) {
            return new FailureFeedback(
                    "There is no item numbered " + this.prefix + this.taskNum);
        }
    }

}
