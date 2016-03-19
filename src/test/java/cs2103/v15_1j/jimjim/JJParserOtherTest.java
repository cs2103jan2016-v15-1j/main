package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.command.DeleteCommand;
import cs2103.v15_1j.jimjim.command.InvalidCommand;
import cs2103.v15_1j.jimjim.command.MarkDoneCommand;
import cs2103.v15_1j.jimjim.parser.JJParser;

public class JJParserOtherTest {

	JJParser parser;

	@Before
	public void setUp() throws Exception {
		this.parser = new JJParser();
	}

    @Test
    public void testDel() {
        Command result = this.parser.parse("DELETE d8");
        assertEquals(true, result instanceof DeleteCommand);
        DeleteCommand casted = (DeleteCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('d', casted.getPrefix());

        result = this.parser.parse("delete F10");
        assertEquals(true, result instanceof DeleteCommand);
        casted = (DeleteCommand) result;
        assertEquals(10, casted.getTaskNum());
        assertEquals('f', casted.getPrefix());
        
        result = this.parser.parse("delete e8");
        assertEquals(true, result instanceof DeleteCommand);
        casted = (DeleteCommand) result;
        assertEquals(8, casted.getTaskNum());
        assertEquals('e', casted.getPrefix());
    }
    
    @Test
    public void testMarkDone() {
        Command result = this.parser.parse("mark d3 as done");
        assertEquals(true, result instanceof MarkDoneCommand);
        MarkDoneCommand casted = (MarkDoneCommand) result;
        assertEquals(3, casted.getTaskNum());
        assertEquals('d', casted.getPrefix());

        result = this.parser.parse("Mark F3");
        assertEquals(true, result instanceof MarkDoneCommand);
        casted = (MarkDoneCommand) result;
        assertEquals(3, casted.getTaskNum());
        assertEquals('f', casted.getPrefix());
    }

    @Test
    public void testMarkDoneEvent() {
        Command result = this.parser.parse("mark e3 as done");
        assertEquals(true, result instanceof InvalidCommand);
        InvalidCommand casted = (InvalidCommand) result;
        assertEquals("e3 is not a valid task!", casted.getMessage());
    }

}
