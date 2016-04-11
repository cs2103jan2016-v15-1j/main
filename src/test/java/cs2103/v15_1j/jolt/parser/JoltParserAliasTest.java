//@@author A0131140E
package cs2103.v15_1j.jolt.parser;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.antlr4.UserCommandLexer;
import cs2103.v15_1j.jolt.command.AddCommand;
import cs2103.v15_1j.jolt.command.Command;
import cs2103.v15_1j.jolt.command.DeleteCommand;
import cs2103.v15_1j.jolt.parser.JoltParser;

public class JoltParserAliasTest {
    
    private JoltParser parser;
    private HashMap<String, Integer> aliases;

    @Before
    public void setUp() throws Exception {
        aliases = new HashMap<>();
        aliases.put("del", UserCommandLexer.DELETE);
        parser = new JoltParser();
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

    @Test
    public void testEscape() {
        Command result = this.parser.parse("\\DEL d8");
        assertEquals(true, result instanceof AddCommand);
        AddCommand casted = (AddCommand) result;
        assertEquals("DEL d8", casted.getTaskEvent().getName());
        result = this.parser.parse("\\\\DEL d8");
        assertEquals(true, result instanceof AddCommand);
        casted = (AddCommand) result;
        assertEquals("\\DEL d8", casted.getTaskEvent().getName());
    }

}
