package cs2103.v15_1j.jimjim.uifeedback;

/* @@author A0124995R */

import java.util.Map;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class AliasListFeedback implements UIFeedback {
	Map<String, String> feedbackList;
	
	public AliasListFeedback(Map<String, String> feedbackList) {
		this.feedbackList = feedbackList;
	}
	
	public Map<String, String> getFeedbackList() {
		return feedbackList;
	}
	
	@Override
	public void execute(MainViewController con) {
		// TODO
	}
	
	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof AliasAddFeedback)) {
			return false;
		}
		AliasListFeedback other = (AliasListFeedback) t; 
		return this.feedbackList.equals(other.feedbackList);
	}
}
