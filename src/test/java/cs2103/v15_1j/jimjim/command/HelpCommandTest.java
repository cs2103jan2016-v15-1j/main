package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import org.junit.Test;

import cs2103.v15_1j.jimjim.uifeedback.HelpFeedback;

public class HelpCommandTest {

    @Test
    public void testExecute() {
        Command help = new HelpCommand();
        assertTrue(help.execute(null, null, null, null) instanceof HelpFeedback);
        
    }

}
