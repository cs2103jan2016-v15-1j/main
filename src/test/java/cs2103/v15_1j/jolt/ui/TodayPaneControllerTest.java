package cs2103.v15_1j.jolt.ui;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TodayPaneControllerTest extends GuiTest {
	private DataLists masterList;
	private DataLists displayList;

	private DayPickerPaneController con;

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

		con = new DayPickerPaneController(new MainViewControllerStub(), masterList, displayList);

		return con.getDayPickerPane();
	}

	@Test
	public void dayDetailGridPaneTest() {
		GridPane pane = find("#todayGridPane");
		int noOfNodes = (4 * 3) + 1;
		assertEquals(noOfNodes, pane.getChildren().size());

		Label idLabel = new Label("E1");
		assert(pane.getChildren().contains(idLabel));

		Label eventLabel = new Label("Meeting");
		assert(pane.getChildren().contains(eventLabel));

	}
}
