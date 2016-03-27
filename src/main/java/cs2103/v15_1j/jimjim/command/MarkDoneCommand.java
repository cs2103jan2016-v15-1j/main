package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

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
    public String undo(DataLists searchResultsList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String execute(DataLists searchResultsList, DataLists masterList, Storage storage, Searcher searcher) {
        Task task;
        try {
            switch (this.prefix) {
                case 'f':
                    task = masterList.getFloatingTasksList().get(taskNum-1);
                    break;
                case 'd':
                    task = masterList.getDeadlineTasksList().get(taskNum-1);
                    break;
                default:
                    assert false;    // shouldn't happen
                    task = null;
                    break;
            }
            task.setCompleted(true);
            if (storage.save(masterList)) {
                return "Done!";
            } else {
                task.setCompleted(false);
                return "Some error has occured. Please try again.";
            }
        } catch (IndexOutOfBoundsException e) {
            return "There is no item numbered " + this.prefix + this.taskNum;
        }
    }

}
