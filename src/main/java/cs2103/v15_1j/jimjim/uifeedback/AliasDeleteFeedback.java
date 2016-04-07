package cs2103.v15_1j.jimjim.uifeedback;

/* @@author A0124995R */

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class AliasDeleteFeedback implements UIFeedback {
	private String alias;

	public AliasDeleteFeedback(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	@Override
	public void execute(MainViewController con) {
		con.showNotification("\"" + alias + "\" has been deleted as an alias.");
	}

	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof AliasAddFeedback)) {
			return false;
		}
		AliasDeleteFeedback other = (AliasDeleteFeedback) t;
		return this.alias.equals(other.alias);
	}
}
