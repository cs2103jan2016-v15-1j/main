package cs2103.v15_1j.jimjim.controller;

import java.util.Stack;

import cs2103.v15_1j.jimjim.command.UndoableCommand;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.parser.Parser;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class ControllerStates {
	public DataLists searchResultsList;
	public DataLists masterList;
	public DataLists displayList;
	public Stack<UndoableCommand> undoCommandHistory = new Stack<UndoableCommand>();
	public Stack<UndoableCommand> redoCommandHistory = new Stack<UndoableCommand>();
	public Configuration config;
	public Parser parser;
	public Searcher searcher;
	public Storage storage;
	
	/* @@author A0124995R */
	public void resetRedoHistory() {
		redoCommandHistory = new Stack<UndoableCommand>();
	}
}
