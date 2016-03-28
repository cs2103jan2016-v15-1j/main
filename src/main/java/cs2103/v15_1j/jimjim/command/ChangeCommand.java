package cs2103.v15_1j.jimjim.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Stack;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
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
	public UIFeedback undo(DataLists searchResultsList, DataLists masterList, Storage storage, Searcher searcher,
			Stack<UndoableCommand> undoCommandHistory, Stack<UndoableCommand> redoCommandHistory) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public UIFeedback execute(DataLists searchResultsList, DataLists masterList, 
							  Storage storage, Searcher searcher, Stack<UndoableCommand> undoCommandHistory,
							  Stack<UndoableCommand> redoCommandHistory) {
		try {
			backup = masterList.getTaskEvent(taskNum, prefix);
            DeadlineTask tempDeadlineTask = null;
            Event tempEvent = null;
            if (backup instanceof DeadlineTask) {
            	tempDeadlineTask = (DeadlineTask) backup;
            } else if (backup instanceof Event) {
            	tempEvent = (Event) backup;
            }
            
            if (newName != null) {
            	backup.setName(newName);
            }
            if (newStartDate != null) {
            	if (backup instanceof DeadlineTask) {
            		tempDeadlineTask.setDate(newStartDate);
            	} else if (backup instanceof Event) {
            		EventTime currentEventTime = tempEvent.getDateTimes().get(0);
            		currentEventTime.setStartDate(newStartDate);
            	}
            }
            if (newStartTime != null) {
            	if (backup instanceof DeadlineTask) {
            		tempDeadlineTask.setTime(newStartTime);
            	} else if (backup instanceof Event) {
            		EventTime currentEventTime = tempEvent.getDateTimes().get(0);
            		currentEventTime.setStartTime(newStartTime);
            	}
            }
            if (newEndDate != null) {
            	if (backup instanceof Event) {
            		EventTime currentEventTime = tempEvent.getDateTimes().get(0);
            		currentEventTime.setEndDate(newEndDate);
            	}
            }
            if (newEndTime != null) {
            	if (backup instanceof Event) {
            		EventTime currentEventTime = tempEvent.getDateTimes().get(0);
            		currentEventTime.setEndTime(newEndTime);
            	}
            }
		} catch (IndexOutOfBoundsException e) {
			return new FailureFeedback(
                    "There is no item numbered " + this.prefix + this.taskNum);
		}
		return null;
	}
}
