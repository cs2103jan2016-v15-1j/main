package cs2103.v15_1j.jimjim.command;

import java.io.File;

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
    	File file = null;
    	
    	if (savePath.contains(" ")) {
    		return new FailureFeedback("Invalid file path specified!");
    	}
    	
    	try {
    		file = new File(savePath);
            if (!file.exists()) {
            	File parent = file.getParentFile();
            	if (parent != null && !parent.exists()) {
            		parent.mkdirs();
            	}
            	file.createNewFile();
            }
    	} catch (Exception e) {
    		return new FailureFeedback("Invalid file path specified!");
    	}
    	
    	conStates.config.savePath = savePath;
    	conStates.storage.setSaveFile(savePath);
    	conStates.storage.save(conStates.masterList);
    	
    	if (conStates.storage.saveConfig(conStates.config)) {
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
