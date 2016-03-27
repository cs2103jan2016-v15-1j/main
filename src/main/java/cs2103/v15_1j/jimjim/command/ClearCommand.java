package cs2103.v15_1j.jimjim.command;

import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class ClearCommand implements Command {
    @Override
    public UIFeedback execute(DataLists displayList, DataLists masterList, 
    					  Storage storage, Searcher searcher, Stack<Command> undoCommandHistory) {
        displayList.copy(masterList);
        return null;
    }

}
