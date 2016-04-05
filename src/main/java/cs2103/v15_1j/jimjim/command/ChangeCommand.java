package cs2103.v15_1j.jimjim.command;

import java.time.LocalDate;
import java.time.LocalTime;
import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.uifeedback.ChangeFeedback;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class ChangeCommand implements UndoableCommand {

    private int taskNum;
    private char prefix;
    private String newName;
    private LocalDate newStartDate;
    private LocalTime newStartTime;
    private LocalDate newEndDate;
    private LocalTime newEndTime;
    private TaskEvent actual;
    private TaskEvent backup;
    
    public ChangeCommand(char prefix, int num, String newName, LocalDate newDate,
            LocalTime newTime, LocalDate newEndDate, LocalTime newEndTime) {
        this.taskNum = num;
        this.prefix = prefix;
        this.newName = newName;
        this.newStartDate = newDate;
        this.newStartTime = newTime;
        this.newEndDate = newEndDate;
        this.newEndTime = newEndTime;
    }
    
    public int getTaskNum() {
        return this.taskNum;
    }
    
    public char getPrefix() {
        return this.prefix;
    }
    
    public String getNewName() {
        return newName;
    }
    
    public LocalDate getNewStartDate() {
        return newStartDate;
    }
    
    public LocalTime getNewStartTime() {
        return newStartTime;
    }
    
    public LocalDate getNewEndDate() {
        return newEndDate;
    }
    
    public LocalTime getNewEndTime() {
        return newEndTime;
    }
    
    /* @@author A0124995R */
	@Override
	public UIFeedback undo(ControllerStates conStates) {
		TaskEvent temp = conStates.masterList.remove(actual);
		conStates.masterList.add(backup);

		if (conStates.storage.save(conStates.masterList)) {
			conStates.redoCommandHistory.push(this);
	        return new ChangeFeedback("Task/Event successfully changed back!");
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
			DeadlineTask deadlineTask = null;
            Event event = null;
			actual = conStates.displayList.getTaskEvent(taskNum-1, prefix);
			if (actual instanceof FloatingTask) {
				backup = new FloatingTask((FloatingTask) actual);
			} else if (actual instanceof DeadlineTask) {
				deadlineTask = (DeadlineTask) actual;
				backup = new DeadlineTask((DeadlineTask) actual);
			} else {
				event = (Event) actual;
				backup = new Event((Event) actual);
			}
            
            if (newName != null) {
            	actual.setName(newName);
            }
            if (newStartDate != null) {
            	if (actual instanceof DeadlineTask) {
            		deadlineTask.setDate(newStartDate);
            	} else if (actual instanceof Event) {
            		event.setStartDate(newStartDate);
            	}
            }
            if (newStartTime != null) {
            	if (actual instanceof DeadlineTask) {
            		deadlineTask.setTime(newStartTime);
            	} else if (actual instanceof Event) {
            		event.setStartTime(newStartTime);
            	}
            }
            if (newEndDate != null) {
            	if (actual instanceof Event) {
            		event.setEndDate(newEndDate);
            	}
            }
            if (newEndTime != null) {
            	if (actual instanceof Event) {
            		event.setEndTime(newEndTime);
            	}
            }
            
            if (actual instanceof Event) {
            	if (event.getEndDateTime().isBefore(event.getStartDateTime())) {
            		conStates.masterList.remove(actual);
            		conStates.masterList.add(backup);
            		return new FailureFeedback("Can't have the end datetime of an event before the start datetime!");
            	}
            }
            
            conStates.masterList.sort();
            
            if (conStates.storage.save(conStates.masterList)) {
            	conStates.undoCommandHistory.push(this);
                return new ChangeFeedback("Task/Event successfully changed!");
            } else {
            	conStates.redoCommandHistory.push(this);
            	conStates.masterList.remove(actual);
            	conStates.masterList.add(backup);
                return new FailureFeedback("Some error has occured. Please try again.");
            }
		} catch (IndexOutOfBoundsException e) {
			return new FailureFeedback(
                    "There is no item numbered " + this.prefix + this.taskNum);
		}
	}
}
