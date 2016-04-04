package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;

import cs2103.v15_1j.jimjim.model.DataLists;
import javafx.geometry.HPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class TodayPaneController {

	private GridPane todayGridPane;
	private ScrollPane todayScrollPane;

	private MainViewController con;
	private TaskEventRowFactory rowFactory;

	private DataLists masterList;
	private DataLists displayList;

	private boolean showCompleted;
	private boolean hasCompleted;
	private boolean showOverdue;

	private final double COLUMN_WIDTH = 500.0;

	//@@author Jeremy
	public TodayPaneController(MainViewController con, DataLists masterList, DataLists displayList){
		this.masterList = masterList;
		this.displayList = displayList;
		this.showOverdue = true;
		setMainViewController(con);
		initialize();
	}

	public ScrollPane getTodayScrollPane(){
		return todayScrollPane;
	}

	private void initialize(){
		setUpTodayPane();
		setUpRowFactory();
		showFloatingTasks();
	}

	private void setUpTodayPane(){
		todayGridPane = new GridPane();
		todayGridPane.prefWidth(COLUMN_WIDTH);
		todayGridPane.setHgap(10);
		todayGridPane.getStyleClass().add("pane");

		todayScrollPane = new ScrollPane();
		todayScrollPane.setContent(todayGridPane);
		todayScrollPane.getStyleClass().add("scrollpane");
		todayScrollPane.setFocusTraversable(false);
		todayScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		todayScrollPane.getStyleClass().add("pane");
	}

	private void setUpRowFactory(){
		rowFactory = new TaskEventRowFactory(masterList, displayList, todayGridPane);
		refreshData();
	}

	public void refreshData(){
		rowFactory.clear();
		rowFactory.addLabel("Today", "header");
		int noOfTaskEvents = rowFactory.showTaskEventsOnDate(LocalDate.now());

		if(noOfTaskEvents == 0){
			rowFactory.addLabel("No events or deadline tasks today", "red-label");
		}

		if(showOverdue){
			rowFactory.addLabel("Overdue", "header");
			int noOfOverdue = rowFactory.showOverdue();

			if(noOfOverdue == 0){
				rowFactory.addLabel("Congratulations there are no Overdue Events or Tasks", "no-overdue-label");
			}

			rowFactory.addLabel("", "");
		}

		showFloatingTasks();
	}

	private void showFloatingTasks(){
		rowFactory.addLabel("Floating Tasks", "header");
		hasCompleted = rowFactory.showAllFloatingTasks(showCompleted);

		int index = todayGridPane.getChildren().size();

		if(displayList.getFloatingTasksList().size() == 0){
			if(!hasCompleted){
				rowFactory.addLabel("You have no floating tasks", "task-label");
			}
			else {
				rowFactory.addLabel("You have no outstanding floating tasks", "task-label");
			}

			rowFactory.addLabel("", "");
		}

		if(hasCompleted && !showCompleted){
			JFXButton showCompletedBtn = new JFXButton("Show Completed");
			showCompletedBtn.getStyleClass().add("button-raised");
			showCompletedBtn.setOnAction(event -> toggleShowCompleted());
			GridPane.setHalignment(showCompletedBtn, HPos.CENTER);
			todayGridPane.add(showCompletedBtn, 0, index, 3, 1);
		}
		else if (hasCompleted && showCompleted){
			JFXButton hideCompletedBtn = new JFXButton("Hide Completed");
			hideCompletedBtn.getStyleClass().add("button-raised");
			hideCompletedBtn.setOnAction(event -> toggleShowCompleted());
			GridPane.setHalignment(hideCompletedBtn, HPos.CENTER);
			todayGridPane.add(hideCompletedBtn, 0, index, 3, 1);
		}

	}

	private void toggleShowCompleted(){
		showCompleted = !showCompleted;
		con.updateData();
		con.focusCommandBar();
	}

	public void setShowCompleted(boolean showCompleted){
		this.showCompleted = showCompleted;
		con.updateData();
		con.focusCommandBar();
	}

	public void setShowOverdue(boolean showOverdue){
		this.showOverdue = showOverdue;
		con.updateData();
		con.focusCommandBar();
	}

	public void setMainViewController(MainViewController con){
		this.con = con;
	}
}
