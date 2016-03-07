package cs2103.v15_1j.jimjim;

import java.util.List;

public class MarkDoneCommand implements Command {
    private int taskNum;
    
    public MarkDoneCommand(int num) {
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
        if (!(backup instanceof Task)) {
            displayList.add(taskNum-1, backup);
            return "Number " + taskNum + " is an event, not a task!";
        }
        Task task = (Task) backup;
        if (!list.contains(task)) {
            // synchronization issue between list and displayList
            // quietly add the task to list
            list.add(task);
        }
        task.completed = true;
        if (storage.save(list)) {
            return "Done!";
        } else {
            // failed to save, add the item back
            displayList.add(taskNum-1, task);
            task.completed = false;
            return "Some error has occured. Please try again.";
        }
    }

}
