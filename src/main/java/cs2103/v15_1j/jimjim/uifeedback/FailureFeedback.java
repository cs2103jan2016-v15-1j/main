package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class FailureFeedback implements UIFeedback {

	String message = "";
	
	public FailureFeedback(String message){
		this.message = message;
	}
	
	public String getMessage() {
        return message;
    }

    @Override
    public void execute(MainViewController con) {
		con.showNotification(message);
    }
    
    /* @@author A0124995R */
    
    @Override
    public boolean equals(Object t) {
    	if (t == null || !(t instanceof FailureFeedback)) {
			return false;
		}
    	FailureFeedback other = (FailureFeedback) t;
		return this.message.equals(other.message);
	}
}
