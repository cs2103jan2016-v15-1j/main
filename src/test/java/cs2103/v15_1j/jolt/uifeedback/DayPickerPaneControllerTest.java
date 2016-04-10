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
import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class DayPickerPaneControllerTest extends GuiTest {
	
	private DataLists masterList;
	private DataLists displayList;
	
	private DayPickerPaneController con;
	
	@Before
	public void setUp() throws Exception {
	}
	
	public Parent getRootNode() {
		masterList = new DataLists();
		displayList = new DataLists();
		
		Event event = new Event("Meeting", LocalDate.now().atTime(12, 00), LocalDate.now().atTime(13, 00));
		Event eventClashing = new Event("Meeting Again", LocalDate.now().atTime(12, 30),
				LocalDate.now().atTime(13, 00));
		FloatingTask fTask = new FloatingTask("Get milk");
		DeadlineTask dTask = new DeadlineTask("Buy oranges", LocalDate.now().atTime(20, 00));
		Event eventFullDay = new Event("New Year's Day", LocalDate.now().minusDays(1));
		
		masterList.add(event);
		masterList.add(eventClashing);
		masterList.add(eventFullDay);
		masterList.add(fTask);
		masterList.add(dTask);
		
		con = new DayPickerPaneController(null, masterList, displayList);
		
		return con.getDayPickerPane();
	}
	
	@Test
	public void dayDetailGridPaneTest() {
		GridPane pane = find("#dayDetailGridPane");
		int noOfNodes = (4 * 3) + 1;
		assertEquals(noOfNodes, pane.getChildren().size());
	}
}
