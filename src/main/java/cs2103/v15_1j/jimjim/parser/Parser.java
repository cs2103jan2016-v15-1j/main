package cs2103.v15_1j.jimjim.parser;

import java.util.Map;

import cs2103.v15_1j.jimjim.command.Command;

public interface Parser {
	public Command parse(String userCommand);

    void setAliases(Map<String, Integer> aliases);

    String getKeywordString(int keywordInt);
}
