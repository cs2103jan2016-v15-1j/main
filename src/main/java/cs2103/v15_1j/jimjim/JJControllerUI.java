package cs2103.v15_1j.jimjim;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JJControllerUI implements Controller {
	
	private ObservableList<TaskEvent> displayList = FXCollections.observableArrayList();
	private Parser parser;
	private Searcher searcher;
	private Storage storage;
	
	@Override
	public String execute(String userCommand) {
		displayList.add(new Task(userCommand,LocalDateTime.of(2001, 02, 02, 0,10)));
		displayList.add(new Event(userCommand,LocalDateTime.of(2001, 02, 03, 0,0), LocalDateTime.of(2001, 02, 04, 0,0)));
		return "Success";
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
