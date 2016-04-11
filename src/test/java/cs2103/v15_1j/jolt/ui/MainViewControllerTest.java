package cs2103.v15_1j.jolt.ui;

import static org.junit.Assert.assertEquals;

import org.controlsfx.control.MasterDetailPane;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import com.jfoenix.controls.JFXTextField;

import cs2103.v15_1j.jolt.model.DataLists;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainViewControllerTest extends GuiTest {

	private DataLists masterList;
	private DataLists displayList;
	private DataLists searchResultsList;

	private MainViewController con;

	public Parent getRootNode() {
		masterList = new DataLists();
		displayList = new DataLists();
		searchResultsList = new DataLists();

		JoltUIStub stub = new JoltUIStub();

		con = new MainViewController(stub, masterList, displayList, searchResultsList);
		Stage newStage = new Stage();

		return con.initialize(newStage);
	}

	@Test
	public void componentsTest() {
		BorderPane mainPane = find("#mainPane");
		Pane leftPane = find("#leftPane");
		MasterDetailPane rightPane = find("#rightPane");
		BorderPane bottomPane = find("#bottomPane");

		assert (mainPane.getLeft().equals(leftPane));
		assert (mainPane.getRight().equals(rightPane));
		assert (mainPane.getBottom().equals(bottomPane));
	}

	@Test
	public void commandBarTest() {
		JFXTextField commandBar = find("#commandBar");
		BorderPane bottomPane = find("#bottomPane");

		assert (bottomPane.getChildren().contains(commandBar));

		click(commandBar);
		type("Hello world");
		push(KeyCode.ENTER);

		type("Get milk");
		push(KeyCode.UP);

		assertEquals(commandBar.getText(), "Hello world");

		push(KeyCode.DOWN);
		assertEquals(commandBar.getText(), "Get milk");
	}
}
