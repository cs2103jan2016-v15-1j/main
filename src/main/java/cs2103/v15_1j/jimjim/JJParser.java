package cs2103.v15_1j.jimjim;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import cs2103.v15_1j.jimjim.antlr4.*;

public class JJParser implements Parser {

	@Override
	public Command parse(String userCommand) {
		UserCommandLexer lexer =
				new UserCommandLexer(new ANTLRInputStream(userCommand));
		UserCommandParser parser =
				new UserCommandParser(new CommonTokenStream(lexer));
		ParseTree tree = parser.cmd();
		JJCommandVisitor visitor = new JJCommandVisitor(userCommand);
		try {
			return visitor.visit(tree);
		} catch (RuntimeException e) {
			if (e.getMessage() == null) {
				return new InvalidCommand("This feature is not yet implemented.");
			} else {
				return new InvalidCommand(e.getMessage());
			}
		}
	}

}
