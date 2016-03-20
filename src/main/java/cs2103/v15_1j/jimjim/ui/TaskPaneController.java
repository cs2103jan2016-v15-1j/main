package cs2103.v15_1j.jimjim.ui;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class TaskPaneController {

	private GridPane taskGridPane;
	private ScrollPane taskScrollPane;

	private MainViewController con;

	private DataLists lists;

	private final double COLUMN_WIDTH = 300.0;

	public TaskPaneController(MainViewController con, DataLists lists){
		this.con = con;
		this.lists = lists;
		initialize();
	}

	public ScrollPane getTaskPane(){
		return taskScrollPane;
	}

	private void initialize(){
		setUpTaskPane();
		getTasks();
	}

	private void setUpTaskPane(){
		taskGridPane = new GridPane();
		taskGridPane.prefWidth(COLUMN_WIDTH);

		taskScrollPane = new ScrollPane();
		taskScrollPane.setContent(taskGridPane);
		taskScrollPane.setFocusTraversable(false);
		taskScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	}

	public void refreshData(DataLists lists){
		this.lists = lists;
		getTasks();
	}

	private void getTasks(){
		taskGridPane.getChildren().clear();
		for(Task t: lists.getTasksList()){
			AnchorPane row = new AnchorPane();
			row.setPrefHeight(20.0);
			row.setPrefWidth(COLUMN_WIDTH);

			CheckBox cb = new CheckBox();
			cb.selectedProperty().bindBidirectional(t.completedProperty());
			AnchorPane.setTopAnchor(cb, 5.0);
			AnchorPane.setLeftAnchor(cb, 5.0);

			Label taskLabel = new Label();
			taskLabel.textProperty().bindBidirectional(t.taskNameProperty());
			AnchorPane.setTopAnchor(taskLabel, 5.0);
			AnchorPane.setLeftAnchor(taskLabel, 25.0);

			row.getChildren().addAll(cb, taskLabel);

			taskGridPane.addColumn(0, row);
		}
	}
}
