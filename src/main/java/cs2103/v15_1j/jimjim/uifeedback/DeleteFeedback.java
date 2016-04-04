package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.ui.MainViewController;

public class DeleteFeedback implements UIFeedback {

    private TaskEvent taskEvent;
	
	public DeleteFeedback(TaskEvent taskEvent){
	    this.taskEvent = taskEvent;
	}
	
	public TaskEvent getTaskEvent() {
        return taskEvent;
    }
	
	/* @@author A0124995R */
	
	@Override
	public void execute(MainViewController con) {
		con.showNotification("\""+taskEvent.getName() + "\" has been deleted.");
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
