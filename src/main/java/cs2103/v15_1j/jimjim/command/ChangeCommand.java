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
    
    public LocalDate getNewDate() {
        return newStartDate;
    }
    
    public LocalTime getNewTime() {
        return newStartTime;
    }
    
    public LocalDate getNewEndDate() {
        return newEndDate;
    }
    
    public LocalTime getNewEndTime() {
        return newEndTime;
    }
    
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
			actual = conStates.displayList.getTaskEvent(taskNum-1, prefix);
			if (actual instanceof FloatingTask) {
				backup = new FloatingTask((FloatingTask) actual);
			} else if (actual instanceof DeadlineTask) {
				backup = new DeadlineTask((DeadlineTask) actual);
			} else {
				backup = new Event((Event) actual);
			}
            DeadlineTask tempDeadlineTask = null;
            Event tempEvent = null;
            if (actual instanceof DeadlineTask) {
            	tempDeadlineTask = (DeadlineTask) actual;
            } else if (actual instanceof Event) {
            	tempEvent = (Event) actual;
            }
            
            if (newName != null) {
            	actual.setName(newName);
            }
            if (newStartDate != null) {
            	if (actual instanceof DeadlineTask) {
            		tempDeadlineTask.setDate(newStartDate);
            	} else if (actual instanceof Event) {
            		tempEvent.setStartDate(newStartDate);
            	}
            }
            if (newStartTime != null) {
            	if (actual instanceof DeadlineTask) {
            		tempDeadlineTask.setTime(newStartTime);
            	} else if (actual instanceof Event) {
            		tempEvent.setStartTime(newStartTime);
            	}
            }
            if (newEndDate != null) {
            	if (actual instanceof Event) {
            		tempEvent.setEndDate(newEndDate);
            	}
            }
            if (newEndTime != null) {
            	if (actual instanceof Event) {
            		tempEvent.setEndTime(newEndTime);
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
