package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.DataLists;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class DeleteCommand implements Command {

    private int taskNum;
    
    public DeleteCommand(int num) {
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
        int ind = masterList.indexOf(backup);
        masterList.remove(backup);
        if (storage.save(masterList)) {
            return "Deleted!";
        } else {
            // failed to delete, add the item back
            displayList.add(taskNum-1, backup);
            masterList.add(ind, backup);
            return "Some error has occured. Please try again.";
        }
    }

}
