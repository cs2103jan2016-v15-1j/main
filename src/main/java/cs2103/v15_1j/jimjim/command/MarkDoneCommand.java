package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.model.Task;

import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class MarkDoneCommand implements UndoableCommand {
    private int taskNum;
    private char prefix;
    private Task backup;
    
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
    public String undo(DataLists displayList, DataLists masterList, 
    				   Storage storage, Searcher searcher, Stack<Command> undoCommandHistory) {
        backup.setCompleted(false);
        masterList.add(taskNum-1, backup);
        if (storage.save(masterList)) {
        	return "Task undone!";
        } else {
        	masterList.remove(backup);
        	backup.setCompleted(true);
        	undoCommandHistory.push(this);
        	return "Some error has occured. Please try again.";
        }
    }

    @Override
    public String execute(DataLists displayList, DataLists masterList, 
    					  Storage storage, Searcher searcher, Stack<Command> undoCommandHistory) {
        try {
            switch (this.prefix) {
                case 'f':
                    backup = displayList.getFloatingTasksList().remove(taskNum-1);
                    break;
                case 'd':
                    backup = displayList.getDeadlineTasksList().remove(taskNum-1);
                    break;
                default:
                    assert false;    // shouldn't happen
                    backup = null;
                    break;
            }
            if (!masterList.contains(backup)) {
                // synchronization issue between list and displayList
                // quietly add the task to list
                masterList.add(backup);
            }
            backup.setCompleted(true);
            if (storage.save(masterList)) {
            	undoCommandHistory.push(this);
                return "Task done!";
            } else {
                // failed to save, add the item back
                displayList.add(taskNum-1, backup);
                backup.setCompleted(false);
                return "Some error has occured. Please try again.";
            }
        } catch (IndexOutOfBoundsException e) {
            return "There is no item numbered " + this.prefix + this.taskNum;
        }
    }

}
