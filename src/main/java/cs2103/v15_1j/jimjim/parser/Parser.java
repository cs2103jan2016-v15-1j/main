package cs2103.v15_1j.jimjim.parser;

import cs2103.v15_1j.jimjim.command.Command;

public interface Parser {
	public Command parse(String userCommand);
}
