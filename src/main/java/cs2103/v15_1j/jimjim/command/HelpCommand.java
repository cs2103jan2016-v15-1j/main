package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.HelpFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class HelpCommand implements Command {
    @Override
    public UIFeedback execute(ControllerStates conStates) {
    	conStates.resetRedoHistory();
        return new HelpFeedback();
    }

}
