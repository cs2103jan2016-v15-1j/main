package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.MarkFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class MarkDoneCommand implements Command {
    private int taskNum;
    private char prefix;
    
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
    public UIFeedback undo(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UIFeedback execute(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        Task task;
        try {
            switch (this.prefix) {
                case 'f':
                    task = displayList.getFloatingTasksList().remove(taskNum-1);
                    break;
                case 'd':
                    task = displayList.getDeadlineTasksList().remove(taskNum-1);
                    break;
                default:
                    assert false;    // shouldn't happen
                    task = null;
                    break;
            }
            if (!masterList.contains(task)) {
                // synchronization issue between list and displayList
                // quietly add the task to list
                masterList.add(task);
            }
            task.setCompleted(true);
            if (storage.save(masterList)) {
                return new MarkFeedback(task);
            } else {
                // failed to save, add the item back
                displayList.add(taskNum-1, task);
                task.setCompleted(false);
                return new FailureFeedback(
                        "Some error has occured. Please try again.");
            }
        } catch (IndexOutOfBoundsException e) {
            return new FailureFeedback(
                    "There is no item numbered " + this.prefix + this.taskNum);
        }
    }

}
