package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class DeleteCommand implements Command {

    private int taskNum;
    private char prefix;
    
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
    public String undo(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String execute(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        TaskEvent backup;
        try {
            switch (this.prefix) {
                case 'f':
                    backup = displayList.getFloatingTasksList().remove(taskNum-1);
                    break;
                case 'd':
                    backup = displayList.getDeadlineTasksList().remove(taskNum-1);
                    break;
                case 'e':
                    backup = displayList.getEventsList().remove(taskNum-1);
                    break;
                default:
                    assert false;    // shouldn't happen
                    backup = null;
                    break;
            }
            int ind = masterList.indexOf(backup);
            masterList.remove(backup);
            if (storage.save(masterList)) {
                return "Deleted!";
            } else {
                // failed to delete, add the item back in the old position
                displayList.add(taskNum-1, backup);
                masterList.add(ind, backup);
                return "Some error has occured. Please try again.";
            }
        } catch (IndexOutOfBoundsException e) {
            return "There is no item numbered " + this.prefix + this.taskNum;
        }
    }
}
