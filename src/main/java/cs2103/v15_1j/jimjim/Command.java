package cs2103.v15_1j.jimjim;
import java.util.List;

public interface Command {
	public void undo(List<TaskEvent> displayList,
					 Storage storage, Searcher searcher);
	public void execute(List<TaskEvent> displayList,
						Storage storage, Searcher searcher);
}
