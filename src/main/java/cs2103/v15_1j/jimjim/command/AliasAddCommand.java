package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.AliasAddFeedback;
import cs2103.v15_1j.jimjim.uifeedback.AliasDeleteFeedback;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class AliasAddCommand implements UndoableCommand {
	
    private String alias;
    private int keyword;    // the index of the token understood by the parser
    private String keywordString;   // the string the user typed for keyword
                                    // used for feedback purpose

    public AliasAddCommand(String alias, int keyword, String keywordString) {
        this.alias = alias;
        this.keyword = keyword;
        this.keywordString = keywordString;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public int getKeyword() {
        return keyword;
    }
    
    public String getKeywordString() {
        return keywordString;
    }

    @Override
    public UIFeedback undo(ControllerStates conStates) {
    	conStates.config.aliases.remove(alias, keyword);
    	
	    if (conStates.storage.saveConfig(conStates.config)) {
	    	conStates.redoCommandHistory.push(this);
	        return new AliasDeleteFeedback(alias);
	    } else {
	        // If conStates.storage fails to save list
	        // add alias back to config
	        conStates.config.aliases.put(alias, keyword);
	        conStates.undoCommandHistory.push(this);
	        return new FailureFeedback("Some error has occured. Please try again.");
	    }
    }

    @Override
    public UIFeedback execute(ControllerStates conStates) {
    	conStates.config.aliases.put(alias, keyword);
    	
        if (conStates.storage.saveConfig(conStates.config)) {
        	conStates.undoCommandHistory.push(this);
        	return new AliasAddFeedback(alias, keywordString);
        } else {
            // If conStates.storage fails to save config
            // remove alias
        	conStates.redoCommandHistory.push(this);
            conStates.config.aliases.remove(alias, keyword);
            return new FailureFeedback("Some error has occured. Please try again.");
        }
    }
}
