package cs2103.v15_1j.jolt.ui;

import static org.junit.Assert.assertEquals;

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
		GridPane pane = find("#todayGridPane");

		Label idLabel = new Label("E1");
		assertEquals (pane.getChildren().contains(idLabel), true);

		Label eventLabel = new Label("Meeting");
		assertEquals (pane.getChildren().contains(eventLabel), true);

	}

	@Test
	public void overdueTest() {
		GridPane pane = find("#todayGridPane");

		Label idLabel = new Label("E1");
		idLabel.getStyleClass().add("overdue-label");

		Label eventLabel = new Label("Meeting");
		eventLabel.getStyleClass().add("overdue-label");

		if (LocalTime.now().isAfter(LocalTime.of(13, 00))) {
			assert (pane.getChildren().contains(idLabel));
			assert (pane.getChildren().contains(eventLabel));
		} else {
			assert (!pane.getChildren().contains(idLabel));
			assert (!pane.getChildren().contains(eventLabel));
		}
	}

	@Test
	public void floatingTest() {
		GridPane pane = find("#todayGridPane");

		Label idLabel = new Label("F1");
		Label taskLabel = new Label("Get milk");
		
		Label completedIdLabel = new Label("F2");
		Label completedTaskLabel = new Label("Get More Milk");

		assert (pane.getChildren().contains(idLabel));
		assert (pane.getChildren().contains(taskLabel));

		assert (!pane.getChildren().contains(completedIdLabel));
		assert (!pane.getChildren().contains(completedTaskLabel));
		
		JFXButton showCompletedBtn = new JFXButton("Show Completed");
		JFXButton hideCompletedBtn = new JFXButton("Hide Completed");
		
		assert (pane.getChildren().contains(showCompletedBtn));
		assert (!pane.getChildren().contains(hideCompletedBtn));
		
		showCompletedBtn = find("#showCompletedBtn");
		click(showCompletedBtn);


		assert (pane.getChildren().contains(completedIdLabel));
		assert (pane.getChildren().contains(completedTaskLabel));
		assert (!pane.getChildren().contains(showCompletedBtn));
		assert (pane.getChildren().contains(hideCompletedBtn));
		
	}
}
