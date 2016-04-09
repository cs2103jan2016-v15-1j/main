package cs2103.v15_1j.jimjim.command;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.AliasAddFeedback;
import cs2103.v15_1j.jimjim.uifeedback.AliasDeleteFeedback;
import cs2103.v15_1j.jimjim.uifeedback.FailureFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class AliasDeleteCommand implements UndoableCommand {
	private String alias;
	/* @@author A0124995R */
	private Integer keyword;
	/* @@author */

	public AliasDeleteCommand(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public int getKeyword() {
		return keyword;
	}

	/* @@author A0124995R */
	@Override
	public UIFeedback undo(ControllerStates conStates) {
		conStates.config.aliases.put(alias, keyword);

		if (conStates.storage.saveConfig(conStates.config)) {
			conStates.redoCommandHistory.push(this);
			return new AliasAddFeedback(alias);
		} else {
			// If conStates.storage fails to save list
			// add alias back to config
			conStates.config.aliases.remove(alias, keyword);
			conStates.undoCommandHistory.push(this);
			return new FailureFeedback("Some error has occured. Please try again.");
		}
	}

	@Override
	public UIFeedback execute(ControllerStates conStates) {
		keyword = conStates.config.aliases.remove(alias);
		if (keyword == null) {
			return new FailureFeedback("Alias specified for deletion does not exist");
		}
		if (conStates.storage.saveConfig(conStates.config)) {
			conStates.undoCommandHistory.push(this);
			return new AliasDeleteFeedback(alias);
		} else {
			// If conStates.storage fails to save config
			// remove alias
			conStates.redoCommandHistory.push(this);
			conStates.config.aliases.put(alias, keyword);
			return new FailureFeedback("Some error has occured. Please try again.");
		}
	}
}
