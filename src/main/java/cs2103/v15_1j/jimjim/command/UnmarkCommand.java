package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.MarkFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UnmarkFeedback;

public class UnmarkCommand implements UndoableCommand {
    private int taskNum;
    private char prefix;
    private TaskEvent backup;
    
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
    public UIFeedback undo(ControllerStates conStates) {
        backup.setCompleted(true);
        if (conStates.storage.save(conStates.masterList)) {
        	conStates.redoCommandHistory.push(this);
        	return new MarkFeedback(backup);
        } else {
        	backup.setCompleted(false);
        	conStates.undoCommandHistory.push(this);
        	return new FailureFeedback("Some error has occured. Please try again.");
        }
    }

    @Override
    public UIFeedback execute(ControllerStates conStates) {
        try {
            backup = conStates.displayList.getTaskEvent(taskNum-1, prefix);
            backup.setCompleted(false);
            if (conStates.storage.save(conStates.masterList)) {
            	conStates.undoCommandHistory.push(this);
                return new UnmarkFeedback(backup);
            } else {
            	conStates.redoCommandHistory.push(this);
                backup.setCompleted(true);
                return new FailureFeedback("Some error has occured. Please try again.");
            }
        } catch (IndexOutOfBoundsException e) {
            return new FailureFeedback(
                    "There is no item numbered " + this.prefix + this.taskNum);
        }
    }
}
