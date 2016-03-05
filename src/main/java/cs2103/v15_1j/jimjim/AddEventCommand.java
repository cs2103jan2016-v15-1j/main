package cs2103.v15_1j.jimjim;

import java.time.LocalDateTime;
import java.util.List;

public class AddEventCommand implements Command {
    
    private Event event;
    
    public AddEventCommand(String name, LocalDateTime start, LocalDateTime end) {
        this.event = new Event(name, start, end);
    }
    
    public Event getEvent() {
        return this.event;
    }

    @Override
    public String undo(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String execute(List<TaskEvent> displayList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

}
