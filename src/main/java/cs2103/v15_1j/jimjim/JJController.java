package cs2103.v15_1j.jimjim;

import java.util.ArrayList;
import java.util.List;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JJController implements Controller {
	
	private ObservableList<TaskEvent> displayList = FXCollections.observableArrayList();
	private List<TaskEvent> list = new ArrayList<TaskEvent>();
	private Parser parser;
	private Searcher searcher;
	private Storage storage;

	@Override
	public String execute(String userCommand) {
		Command command = parser.parse(userCommand);
		return command.execute(displayList, list, storage, searcher);
	}

	@Override
	public ObservableList<TaskEvent> getDisplayList() {
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
