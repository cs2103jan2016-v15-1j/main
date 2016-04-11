package cs2103.v15_1j.jolt.command;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;
import cs2103.v15_1j.jolt.model.TaskEvent;
import cs2103.v15_1j.jolt.uifeedback.AddFeedback;
import cs2103.v15_1j.jolt.uifeedback.DeleteFeedback;
import cs2103.v15_1j.jolt.uifeedback.FailureFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class AddCommand implements UndoableCommand {
	private TaskEvent taskEvent;

	public AddCommand(String name, LocalDateTime datetime) {
		this.taskEvent = new DeadlineTask(name, datetime);
	}

	public AddCommand(String name) {
		this.taskEvent = new FloatingTask(name);
	}

	public AddCommand(String name, LocalDateTime start, LocalDateTime end) {
		this.taskEvent = new Event(name, start, end);
	}
	
	public AddCommand(String name, LocalDate date) {
		this.taskEvent = new Event(name, date);
	}

	/* @@author A0124995R */
	@Override
	public UIFeedback undo(ControllerStates conStates) {
		conStates.masterList.remove(taskEvent);

		if (conStates.storage.save(conStates.masterList)) {
			conStates.redoCommandHistory.push(this);
			return new DeleteFeedback(taskEvent);
		} else {
			// If conStates.storage fails to save list
			// add task/event back to conStates.masterList and displayList
			conStates.masterList.add(taskEvent);
			conStates.undoCommandHistory.push(this);
			return new FailureFeedback("Some error has occured. Please try again.");
		}
	}

	@Override
	public UIFeedback execute(ControllerStates conStates) {
		if(conStates.masterList.contains(taskEvent)){
			return new FailureFeedback("Task/Event already exists.");
		}
		
		conStates.masterList.add(taskEvent);

		if (conStates.storage.save(conStates.masterList)) {
			conStates.undoCommandHistory.push(this);
			return new AddFeedback(taskEvent);
		} else {
			// If conStates.storage fails to save list
			// remove task
			conStates.redoCommandHistory.push(this);
			conStates.masterList.remove(taskEvent);
			return new FailureFeedback("Some error has occured. Please try again.");
		}
	}

	public TaskEvent getTaskEvent() {
		return taskEvent;
	}

}
