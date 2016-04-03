package cs2103.v15_1j.jimjim.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.AliasListFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class AliasListCommand implements Command {

    @Override
    public UIFeedback execute(ControllerStates conStates) {
    	Map<String, Integer> mappings = conStates.config.aliases;
    	Map<String, String> feedbackList = new HashMap<String, String>();
    	
    	for (Entry<String, Integer> entry : mappings.entrySet()) {
    		feedbackList.put(entry.getKey(), conStates.parser.getKeywordString(entry.getValue()));
    	}
        return new AliasListFeedback(feedbackList);
    }

}
