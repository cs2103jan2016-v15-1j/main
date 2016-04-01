package cs2103.v15_1j.jimjim.parser;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.antlr4.UserCommandLexer;
import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.command.DeleteCommand;

public class JJParserAliasTest {
    
    private JJParser parser;
    private HashMap<String, Integer> aliases;

    @Before
    public void setUp() throws Exception {
        aliases = new HashMap<>();
        aliases.put("del", UserCommandLexer.DELETE);
        parser = new JJParser();
        parser.setAliases(aliases);
    }

    @Test
    public void test() {
        Command result = this.parser.parse("DEL d8");
        assertEquals(true, result instanceof DeleteCommand);
        DeleteCommand casted = (DeleteCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('d', casted.getPrefix());
    }

}
