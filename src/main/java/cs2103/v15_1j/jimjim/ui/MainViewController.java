package cs2103.v15_1j.jimjim.ui;

import java.util.Map;
import java.util.Optional;

import org.controlsfx.control.MasterDetailPane;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.Event;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainViewController {

	private BorderPane mainPane;
	private Pane leftPane;
	private MasterDetailPane rightPane;
	private BorderPane rightInnerPane;
	private BorderPane bottomPane;

	private JJUI uiController;
	private BottomPaneController bottomPaneController;
	private DayPickerPaneController dayPickerPaneController;
	private TodayPaneController todayPaneController;
	private SearchPaneController searchPaneController;

	private DataLists masterList;
	private DataLists displayList;
	private DataLists searchResultsList;

	private final double LEFT_PANE_WIDTH = 500.0;
	private final double RIGHT_PANE_WIDTH = 500.0;
	private final double PANE_HEIGHT = 500.0;
	private final double WINDOW_WIDTH = 1000.0;
	private final double WINDOW_HEIGHT = 600.0;

	// @@author A0139963N
	/**
	 * Constructor
	 * @param uiController Reference to the UI Component Controller
	 * @param masterList  Master List of all Tasks and Events
	 * @param displayList Display List of all Tasks and Events
	 * @param searchResultsList Search Results List
	 */
	public MainViewController(JJUI uiController, DataLists masterList, DataLists displayList,
			DataLists searchResultsList) {
		this.masterList = masterList;
		this.searchResultsList = searchResultsList;
		this.displayList = displayList;
		setUIController(uiController);
	}

	/**
	 * Initialize the components
	 * @param primaryStage Stage for the Application
	 * @return Main View
	 */
	public BorderPane initialize(Stage primaryStage) {
		try {
			setUpMainView(primaryStage);
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println(e.getMessage());
			showFatalError("An unexpected error has occured during initialization.");
		}

		return mainPane;
	}

	private void setUpMainView(Stage primaryStage) {
		setUpPaneControllers(primaryStage);
		setUpMainPane();
		setUpLeftPane();
		setUpRightPane();
		setUpBottomPane();
	}

	private void setUpPaneControllers(Stage primaryStage) {
		bottomPaneController = new BottomPaneController(this, primaryStage);
		dayPickerPaneController = new DayPickerPaneController(this, masterList, displayList);
		todayPaneController = new TodayPaneController(this, masterList, displayList);
		searchPaneController = new SearchPaneController(this, searchResultsList, displayList);
	}

	private void setUpMainPane() {
		mainPane = new BorderPane();
		mainPane.getStyleClass().add("pane");
		mainPane.setPrefWidth(WINDOW_WIDTH);
		mainPane.setPrefHeight(WINDOW_HEIGHT);
		mainPane.setPadding(new Insets(14.0));
	}

	private void setUpLeftPane() {
		leftPane = dayPickerPaneController.getDayPickerPane();
		leftPane.setPrefWidth(LEFT_PANE_WIDTH);
		leftPane.setPrefHeight(PANE_HEIGHT);

		mainPane.setLeft(leftPane);
	}

	private void setUpRightPane() {
		rightInnerPane = new BorderPane();
		rightInnerPane.setTop(todayPaneController.getTodayPane());

		rightPane = new MasterDetailPane();
		rightPane.setMasterNode(rightInnerPane);
		rightPane.setDetailNode(searchPaneController.getSearchPane());
		rightPane.setDetailSide(Side.BOTTOM);
		rightPane.setShowDetailNode(false);
		rightPane.setPrefWidth(RIGHT_PANE_WIDTH);
		rightPane.setPrefHeight(PANE_HEIGHT);
		rightPane.setDividerPosition(0.6);
		rightPane.setAnimated(true);

		mainPane.setRight(rightPane);
	}

	private void setUpBottomPane() {
		bottomPane = bottomPaneController.getBottomPane();
		mainPane.setBottom(bottomPane);
	}

	/**
	 * Refreshes Data shown in UI
	 */
	public void refreshData() {
		displayList.clear();
		dayPickerPaneController.refreshData();
		todayPaneController.refreshData();
		searchPaneController.refreshData();
	}

	/**
	 * Retrieves the Display List
	 * @return
	 */
	public DataLists getDisplayLists() {
		return displayList;
	}

	/**
	 * Displays a Notification
	 * @param msg Message to be displayed
	 */
	public void showNotification(String msg) {
		bottomPaneController.showNotification(msg);
	}

	/**
	 * Displays the Help PopOver
	 */
	public void showHelp(String page) {
		bottomPaneController.showHelp(page);
	}

	/**
	 * Displays the Search Results
	 */
	public void showSearchResults() {
		refreshData();
		rightPane.setShowDetailNode(true);
	}

	/**
	 * Hides the Search Results
	 */
	public void hideSearchResults() {
		rightPane.setShowDetailNode(false);
	}

	/**
	 * Hide or Show Completed Tasks
	 * @param shouldShowCompleted Whether Completed Tasks should be shown
	 */
	public void setShowCompleted(boolean shouldShowCompleted) {
		todayPaneController.setShowCompleted(shouldShowCompleted);
	}

	/**
	 * Hide or Show Overdue Tasks
	 * @param showOverdue Whether Overdue Tasks should be shown
	 */
	public void setShowOverdue(boolean showOverdue) {
		todayPaneController.setShowOverdue(showOverdue);
	}

	/**
	 * Show the List of Aliases
	 * @param aliasList List of Aliases
	 */
	public void showAliases(Map<String, String> aliasList) {
		bottomPaneController.showAliases(aliasList);
	}

	/**
	 * Grabs Focus on Command Bar
	 */
	public void focusCommandBar() {
		bottomPaneController.focusCommandBar();
	}

	/**
	 * Executes User's Command
	 * @param command User's Command
	 */
	public void executeCommand(String command) {
		uiController.executeCommand(command);
	}

	/**
	 * Retrieves the File Path
	 * @return File Path
	 */
	public String getFilePath() {
		return uiController.getFilePath();
	}

	/**
	 * Sets the File Path
	 * @param filePath File Path
	 */
	public void setFilePath(String filePath) {
		uiController.setFilePath(filePath);
	}

	/**
	 * Sets Reference to the UI Component Controller
	 * @param uiController UI Component Controller
	 */
	public void setUIController(JJUI uiController) {
		this.uiController = uiController;
	}

	/**
	 * Displays an Error Message, will terminate on OK
	 * @param message Message to be shown
	 */
	public void showFatalError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Fatal Error");
		alert.setHeaderText(null);
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.exit(0);
		}
	}

	/**
	 * Adds an Event
	 * @param event Event added
	 * @return Whether event clashes
	 */
	public boolean addEvent(Event event) {
		return dayPickerPaneController.addEvent(event);
	}

}
