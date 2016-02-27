package cs2103.v15_1j.jimjim;
import java.util.List;

public interface Searcher {
	public List<TaskEvent> search(String searchPhrase,
								  List<TaskEvent> allTaskEvents);
}
