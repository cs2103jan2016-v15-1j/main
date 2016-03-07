package cs2103.v15_1j.jimjim;

import java.util.List;

public class DeleteCommand implements Command {

    private int taskNum;
    
    public DeleteCommand(int num) {
        this.taskNum = num;
    }
    
    public int getTaskNum() {
        return this.taskNum;
    }
    
    @Override
    public String undo(List<TaskEvent> displayList, List<TaskEvent> list, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String execute(List<TaskEvent> displayList, List<TaskEvent> list, Storage storage, Searcher searcher) {
        TaskEvent backup;
        try {
            backup = displayList.remove(taskNum-1);
        } catch (IndexOutOfBoundsException e) {
            return "There is no item numbered " + this.taskNum;
        }
        int ind = list.indexOf(backup);
        list.remove(backup);
        if (storage.save(list)) {
            return "Deleted!";
        } else {
            // failed to delete, add the item back
            displayList.add(taskNum-1, backup);
            list.add(ind, backup);
            return "Some error has occured. Please try again.";
        }
    }

}
