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
	public String execute(MainViewController con) {
		// TODO Auto-generated method stub
		return null;
	}

}
