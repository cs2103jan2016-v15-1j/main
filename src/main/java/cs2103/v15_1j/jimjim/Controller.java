package cs2103.v15_1j.jimjim;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import javafx.collections.ObservableList;

public interface Controller {
	public String execute(String userCommand);
	public ObservableList<TaskEvent> getDisplayList();
	public void setStorage(Storage storage);
	public void setParser(Parser parser);
	public void setSearcher(Searcher searcher);
}
