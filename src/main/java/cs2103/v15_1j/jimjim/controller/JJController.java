package cs2103.v15_1j.jimjim.controller;

import java.util.Collections;

import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.parser.Parser;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class JJController implements Controller {

    private DataLists displayList;
    private DataLists masterList;
    private Parser parser;
    private Searcher searcher;
    private Storage storage;

    @Override
    public String execute(String userCommand) {
        assert userCommand != null;
        Command command = parser.parse(userCommand);
        assert command != null;
        return command.execute(displayList, masterList, storage, searcher);
    }

    @Override
    public DataLists getDisplayList() {
        return displayList;
    }

    @Override
    public void setStorage(Storage storage) {
        this.storage = storage;
        this.masterList = storage.load();
        this.displayList = new DataLists();
        Collections.copy(displayList.getEventsList(), masterList.getEventsList());
        Collections.copy(displayList.getTasksList(),
                masterList.getTasksList());
        Collections.copy(displayList.getDeadlineTasksList(), masterList.getDeadlineTasksList());
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
