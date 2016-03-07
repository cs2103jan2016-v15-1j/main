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
        // TODO Auto-generated method stub
        return null;
    }

}
