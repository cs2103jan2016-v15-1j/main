package cs2103.v15_1j.jimjim;
import java.util.List;

import cs2103.v15_1j.jimjim.model.TaskEvent;

public interface Searcher {
	public List<TaskEvent> search(String searchPhrase,
								  List<TaskEvent> allTaskEvents);
}
