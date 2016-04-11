package cs2103.v15_1j.jolt.controller;

import java.util.Stack;

import cs2103.v15_1j.jolt.command.SearchCommand;
import cs2103.v15_1j.jolt.command.UndoableCommand;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.parser.Parser;
import cs2103.v15_1j.jolt.searcher.Searcher;
import cs2103.v15_1j.jolt.storage.Storage;

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
	public SearchCommand lastSearch;
	
	/* @@author A0124995R */
	public void resetRedoHistory() {
		redoCommandHistory = new Stack<UndoableCommand>();
	}
}
