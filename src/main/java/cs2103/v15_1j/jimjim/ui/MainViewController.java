package cs2103.v15_1j.jimjim.ui;

import java.util.Map;

import org.controlsfx.control.MasterDetailPane;

import cs2103.v15_1j.jimjim.model.DataLists;
import javafx.geometry.Insets;
import javafx.geometry.Side;
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
	
	private String filePath;

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

	public BorderPane initialize(Stage primaryStage, String filePath) {
		this.primaryStage = primaryStage;
		this.filePath = filePath;
		setUpMainView();

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
		bottomPaneController = new BottomPaneController(this, primaryStage, filePath);
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

		rightInnerPane.setTop(todayPaneController.getOverdueScrollPane());

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
	
	public void showAliases(Map<String, String> aliasList){
		bottomPaneController.showAliases(aliasList);
	}

	public void focusCommandBar(){
		bottomPaneController.focusCommandBar();
	}

	public void executeCommand(String command){
		uiController.executeCommand(command);
	}
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
		uiController.setFilePath(filePath);
		showNotification("Save File Location has been changed to "+filePath);
	}

	public void setUIController(JJUI uiController){
		this.uiController = uiController;
	}

}
