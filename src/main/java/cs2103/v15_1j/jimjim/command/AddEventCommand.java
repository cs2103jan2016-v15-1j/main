package cs2103.v15_1j.jimjim.command;

import java.time.LocalDateTime;

import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.DataLists;
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
    public String undo(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String execute(DataLists displayList, DataLists masterList, Storage storage, Searcher searcher) {
        displayList.getEventsList().add(event);
        masterList.getEventsList().add(event);
        
        if (storage.save(masterList)) {
        	return "Event added";
        } else {
	    	// If storage fails to save list
	    	// remove task from list and displayList
	    	masterList.getEventsList().remove(event);
	    	displayList.getEventsList().remove(event);
	    	return "Some error has occured. Please try again.";	
        }
    }

}
