package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class AliasDeleteCommand implements UndoableCommand {
	
    private String alias;

    public AliasDeleteCommand(String alias) {
        this.alias = alias;
    }
    
    public String getAlias() {
        return alias;
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
