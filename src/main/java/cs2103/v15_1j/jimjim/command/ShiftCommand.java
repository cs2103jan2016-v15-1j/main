package cs2103.v15_1j.jimjim.command;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.uifeedback.ChangeFeedback;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.ShiftFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class ShiftCommand implements UndoableCommand {
    
    private int taskNum;
    private char prefix;
    private LocalDate newDate;
    private LocalTime newTime;
    private TaskEvent actual;
    private Event backup;
    
    public ShiftCommand(char prefix, int taskNum, LocalDate date, LocalTime time) {
        this.prefix = prefix;
        this.taskNum = taskNum;
        this.newDate = date;
        this.newTime = time;
    }
    
    public int getTaskNum() {
        return taskNum;
    }
    
    public char getPrefix() {
        return prefix;
    }
    
    public LocalDate getNewDate() {
        return newDate;
    }
    
    public LocalTime getNewTime() {
        return newTime;
    }
    
    public TaskEvent getActual() {
    	return actual;
    }
    
    public Event getBackup() {
    	return backup;
    }
    
    @Override
    public UIFeedback undo(ControllerStates conStates) {
		TaskEvent temp = conStates.masterList.remove(actual);
		conStates.masterList.add(backup);

		if (conStates.storage.save(conStates.masterList)) {
			conStates.redoCommandHistory.push(this);
	        return new ShiftFeedback("Event successfully changed back!");
	    } else {
	        conStates.masterList.remove(backup);
	        conStates.masterList.add(temp);
	        conStates.undoCommandHistory.push(this);
	        return new FailureFeedback("Some error has occured. Please try again.");
	    }	
    }

    @Override
    public UIFeedback execute(ControllerStates conStates) {
        try {
        	actual = conStates.displayList.getTaskEvent(taskNum-1, prefix);
            Event tempEvent = null;
        	if (actual instanceof FloatingTask || actual instanceof DeadlineTask) {
        		return new FailureFeedback("Can't shift a task!");	
			} else {
				tempEvent = (Event) actual;
				backup = new Event(tempEvent);
				LocalDateTime currentStartDateTime = tempEvent.getStartDateTime();
				LocalDateTime currentEndDateTime = tempEvent.getEndDateTime();
				
				LocalDateTime newStartDateTime = null;
				if (newDate != null && newTime != null) {
					newStartDateTime = LocalDateTime.of(newDate, newTime);
				} else if (newDate != null && newTime == null) {
					newStartDateTime = LocalDateTime.of(newDate, currentStartDateTime.toLocalTime());
				} else if (newDate == null & newTime != null) {
					newStartDateTime = LocalDateTime.of(currentStartDateTime.toLocalDate(), newTime);
				}
				long diffInSeconds = Duration.between(currentStartDateTime, currentEndDateTime).getSeconds();
				LocalDateTime newEndDateTime = newStartDateTime.plusSeconds(diffInSeconds);
				
				tempEvent.setStartDateTime(newStartDateTime);
				tempEvent.setEndDateTime(newEndDateTime);
				
	            if (conStates.storage.save(conStates.masterList)) {
	            	conStates.undoCommandHistory.push(this);
	                return new ShiftFeedback("Event date/time successfully changed!");
	            } else {
	            	conStates.redoCommandHistory.push(this);
	            	conStates.masterList.remove(actual);
	            	conStates.masterList.add(backup);
	                return new FailureFeedback("Some error has occured. Please try again.");
	            }
			}
		} catch (IndexOutOfBoundsException e) {
			return new FailureFeedback("There is no item numbered " + this.prefix + this.taskNum);
		}
    }

}
