package cs2103.v15_1j.jimjim.command;
import java.util.List;

import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public interface Command {
	public String undo(List<TaskEvent> displayList,
					 List<TaskEvent> list, Storage storage, Searcher searcher);
	public String execute(List<TaskEvent> displayList,
						List<TaskEvent> list, Storage storage, Searcher searcher);
}