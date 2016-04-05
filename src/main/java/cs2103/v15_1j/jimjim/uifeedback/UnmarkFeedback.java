package cs2103.v15_1j.jimjim.uifeedback;

/* @@author A0124995R */

import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.ui.MainViewController;

public class UnmarkFeedback implements UIFeedback {

    private TaskEvent task;
	
	public UnmarkFeedback(TaskEvent task){
		this.task = task;
	}
	
	public TaskEvent getTask() {
        return task;
    }
	
	@Override
	public void execute(MainViewController con) {
		con.showNotification("\""+task.getName() + "\" has been marked as not completed.");
	}
	
	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof UnmarkFeedback)) {
			return false;
		}
		UnmarkFeedback other = (UnmarkFeedback) t;
		return this.task.equals(other.task);
	}
}
