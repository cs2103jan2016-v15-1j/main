package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
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
        // TODO This only mark done for deadline tasks, not floating tasks
        DeadlineTask task;
        try {
            task = displayList.getDeadlineTasksList().remove(taskNum-1);
        } catch (IndexOutOfBoundsException e) {
            return "There is no item numbered " + this.taskNum;
        }
        if (!masterList.getDeadlineTasksList().contains(task)) {
            // synchronization issue between list and displayList
            // quietly add the task to list
            masterList.getDeadlineTasksList().add(task);
        }
        task.setCompleted(true);
        if (storage.save(masterList)) {
            return "Done!";
        } else {
            // failed to save, add the item back
            displayList.getDeadlineTasksList().add(taskNum-1, task);
            task.setCompleted(false);
            return "Some error has occured. Please try again.";
        }
    }

}
