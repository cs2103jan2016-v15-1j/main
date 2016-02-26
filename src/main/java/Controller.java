import java.util.List;

public interface Controller {
	public String execute(String userCommand);
	public List<TaskEvent> getDisplayList();
	public void setStorage(Storage storage);
	public void setParser(Parser parser);
	public void setSearcher(Searcher searcher);
}
