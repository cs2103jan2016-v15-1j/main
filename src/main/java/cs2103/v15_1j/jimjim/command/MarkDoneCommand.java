package cs2103.v15_1j.jimjim.command;

import java.util.List;

import cs2103.v15_1j.jimjim.DataLists;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class MarkDoneCommand implements Command {
    private int taskNum;
    
    public MarkDoneCommand(int num) {
        this.taskNum = num;
    }
    
    public int getTaskNum() {
        return this.taskNum;
    }

    @Override
    public String undo(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String execute(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        TaskEvent backup;
        try {
            backup = displayList.remove(taskNum-1);
        } catch (IndexOutOfBoundsException e) {
            return "There is no item numbered " + this.taskNum;
        }
        if (!(backup instanceof Task)) {
            displayList.add(taskNum-1, backup);
            return "Number " + taskNum + " is an event, not a task!";
        }
        Task task = (Task) backup;
        if (!masterList.contains(task)) {
            // synchronization issue between list and displayList
            // quietly add the task to list
            masterList.add(task);
        }
        task.setCompleted(true);
        if (storage.save(masterList)) {
            return "Done!";
        } else {
            // failed to save, add the item back
            displayList.add(taskNum-1, task);
            task.setCompleted(false);
            return "Some error has occured. Please try again.";
        }
    }

}
