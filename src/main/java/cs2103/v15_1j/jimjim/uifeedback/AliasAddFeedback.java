package cs2103.v15_1j.jimjim.uifeedback;

import cs2103.v15_1j.jimjim.ui.MainViewController;

public class AliasAddFeedback implements UIFeedback {
	private String alias;
	private String keywordString = null;
	
	public AliasAddFeedback(String alias) {
		this.alias = alias;
	}
	
	public AliasAddFeedback(String alias, String keywordString) {
		this(alias);
		this.keywordString = keywordString;
	}
	
	
	public String getAlias() {
		return alias;
	}
	
	public String getKeywordString() {
		return keywordString;
	}
	
	@Override
	public void execute(MainViewController con) {
		if (keywordString == null) {
			con.showNotification("\""+ alias + "\" has been added as an alias.");
		} else {
			con.showNotification("\""+ alias + "\" has been added as an alias for " + keywordString + ".");	
		}
	}
	
	@Override
	public boolean equals(Object t) {
		if (t == null || !(t instanceof AliasAddFeedback)) {
			return false;
		}
		AliasAddFeedback other = (AliasAddFeedback) t; 
		return this.keywordString.equals(other.keywordString);
	}
}
