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
	
	@Override
	public String execute(MainViewController con) {
		// TODO Auto-generated method stub
		return null;
	}

}
