package cs2103.v15_1j.jimjim.command;

import static org.junit.Assert.*;

import org.junit.Test;

import cs2103.v15_1j.jimjim.uifeedback.HelpFeedback;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class HelpCommandTest {

    @Test
    public void testExecute() {
        Command help = new HelpCommand();
        UIFeedback feedback = help.execute(null);
        assertTrue(feedback instanceof HelpFeedback);
    }

}
