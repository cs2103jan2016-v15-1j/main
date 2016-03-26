package cs2103.v15_1j.jimjim.command;

import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class ClearCommand implements Command {
    @Override
    public String execute(DataLists displayList, DataLists masterList, 
    					  Storage storage, Searcher searcher, Stack<Command> undoCommandHistory) {
        displayList.copy(masterList);
        return null;
    }

}
