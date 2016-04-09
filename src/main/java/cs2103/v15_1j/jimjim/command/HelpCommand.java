package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.HelpFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

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
