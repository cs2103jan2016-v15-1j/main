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

	public FloatingTaskPaneController(MainViewController con, DataLists lists){
		this.con = con;
		this.lists = lists;
		showCompleted = false;
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
		Button showCompletedBtn = new Button("Show Completed");
		showCompletedBtn.setOnAction(event -> showCompleted());
		GridPane.setHalignment(showCompletedBtn, HPos.CENTER);
		floatingTaskGridPane.add(showCompletedBtn, 0, rowNo, 4, 1);
	}

	private void addFloatingTaskToPane(FloatingTask t, int counter){
		CheckBox cb = new CheckBox();
		cb.setDisable(true);
		cb.selectedProperty().bindBidirectional(t.completedProperty());
		floatingTaskGridPane.addColumn(0, cb);
		cb.setOnMouseClicked(event -> showFloatingTasks());

		Label idLabel = new Label("[F"+counter+"]");
		floatingTaskGridPane.addColumn(1, idLabel);

		Label taskLabel = new Label();
		taskLabel.textProperty().bindBidirectional(t.taskNameProperty());
		floatingTaskGridPane.addColumn(2, taskLabel);
	}

	private void showCompleted(){
		showCompleted = !showCompleted;
		showFloatingTasks();
	}
}
