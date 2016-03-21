package cs2103.v15_1j.jimjim.searcher;

import java.time.LocalDateTime;
import java.util.List;

import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
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
        	List<EventTime> eventTimes = event.getDateTimes();
        	for (EventTime eventTime : eventTimes) {
        		LocalDateTime eventStart = eventTime.getStartDateTime();
        		LocalDateTime eventEnd = eventTime.getEndDateTime();
        		if ((start.compareTo(eventStart) <= 0) && (end.compareTo(eventEnd) >= 0)) {
        			return true;
        		}
        	}
        	return false;
        }
    }

}
