package cs2103.v15_1j.jolt.uifeedback;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;
import cs2103.v15_1j.jolt.ui.DayPickerPaneController;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class DayPickerPaneControllerTest extends GuiTest {
	
	private DataLists masterList;
	private DataLists displayList;
	
	private DayPickerPaneController con;
	
	@Before
	public void setUp() throws Exception {
		masterList = new DataLists();
		displayList = new DataLists();
		
		Event event = new Event("Meeting", LocalDate.now().atTime(12, 00), LocalDate.now().atTime(13, 00));
		Event eventClashing = new Event("Meeting Again", LocalDate.now().atTime(12, 30),
				LocalDate.now().atTime(13, 00));
		FloatingTask fTask = new FloatingTask("Get milk");
		DeadlineTask dTask = new DeadlineTask("Buy oranges", LocalDate.now().atTime(20, 00));
		
		masterList.add(event);
		masterList.add(eventClashing);
		masterList.add(fTask);
		masterList.add(dTask);
		
		con = new DayPickerPaneController(null, masterList, displayList);
	}
	
	public Parent getRootNode() {
		return con.getDayPickerPane();
	}

	@Test
	public static void dayPickerNodeTest() {
		GridPane pane = find("#dayDetailGridPane");
		assertEquals(4, pane.getChildren().size());
	}
}
