package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.ui.MainViewController;

public class AddFeedback implements UIFeedback {

	private TaskEvent taskEvent;
	
	public AddFeedback(TaskEvent taskEvent){
		this.taskEvent = taskEvent;
	}
	
	public TaskEvent getTaskEvent() {
        return taskEvent;
    }
	
	/* @@author A0124995R */
	
	@Override
	public void execute(MainViewController con) {
		con.showNotification("\""+taskEvent.getName() + "\" has been added.");
	}

	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof AddFeedback)) {
			return false;
		}
		AddFeedback other = (AddFeedback) t; 
		return this.taskEvent.equals(other.taskEvent);
	}

}
