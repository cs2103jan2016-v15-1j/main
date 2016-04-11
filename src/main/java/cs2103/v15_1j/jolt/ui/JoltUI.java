package cs2103.v15_1j.jolt.ui;

import cs2103.v15_1j.jolt.controller.Controller;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class JoltUI implements UI {

	private Controller con;
	private Stage primaryStage;
	private MainViewController mainViewController;

	private final String APPLICATION_NAME = "Jolt";

	// @@author A0139963N
	/**
	 * Initializes the MainViewController
	 */
	public void initialize(){
		mainViewController = new MainViewController(this, getMasterList(), getDisplayList(), getSearchResults());
	}

	/**
	 * Sets the Stage of the Application
	 */
	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(APPLICATION_NAME);
		showMainView();
	}

	/**
	 * Returns the Stage of the Application
	 * @return Stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Shows the Main View for the UI
	 */
	public void showMainView() {
		BorderPane mainView = mainViewController.initialize(primaryStage);

		Scene scene = new Scene(mainView);
		scene.getStylesheets().add("css/ui.css");

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();

		mainViewController.focusCommandBar();

		refreshData();

		primaryStage.show();
	}

	/**
	 * Displays an Error Message
	 * @param message Message to be displayed
	 */
	public void showFatalError(String message) {
		mainViewController.showFatalError(message);
	}

	/**
	 * Refreshes the UI
	 */
	public void refreshData() {
		mainViewController.refreshData();
	}

	/**
	 * Refreshes the UI based on Feedback from a Command
	 * @param feedback
	 */
	public void refreshData(UIFeedback feedback) {
		refreshData();
		feedback.execute(mainViewController);
	}

	/**
	 * Retrieves the Master List of Tasks and Events from Controller
	 * @return Master List
	 */
	private DataLists getMasterList() {
		DataLists tempList = con.getMasterList();
		assert (tempList) != null;

		return tempList;
	}

	/**
	 * Retrieves the Display List of Tasks and Events from Controller
	 * @return Display List
	 */
	private DataLists getDisplayList() {
		DataLists tempList = con.getDisplayList();
		assert (tempList) != null;

		return tempList;
	}

	/**
	 * Retrieves the Search Results List of Tasks and Events from Controller
	 * @return Display List
	 */
	public DataLists getSearchResults() {
		DataLists tempList = con.getSearchResultsList();
		assert (tempList) != null;

		return tempList;
	}

	/**
	 * Executes the given Command using the Controller Component
	 * @param userCommand Command entered by User
	 */
	public void executeCommand(String userCommand) {
		UIFeedback temp = con.execute(userCommand);
		assert (temp) != null;

		refreshData(temp);
	}

	/**
	 * Retrieves the Save File Path from Controller Component
	 * @return Save File Path
	 */
	public String getFilePath() {
		String temp = con.getFilePath();
		assert (temp) != null;
		
		return temp;
	}

	/**
	 * Sets the New Save File Path in the Controller Component
	 * @param filePath New Save File Path
	 */
	public void setFilePath(String filePath) {
		UIFeedback temp = con.setFilePath(filePath);
		assert (temp) != null;

		refreshData(temp);
	}

	/**
	 * Sets the Reference to the Controller Component
	 * @param Controller Component
	 */
	public void setController(Controller con) {
		this.con = con;
	}
}
