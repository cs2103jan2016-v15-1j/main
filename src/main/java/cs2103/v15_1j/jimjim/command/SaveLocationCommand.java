package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.SaveLocationFeedback;
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
    	String backup = conStates.config.savePath;
    	conStates.config.savePath = savePath;
    	
    	if (conStates.storage.saveConfig(conStates.config)) {
    		conStates.storage.setSaveFile(savePath);
        	return new SaveLocationFeedback(savePath);
        } else {
            // If conStates.storage fails to save config
        	// set savePath back to previous value
            conStates.config.savePath = backup;
            conStates.storage.setSaveFile(backup);
            return new FailureFeedback("Some error has occured. Please try again.");
        }
    }

}
