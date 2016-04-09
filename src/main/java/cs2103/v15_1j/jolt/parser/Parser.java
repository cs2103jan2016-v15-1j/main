package cs2103.v15_1j.jolt.parser;

import java.util.Map;

import cs2103.v15_1j.jolt.command.Command;

public interface Parser {
	public Command parse(String userCommand);

	void setAliases(Map<String, Integer> aliases);

	String getKeywordString(int keywordInt);
}
