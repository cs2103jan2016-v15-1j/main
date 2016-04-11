package cs2103.v15_1j.jolt.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import com.jfoenix.controls.JFXButton;

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

	private TodayPaneController con;

	public Parent getRootNode() {
		masterList = new DataLists();
		displayList = new DataLists();

		Event event = new Event("Meeting", LocalDate.now().atTime(12, 00), LocalDate.now().atTime(13, 00));
		Event eventClashing = new Event("Meeting Again", LocalDate.now().atTime(12, 30),
				LocalDate.now().atTime(13, 00));
		Event eventFullDay = new Event("New Year's Day", LocalDate.now().minusDays(1));
		DeadlineTask dTask = new DeadlineTask("Buy oranges", LocalDate.now().atTime(13, 00));
		FloatingTask fTask = new FloatingTask("Get milk");
		FloatingTask completedFTask = new FloatingTask("Get More Milk");
		completedFTask.setCompleted(true);

		masterList.add(event);
		masterList.add(eventClashing);
		masterList.add(eventFullDay);
		masterList.add(dTask);
		masterList.add(fTask);
		masterList.add(completedFTask);

		MainViewControllerStub stub = new MainViewControllerStub();
		con = new TodayPaneController(stub, masterList, displayList);
		stub.setTodayPaneController(con);

		return con.getTodayPane();
	}

	@Test
	public void todayGridPaneTest() {
		assertNotNull(find("#cbE1"));
		assertNotNull(find("#idLabelE1"));
		assertNotNull(find("#eventLabelE1"));
		assertNotNull(find("#dateTimeLabelE1"));
	}

	@Test
	public void floatingTest() {
		assertNotNull(find("#cbF1"));
		assertNotNull(find("#idLabelF1"));
		assertNotNull(find("#taskLabelF1"));
		
		assertNotNull(find("#showCompletedBtn"));
		
		JFXButton showCompletedBtn = find("#showCompletedBtn");
		click(showCompletedBtn);
		
		assertNotNull(find("#cbF2"));
		assertNotNull(find("#idLabelF2"));
		assertNotNull(find("#taskLabelF2"));
	}
}
