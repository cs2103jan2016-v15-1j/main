package cs2103.v15_1j.jolt.command;

import cs2103.v15_1j.jolt.controller.ControllerStates;
import cs2103.v15_1j.jolt.uifeedback.HelpFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class HelpCommand implements Command {

	private String page;

	public HelpCommand(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}

	@Override
	public UIFeedback execute(ControllerStates conStates) {
		return new HelpFeedback(page);
	}

}
