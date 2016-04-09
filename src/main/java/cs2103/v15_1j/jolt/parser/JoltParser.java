//@@author A0131140E
package cs2103.v15_1j.jolt.parser;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import cs2103.v15_1j.jolt.antlr4.*;
import cs2103.v15_1j.jolt.command.Command;
import cs2103.v15_1j.jolt.command.InvalidCommand;

public class JoltParser implements Parser {

	private static final Logger logger = Logger.getLogger(JoltParser.class.getName());
	private Map<String, Integer> aliases = null;

	@Override
	public void setAliases(Map<String, Integer> aliases) {
		this.aliases = aliases;
	}

	@Override
	public Command parse(String userCommand) {
		assert userCommand != null;
		logger.entering("JJParser", "parse", userCommand);
		UserCommandLexer lexer = new UserCommandLexer(new ANTLRInputStream(userCommand));
		lexer.setAliases(aliases);
		UserCommandParser parser = new UserCommandParser(new CommonTokenStream(lexer));
		ParseTree tree = parser.cmd();
		JoltCommandVisitor visitor = new JoltCommandVisitor(userCommand);
		try {
			return visitor.visit(tree);
		} catch (RuntimeException e) {
			logger.log(Level.INFO, "Exception parsing \"{0}\": {1}", new Object[] { userCommand, e });
			if (e.getMessage() == null) {
				return new InvalidCommand("This feature is not yet implemented.");
			} else {
				return new InvalidCommand(e.getMessage());
			}
		}
	}

	@Override
	public String getKeywordString(int keywordInt) {
		return UserCommandLexer.VOCABULARY.getDisplayName(keywordInt).toLowerCase();
	}

}
