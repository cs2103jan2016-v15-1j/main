package cs2103.v15_1j.jolt.command;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;
import cs2103.v15_1j.jolt.model.TaskEvent;
import cs2103.v15_1j.jolt.uifeedback.FailureFeedback;
import cs2103.v15_1j.jolt.uifeedback.ShiftFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

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

	/* @@author A0124995R */
	@Override
	public UIFeedback undo(ControllerStates conStates) {
		TaskEvent temp = conStates.masterList.remove(actual);
		conStates.masterList.add(backup);

		if (conStates.storage.save(conStates.masterList)) {
			conStates.redoCommandHistory.push(this);
			return new ShiftFeedback(backup.getName(), backup.getStartDateTime(), backup.getEndDateTime());
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
        		return new FailureFeedback("Invalid syntax for shifting " + prefix + taskNum);	
			} else {
				tempEvent = (Event) actual;
				backup = new Event(tempEvent);
				LocalDateTime currentStartDateTime = tempEvent.getStartDateTime();
				LocalDateTime currentEndDateTime = tempEvent.getEndDateTime();

				LocalDateTime newStartDateTime = currentStartDateTime;
				if (newDate != null) {
					newStartDateTime = newStartDateTime.with(newDate);
				}
				if (newTime != null) {
					newStartDateTime = newStartDateTime.with(newTime);
				}

				long diffInSeconds = Duration.between(currentStartDateTime, currentEndDateTime).getSeconds();
				LocalDateTime newEndDateTime = newStartDateTime.plusSeconds(diffInSeconds);

				tempEvent.setStartDateTime(newStartDateTime);
				tempEvent.setEndDateTime(newEndDateTime);

				if (conStates.storage.save(conStates.masterList)) {
					conStates.undoCommandHistory.push(this);
					return new ShiftFeedback(tempEvent.getName(), newStartDateTime, newEndDateTime);
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
