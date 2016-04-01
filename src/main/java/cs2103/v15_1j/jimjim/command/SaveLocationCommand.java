package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class SaveLocationCommand implements Command {
    
    String savePath;
    
    public SaveLocationCommand(String savePath) {
        this.savePath = savePath;
    }
    
    public String getSavePath() {
        return savePath;
    }

    @Override
    public UIFeedback execute(ControllerStates conStates) {
        // TODO Auto-generated method stub
        return null;
    }

}
