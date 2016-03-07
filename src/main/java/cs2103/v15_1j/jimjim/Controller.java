package cs2103.v15_1j.jimjim;
import java.util.List;
import cs2103.v15_1j.jimjim.model.TaskEvent;

public interface Controller {
	public String execute(String userCommand);
	public List<TaskEvent> getDisplayList();
	public void setStorage(Storage storage);
	public void setParser(Parser parser);
	public void setSearcher(Searcher searcher);
}
