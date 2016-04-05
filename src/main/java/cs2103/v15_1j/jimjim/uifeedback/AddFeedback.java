package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.ui.MainViewController;

public class AddFeedback implements UIFeedback {

	private TaskEvent taskEvent;

	//@@author A0139963N
	public AddFeedback(TaskEvent taskEvent){
		this.taskEvent = taskEvent;
	}

	public TaskEvent getTaskEvent() {
        return taskEvent;
    }
	
	//@@author A0139963N
	@Override
	public void execute(MainViewController con) {
		if(taskEvent instanceof Event){
			boolean clashes = con.addEvent((Event) taskEvent);
			if(!clashes){
				con.showNotification("\""+taskEvent.getName() + "\" has been added.");
			}
			else {
				con.showNotification("\""+taskEvent.getName() + "\" clashes with another event!");
			}
		}
		else {
			con.showNotification("\""+taskEvent.getName() + "\" has been added.");
		}

	}

	//@@author
	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof AddFeedback)) {
			return false;
		}
		AddFeedback other = (AddFeedback) t; 
		return this.taskEvent.equals(other.taskEvent);
	}

}
