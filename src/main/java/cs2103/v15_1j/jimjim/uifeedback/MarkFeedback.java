package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.ui.MainViewController;

public class MarkFeedback implements UIFeedback {

    private TaskEvent task;
	
	public MarkFeedback(TaskEvent task){
		this.task = task;
	}
	
	public TaskEvent getTask() {
        return task;
    }
	
	/* @@author A0124995R */
	
	@Override
	public void execute(MainViewController con) {

		con.showNotification("\""+task.getName() + "\" has been completed.");
	}
	
	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof MarkFeedback)) {
			return false;
		}
		MarkFeedback other = (MarkFeedback) t;
		return this.task.equals(other.task);
	}
}
