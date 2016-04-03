package cs2103.v15_1j.jimjim.uifeedback;

import java.util.Map;
import java.util.Map.Entry;

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
		for (Entry<String, String> mapping : feedbackList.entrySet()) {
			con.showNotification("\""+ mapping.getKey() + "\" has been added as an alias for "
								 + mapping.getValue() + ".");	
		}	
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
