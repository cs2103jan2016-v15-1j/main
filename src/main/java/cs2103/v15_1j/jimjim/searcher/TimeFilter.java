package cs2103.v15_1j.jimjim.searcher;

/* @@author A0124995R */

import java.time.LocalTime;

import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;

public class TimeFilter implements Filter {
    
    LocalTime start;
    LocalTime end;
    
    public TimeFilter(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }
    
    public LocalTime getStart() {
        return start;
    }
    
    public LocalTime getEnd() {
        return end;
    }

    @Override
    public boolean check(TaskEvent taskEvent) {
        if (taskEvent instanceof FloatingTask) {
        	return false;
        } else if (taskEvent instanceof DeadlineTask) {
        	DeadlineTask deadlineTask = (DeadlineTask) taskEvent;
        	LocalTime deadline = deadlineTask.getDateTime().toLocalTime();
        	if ((start.compareTo(deadline) <= 0) && (end.compareTo(deadline) >= 0)) {
        		return true;
        	} else return false;
        } else {
        	Event event = (Event) taskEvent;
        	LocalTime startTime = event.getStartDateTime().toLocalTime();
    		LocalTime endTime = event.getEndDateTime().toLocalTime();
    		if ((start.compareTo(startTime) <= 0) && (end.compareTo(endTime) >= 0)) {
    			return true;
    		}
        	return false;
        }
    }

}
