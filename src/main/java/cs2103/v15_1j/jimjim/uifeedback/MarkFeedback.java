package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.ui.MainViewController;

public class MarkFeedback implements UIFeedback {

    private Task task;
	
	public MarkFeedback(Task task){
		this.task = task;
	}
	
	public Task getTask() {
        return task;
    }
	
	@Override
	public void execute(MainViewController con) {

		con.showNotification("\""+task.getName() + "\" has been completed.");
	}
	
	@Override
	public boolean equals(Object t) {
		MarkFeedback other = (MarkFeedback) t;
		return this.task.equals(other.task);
	}
}
