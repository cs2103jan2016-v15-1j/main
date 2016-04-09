package cs2103.v15_1j.jolt.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.uifeedback.AliasListFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class AliasListCommand implements Command {

	/* @@author A0124995R */
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
