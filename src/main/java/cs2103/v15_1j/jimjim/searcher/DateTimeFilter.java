package cs2103.v15_1j.jimjim.searcher;

/* @@author A0124995R */
import java.time.LocalDateTime;

import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;

public class DateTimeFilter implements Filter {
    
    LocalDateTime start;
    LocalDateTime end;
    
    public DateTimeFilter(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
    
    public LocalDateTime getStart() {
        return start;
    }
    
    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public boolean check(TaskEvent taskEvent) {
        if (taskEvent instanceof FloatingTask) {
        	return false; // floating tasks have no datetime
        } else if (taskEvent instanceof DeadlineTask) {
        	DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
        	LocalDateTime deadline = deadlineTask.getDateTime();
        	if ((start.compareTo(deadline) <= 0 ) && (end.compareTo(deadline) >= 0)) {
        		return true;
        	} else return false;
        } else {
        	Event event = (Event) taskEvent;
        	LocalDateTime eventStart = event.getStartDateTime();
    		LocalDateTime eventEnd = event.getEndDateTime();
    		if ((start.compareTo(eventStart) <= 0) && (end.compareTo(eventEnd) >= 0)) {
    			return true;
    		}
        	return false;
        }
    }

}
