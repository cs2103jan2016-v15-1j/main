package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;

import cs2103.v15_1j.jimjim.model.DataLists;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class TodayPaneController {

	private GridPane todayGridPane;
	private ScrollPane todayScrollPane;

	private MainViewController con;
	private TaskEventRowFactory rowFactory;

	private DataLists masterList;
	private DataLists displayList;

	private Integer rowNo;
	private boolean showCompleted;
	private boolean hasCompleted;

	private final double COLUMN_WIDTH = 500.0;

	public TodayPaneController(MainViewController con, DataLists masterList, DataLists displayList){
		this.masterList = masterList;
		this.displayList = displayList;
		setMainViewController(con);
		initialize();
	}

	public ScrollPane getOverdueScrollPane(){
		return todayScrollPane;
	}

	private void initialize(){
		setUpTodayPane();
		rowFactory.showTodayTaskEvents();
		rowFactory.showOverdue();
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

		rowFactory = new TaskEventRowFactory(masterList, displayList, todayGridPane, rowNo);
	}

	public void refreshData(){
		rowFactory.showTodayTaskEvents();
		rowFactory.showOverdue();
		showFloatingTasks();
	}
	
	private void showFloatingTasks(){
		hasCompleted = rowFactory.showFloatingTasks(showCompleted);
		
		int index = todayGridPane.getChildren().size();
		
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

	public void setMainViewController(MainViewController con){
		this.con = con;
	}
}
