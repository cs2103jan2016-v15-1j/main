import java.util.List;

public interface Searcher {
	public List<TaskEvent> search(String searchPhrase,
								  List<TaskEvent> allTaskEvents);
}
