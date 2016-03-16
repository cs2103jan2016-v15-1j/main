package cs2103.v15_1j.jimjim.command;

import java.time.LocalDateTime;
import java.util.List;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.model.Event;

public class AddEventCommand implements Command {
    
    private Event event;
    
    public AddEventCommand(String name, LocalDateTime start, LocalDateTime end) {
        this.event = new Event(name, start, end);
    }
    
    public Event getEvent() {
        return this.event;
    }

    @Override
    public String undo(List<TaskEvent> displayList, List<TaskEvent> list, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String execute(List<TaskEvent> displayList, List<TaskEvent> list, Storage storage, Searcher searcher) {
        displayList.add(event);
        list.add(event);
        
        if (storage.save(list)) {
        	return "Event added";
        } else {
	    	// If storage fails to save list
	    	// remove task from list and displayList
	    	list.remove(event);
	    	displayList.remove(event);
	    	return "Some error has occured. Please try again.";	
        }
    }

}
