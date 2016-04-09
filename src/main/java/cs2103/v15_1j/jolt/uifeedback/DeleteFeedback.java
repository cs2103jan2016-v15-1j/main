package cs2103.v15_1j.jolt.uifeedback;

import cs2103.v15_1j.jolt.model.TaskEvent;
import cs2103.v15_1j.jolt.ui.MainViewController;

public class DeleteFeedback implements UIFeedback {

	private TaskEvent taskEvent;

	public DeleteFeedback(TaskEvent taskEvent) {
		this.taskEvent = taskEvent;
	}

	public TaskEvent getTaskEvent() {
		return taskEvent;
	}

	/* @@author A0124995R */

	@Override
	public void execute(MainViewController con) {
		con.showNotification("\"" + taskEvent.getName() + "\" has been deleted.");
	}

	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof DeleteFeedback)) {
			return false;
		}
		DeleteFeedback other = (DeleteFeedback) t;
		return this.taskEvent.equals(other.taskEvent);
	}
}
