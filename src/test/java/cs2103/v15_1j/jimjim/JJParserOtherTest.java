package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JJParserOtherTest {

	JJParser parser;

	@Before
	public void setUp() throws Exception {
		this.parser = new JJParser();
	}

    @Test
    public void testDel() {
        Command result = this.parser.parse("DELETE 8");
        assertEquals(true, result instanceof DeleteCommand);
        DeleteCommand casted = (DeleteCommand) result;
        assertEquals(8, casted.getTaskNum());
    }
    
    @Test
    public void testMarkDone() {
        Command result = this.parser.parse("mark 3 as done");
        assertEquals(true, result instanceof MarkDoneCommand);
        MarkDoneCommand casted = (MarkDoneCommand) result;
        assertEquals(3, casted.getTaskNum());
    }

}
