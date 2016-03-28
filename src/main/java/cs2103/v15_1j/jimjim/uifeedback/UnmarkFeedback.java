package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.ui.MainViewController;

public class UnmarkFeedback implements UIFeedback {

    private Task task;
	
	public UnmarkFeedback(Task task){
		this.task = task;
	}
	
	public Task getTask() {
        return task;
    }
	
	@Override
	public void execute(MainViewController con) {
		con.showNotification("\""+task.getName() + "\" has been marked as not completed.");
	}
	
	public boolean equals(UnmarkFeedback other) {
		return this.task.equals(other.task);
	}
}
