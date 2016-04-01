package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
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
    public UIFeedback execute(ControllerStates conStates) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UIFeedback undo(ControllerStates conStates) {
        // TODO Auto-generated method stub
        return null;
    }

}
