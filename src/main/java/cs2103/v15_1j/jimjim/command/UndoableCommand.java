package cs2103.v15_1j.jimjim.command;

import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

interface UndoableCommand extends Command {
	public String undo(DataLists displayList, DataLists masterList, 
			   Storage storage, Searcher searcher, Stack<Command> undoCommandHistory);
}