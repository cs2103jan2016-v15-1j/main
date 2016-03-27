package cs2103.v15_1j.jimjim.ui;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class FloatingTaskPaneController {

	private GridPane floatingTaskGridPane;
	private ScrollPane floatingTaskScrollPane;

	private MainViewController con;

	private DataLists lists;

	private final double COLUMN_WIDTH = 300.0;

	private boolean showCompleted;
	private boolean hasCompleted;

	public FloatingTaskPaneController(MainViewController con, DataLists lists){
		this.con = con;
		this.lists = lists;
		showCompleted = false;
		hasCompleted = false;
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
		floatingTaskGridPane.setHgap(10);

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
		int counter = 0;
		int rowNo = 0;

		Label titleLabel = new Label("Floating Tasks");
		floatingTaskGridPane.add(titleLabel, 0, rowNo, 4, 1);

		for(FloatingTask t: lists.getFloatingTasksList()){
			counter++;
			if(!t.getCompleted()){
				rowNo++;
				addFloatingTaskToPane(t, counter);
			}
			else {
				hasCompleted = true;
			}
		}

		if(showCompleted){
			counter = 0;
			for(FloatingTask t: lists.getFloatingTasksList()){
				counter++;
				if(t.getCompleted()){
					rowNo++;
					addFloatingTaskToPane(t, counter);
				}
			}
		}

		rowNo++;

		if(hasCompleted && !showCompleted){
			Button showCompletedBtn = new Button("Show Completed");
			showCompletedBtn.setOnAction(event -> toggleShowCompleted());
			GridPane.setHalignment(showCompletedBtn, HPos.CENTER);
			floatingTaskGridPane.add(showCompletedBtn, 0, rowNo, 4, 1);
		}
		else if (hasCompleted && showCompleted){
			Button hideCompletedBtn = new Button("Hide Completed");
			hideCompletedBtn.setOnAction(event -> toggleShowCompleted());
			GridPane.setHalignment(hideCompletedBtn, HPos.CENTER);
			floatingTaskGridPane.add(hideCompletedBtn, 0, rowNo, 4, 1);
		}

	}

	private void addFloatingTaskToPane(FloatingTask t, int counter){
		CheckBox cb = new CheckBox();
		cb.selectedProperty().bindBidirectional(t.completedProperty());
		cb.setDisable(true);
		floatingTaskGridPane.addColumn(0, cb);
		cb.setOnMouseClicked(event -> showFloatingTasks());

		Label idLabel = new Label("[F"+counter+"]");
		floatingTaskGridPane.addColumn(1, idLabel);

		Label taskLabel = new Label();
		taskLabel.textProperty().bindBidirectional(t.taskNameProperty());
		floatingTaskGridPane.addColumn(2, taskLabel);
	}

	private void toggleShowCompleted(){
		showCompleted = !showCompleted;
		showFloatingTasks();
		con.focusCommandBar();
	}
}