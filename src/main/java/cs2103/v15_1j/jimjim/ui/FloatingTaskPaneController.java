package cs2103.v15_1j.jimjim.ui;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class FloatingTaskPaneController {

	private GridPane floatingTaskGridPane;
	private ScrollPane floatingTaskScrollPane;

	private MainViewController con;

	private DataLists lists;

	private final double COLUMN_WIDTH = 300.0;

	public FloatingTaskPaneController(MainViewController con, DataLists lists){
		this.con = con;
		this.lists = lists;
		initialize();
	}

	public ScrollPane getFloatingTaskPane(){
		return floatingTaskScrollPane;
	}

	private void initialize(){
		setUpFloatingTaskPane();
		showFloatingTasks();
	}

	private void setUpFloatingTaskPane(){
		floatingTaskGridPane = new GridPane();
		floatingTaskGridPane.prefWidth(COLUMN_WIDTH);

		floatingTaskScrollPane = new ScrollPane();
		floatingTaskScrollPane.setContent(floatingTaskGridPane);
		floatingTaskScrollPane.setFocusTraversable(false);
		floatingTaskScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	}

	public void refreshData(DataLists lists){
		this.lists = lists;
		showFloatingTasks();
	}

	private void showFloatingTasks(){
		floatingTaskGridPane.getChildren().clear();
		for(FloatingTask t: lists.getFloatingTasksList()){
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

			floatingTaskGridPane.addColumn(0, row);
		}
	}
}
