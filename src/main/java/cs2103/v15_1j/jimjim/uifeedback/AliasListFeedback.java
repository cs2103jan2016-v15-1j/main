package cs2103.v15_1j.jimjim.uifeedback;

/* @@author A0124995R */

import java.util.Map;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class AliasListFeedback implements UIFeedback {
	Map<String, String> aliasList;

	public AliasListFeedback(Map<String, String> aliasList) {
		this.aliasList = aliasList;
	}

	public Map<String, String> getFeedbackList() {
		return aliasList;
	}

	@Override
	public void execute(MainViewController con) {
		con.showAliases(aliasList);
	}

	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof AliasAddFeedback)) {
			return false;
		}
		AliasListFeedback other = (AliasListFeedback) t;
		return this.aliasList.equals(other.aliasList);
	}
}
