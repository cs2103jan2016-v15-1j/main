package cs2103.v15_1j.jimjim.controller;

import java.util.ArrayList;
import java.util.List;

import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.parser.Parser;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;

public class JJController implements Controller {

    private List<TaskEvent> displayList = new ArrayList<TaskEvent>();
    private List<TaskEvent> list = new ArrayList<TaskEvent>();
    private Parser parser;
    private Searcher searcher;
    private Storage storage;

    @Override
    public String execute(String userCommand) {
        assert userCommand != null;
        Command command = parser.parse(userCommand);
        assert command != null;
        return command.execute(displayList, list, storage, searcher);
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