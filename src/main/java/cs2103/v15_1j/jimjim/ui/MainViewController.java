package cs2103.v15_1j.jimjim.ui;

import java.util.Map;
import java.util.Optional;

import org.controlsfx.control.MasterDetailPane;

import cs2103.v15_1j.jimjim.model.DataLists;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainViewController {

	private Stage primaryStage;
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

	public MainViewController(JJUI uiController, DataLists masterList, DataLists displayList, DataLists searchResultsList) {
		this.masterList = masterList;
		this.searchResultsList = searchResultsList;
		this.displayList = displayList;
		setUIController(uiController);
	}

	public BorderPane initialize(Stage primaryStagefilePath) {
		this.primaryStage = primaryStage;
		try{
			setUpMainView();
		} catch (Exception e){
			showFatalError("An unexpected error has occured during initialization.");
		}

		return mainPane;
	}

	private void setUpMainView(){
		setUpPaneControllers();
		setUpMainPane();
		setUpLeftPane();
		setUpRightPane();
		setUpBottomPane();
	}

	private void setUpPaneControllers(){
		bottomPaneController = new BottomPaneController(this, primaryStage);
		dayPickerPaneController = new DayPickerPaneController(this, masterList, displayList);
		todayPaneController = new TodayPaneController(this, masterList, displayList);
		searchPaneController = new SearchPaneController(this, searchResultsList, displayList);
	}

	private void setUpMainPane(){
		mainPane = new BorderPane();
		mainPane.getStyleClass().add("pane");
		mainPane.setPrefWidth(WINDOW_WIDTH);
		mainPane.setPrefHeight(WINDOW_HEIGHT);
		mainPane.setPadding(new Insets(14.0));
	}

	private void setUpLeftPane(){
		leftPane = dayPickerPaneController.getDayPickerPane();
		leftPane.setPrefWidth(LEFT_PANE_WIDTH);
		leftPane.setPrefHeight(PANE_HEIGHT);

		mainPane.setLeft(leftPane);
	}

	private void setUpRightPane(){
		rightPane = new MasterDetailPane();
		rightInnerPane = new BorderPane();

		rightInnerPane.setTop(todayPaneController.getTodayScrollPane());

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

	private void setUpBottomPane(){
		bottomPane = bottomPaneController.getBottomPane();
		mainPane.setBottom(bottomPane);
	}

	public void updateData(){
		displayList.clear();
		dayPickerPaneController.refreshData();
		todayPaneController.refreshData();
		searchPaneController.refreshData();
	}

	public DataLists getDisplayLists(){
		return displayList;
	}

	public void showNotification(String msg){
		bottomPaneController.showNotification(msg);
	}

	public void showHelp(){
		bottomPaneController.toggleHelp();
	}

	public void showSearchResults(){
		updateData();
		rightPane.setShowDetailNode(true);
	}

	public void hideSearchResults(){
		rightPane.setShowDetailNode(false);
	}
	
	public void setShowCompleted(boolean showCompleted){
		todayPaneController.setShowCompleted(showCompleted);
	}
	
	public void setShowOverdue(boolean showOverdue){
		todayPaneController.setShowOverdue(showOverdue);
	}
	
	public void showAliases(Map<String, String> aliasList){
		bottomPaneController.showAliases(aliasList);
	}

	public void focusCommandBar(){
		bottomPaneController.focusCommandBar();
	}

	public void executeCommand(String command){
		uiController.executeCommand(command);
	}
	
	public String getFilePath(){
		return uiController.getFilePath();
	}
	
	public void setFilePath(String filePath){
		uiController.setFilePath(filePath);
		showNotification("Save File Location has been changed to "+filePath);
	}

	public void setUIController(JJUI uiController){
		this.uiController = uiController;
	}
	
	public void showFatalError(String message){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Fatal Error");
		alert.setHeaderText(null);
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    System.exit(0);
		}
	}

}
