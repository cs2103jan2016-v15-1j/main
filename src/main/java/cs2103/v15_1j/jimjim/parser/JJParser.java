package cs2103.v15_1j.jimjim.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import cs2103.v15_1j.jimjim.antlr4.*;
import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.command.InvalidCommand;

public class JJParser implements Parser {

    private static final Logger logger = Logger.getLogger(JJParser.class.getName());

    @Override
    public Command parse(String userCommand) {
        assert userCommand != null;
        logger.entering("JJParser", "parse", userCommand);
        UserCommandLexer lexer =
                new UserCommandLexer(new ANTLRInputStream(userCommand));
        UserCommandParser parser =
                new UserCommandParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.cmd();
        JJCommandVisitor visitor = new JJCommandVisitor(userCommand);
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

}
