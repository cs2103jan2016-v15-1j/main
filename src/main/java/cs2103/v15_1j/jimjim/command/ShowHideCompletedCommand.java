package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.ShowHideCompletedFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class ShowHideCompletedCommand implements Command {
    
    private boolean willShow;
    
    public ShowHideCompletedCommand(boolean willShow) {
        this.willShow = willShow;
    }
    
    public boolean getWillShow() {
        return this.willShow;
    }

    @Override
    public UIFeedback execute(ControllerStates conStates) {
    	return new ShowHideCompletedFeedback(willShow);
    }

}
