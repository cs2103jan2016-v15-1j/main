package cs2103.v15_1j.jimjim;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JJControllerUI implements Controller {
	
	private List<TaskEvent> displayList = new ArrayList<TaskEvent>();
	private Parser parser;
	private Searcher searcher;
	private Storage storage;
	
	@Override
	public String execute(String userCommand) {
		displayList.add(new Task(userCommand,LocalDateTime.of(20, 1, 1, 1,1)));
		return "Success";
	}

	@Override
	public List<TaskEvent> getDisplayList() {
		return displayList;
	}

	@Override
	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	@Override
	public void setParser(Parser parser) {
		this.parser = parser;
	}

	@Override
	public void setSearcher(Searcher searcher) {
		this.searcher = searcher;
	}
}
