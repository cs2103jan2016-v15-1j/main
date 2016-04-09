package cs2103.v15_1j.jolt.ui;

import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;

import cs2103.v15_1j.jolt.model.DataLists;
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

	private boolean shouldShowCompleted;
	private boolean hasCompleted;
	private boolean shouldShowOverdue;

	private final double COLUMN_WIDTH = 500.0;

	// @@author A0139963N
	/**
	 * Constructor
	 * @param con Reference to Main View Controller
	 * @param masterList Master List of Tasks and Events
	 * @param displayList Display List of Tasks and Events
	 */
	public TodayPaneController(MainViewController con, DataLists masterList, DataLists displayList) {
		this.masterList = masterList;
		this.displayList = displayList;
		this.shouldShowOverdue = true;
		this.con = con;
		initialize();
	}

	/**
	 * Retrieves the Today Pane
	 * @return Today Pane
	 */
	public ScrollPane getTodayPane() {
		return todayScrollPane;
	}

	/**
	 * Initialize Components
	 */
	private void initialize() {
		setUpTodayPane();
		setUpRowFactory();
		showFloatingTasks();
		refreshData();
	}

	private void setUpTodayPane() {
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

	private void setUpRowFactory() {
		rowFactory = new TaskEventRowFactory(masterList, displayList, todayGridPane);
	}

	/**
	 * Refreshes Data in the UI
	 */
	public void refreshData() {
		rowFactory.clear();
		rowFactory.addLabel("Today", "header");
		int noOfTaskEvents = rowFactory.showTaskEventsOnDate(LocalDate.now());

		if (noOfTaskEvents == 0) {
			rowFactory.addLabel("No events or deadline tasks today", "red-label");
		}

		if (shouldShowOverdue) {
			rowFactory.addLabel("Overdue", "header");
			int noOfOverdue = rowFactory.showOverdue();

			if (noOfOverdue == 0) {
				rowFactory.addLabel("Congratulations there are no Overdue Events or Tasks", "no-overdue-label");
			}

			rowFactory.addLabel("", "");
		}

		showFloatingTasks();
	}

	private void showFloatingTasks() {
		rowFactory.addLabel("Floating Tasks", "header");
		hasCompleted = rowFactory.showAllFloatingTasks(shouldShowCompleted);

		int index = todayGridPane.getChildren().size();

		if (displayList.getFloatingTasksList().size() == 0) {
			if (!hasCompleted) {
				rowFactory.addLabel("You have no floating tasks", "no-overdue-label");
			} else {
				rowFactory.addLabel("You have no outstanding floating tasks", "no-overdue-label");
			}

			rowFactory.addLabel("", "");
		}

		if (hasCompleted && !shouldShowCompleted) {
			JFXButton showCompletedBtn = new JFXButton("Show Completed");
			showCompletedBtn.getStyleClass().add("button-raised");
			showCompletedBtn.setOnAction(event -> toggleShowCompleted());
			GridPane.setHalignment(showCompletedBtn, HPos.CENTER);
			todayGridPane.add(showCompletedBtn, 0, index, 3, 1);
		} else if (hasCompleted && shouldShowCompleted) {
			JFXButton hideCompletedBtn = new JFXButton("Hide Completed");
			hideCompletedBtn.getStyleClass().add("button-raised");
			hideCompletedBtn.setOnAction(event -> toggleShowCompleted());
			GridPane.setHalignment(hideCompletedBtn, HPos.CENTER);
			todayGridPane.add(hideCompletedBtn, 0, index, 3, 1);
		}

	}

	private void toggleShowCompleted() {
		shouldShowCompleted = !shouldShowCompleted;
		con.refreshData();
		con.focusCommandBar();
	}

	/**
	 * Set whether Completed Tasks and Events should be shown
	 * @param shouldShowCompleted Whether Completed Tasks of Events should be shown
	 */
	public void setShowCompleted(boolean shouldShowCompleted) {
		this.shouldShowCompleted = shouldShowCompleted;
		con.refreshData();
		con.focusCommandBar();
	}

	/**
	 * Set whether Overdue Tasks and Events should be shown
	 * @param shouldShowOverdue Whether Overdue Tasks and Events should be shown
	 */
	public void setShowOverdue(boolean shouldShowOverdue) {
		this.shouldShowOverdue = shouldShowOverdue;
		con.refreshData();
		con.focusCommandBar();
	}
}
