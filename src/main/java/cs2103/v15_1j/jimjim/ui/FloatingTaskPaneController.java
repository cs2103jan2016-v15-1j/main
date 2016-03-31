package cs2103.v15_1j.jimjim.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class FloatingTaskPaneController {

	private GridPane floatingTaskGridPane;
	private ScrollPane floatingTaskScrollPane;

	private MainViewController con;

	private DataLists masterList;
	private DataLists displayList;

	private final double COLUMN_WIDTH = 500.0;
	private final double TASK_LABEL_WIDTH = 350.0;

	private boolean showCompleted;
	private boolean hasCompleted;

	public FloatingTaskPaneController(MainViewController con, DataLists lists, DataLists displayLists){
		this.con = con;
		this.masterList = lists;
		this.displayList = displayLists;
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
		floatingTaskGridPane.getStyleClass().add("pane");

		floatingTaskScrollPane = new ScrollPane();
		floatingTaskScrollPane.setContent(floatingTaskGridPane);
		floatingTaskScrollPane.getStyleClass().add("scrollpane");
		floatingTaskScrollPane.setFocusTraversable(false);
		floatingTaskScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		floatingTaskScrollPane.getStyleClass().add("pane");
	}

	public void refreshData(){
		showFloatingTasks();
	}

	private void showFloatingTasks(){
		floatingTaskGridPane.getChildren().clear();
		int counter = 0;
		int rowNo = 0;
		hasCompleted = false;

		Label titleLabel = new Label("Floating Tasks");
		titleLabel.getStyleClass().add("header");
		floatingTaskGridPane.add(titleLabel, 0, rowNo, 4, 1);

		for(FloatingTask t: masterList.getFloatingTasksList()){
			if(!t.getCompleted()){
				rowNo++;
				addFloatingTaskToPane(t);
			}
			else {
				hasCompleted = true;
			}
		}

		if(showCompleted){
			for(FloatingTask t: masterList.getFloatingTasksList()){
				if(t.getCompleted()){
					rowNo++;
					addFloatingTaskToPane(t);
				}
			}
		}

		if(rowNo == 0){
			rowNo++;
			Label noLabel = new Label("There are no outstanding floating tasks.");
			noLabel.getStyleClass().add("red-label");
			floatingTaskGridPane.add(noLabel, 0, rowNo, 1, 1);
		}

		rowNo++;

		if(hasCompleted && !showCompleted){
			floatingTaskGridPane.getChildren().remove(titleLabel);
			floatingTaskGridPane.add(titleLabel, 0, 0, 3, 1);
			JFXButton showCompletedBtn = new JFXButton("Show Completed");
			showCompletedBtn.getStyleClass().add("button-raised");
			showCompletedBtn.setOnAction(event -> toggleShowCompleted());
			GridPane.setHalignment(showCompletedBtn, HPos.CENTER);
			floatingTaskGridPane.add(showCompletedBtn, 2, 0, 1, 1);
		}
		else if (hasCompleted && showCompleted){
			floatingTaskGridPane.getChildren().remove(titleLabel);
			floatingTaskGridPane.add(titleLabel, 0, 0, 3, 1);
			JFXButton hideCompletedBtn = new JFXButton("Hide Completed");
			hideCompletedBtn.getStyleClass().add("button-raised");
			hideCompletedBtn.setOnAction(event -> toggleShowCompleted());
			GridPane.setHalignment(hideCompletedBtn, HPos.CENTER);
			floatingTaskGridPane.add(hideCompletedBtn, 2, 0, 1, 1);
		}

	}

	private void addFloatingTaskToPane(FloatingTask t){
		displayList.add(t);
		
		JFXCheckBox cb = new JFXCheckBox();
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(t.completedProperty());
		cb.setDisable(true);
		floatingTaskGridPane.addColumn(0, cb);
		cb.setOnMouseClicked(event -> showFloatingTasks());

		int id = masterList.indexOf(t) + 1;
		Label idLabel = new Label("[F"+displayList.size('f')+"]");
		floatingTaskGridPane.addColumn(1, idLabel);

		Label taskLabel = new Label();
		taskLabel.textProperty().bindBidirectional(t.taskNameProperty());
		taskLabel.setWrapText(true);
		taskLabel.setPrefWidth(TASK_LABEL_WIDTH);

		if(!t.getCompleted()){
			idLabel.getStyleClass().add("id-label");
			taskLabel.getStyleClass().add("task-label");
		}
		else {
			idLabel.getStyleClass().add("completed-task-label");
			taskLabel.getStyleClass().add("completed-task-label");
		}

		floatingTaskGridPane.addColumn(2, taskLabel);
	}

	private void toggleShowCompleted(){
		showCompleted = !showCompleted;
		con.updateData();
		con.focusCommandBar();
	}
}