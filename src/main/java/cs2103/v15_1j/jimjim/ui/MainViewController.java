package cs2103.v15_1j.jimjim.ui;

import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.NotificationPane;

import cs2103.v15_1j.jimjim.model.DataLists;
import javafx.scene.control.TextField;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainViewController {

	private NotificationPane notificationPane;
	private BorderPane mainPane;
	private Pane leftPane;
	private MasterDetailPane rightPane;
	private AnchorPane bottomPane;
	private TextField commandBar;
	private Button executeBtn;

	private JJUI uiController;
	private DayPickerPaneController dayPickerPaneController;
	private FloatingTaskPaneController floatingTaskPaneController;
	private SearchPaneController searchPaneController;
	private TodayPaneController todayPaneController;
	private UpcomingPaneController upcomingPaneController;

	private DataLists lists;

	private final double BORDER_WIDTH = 14.0;
	private final double BOTTOM_BORDER = 30.0;
	private final double COMMAND_BAR_RIGHT_BORDER = 105.0;
	private final double PANE_WIDTH = 420.0;
	private final double PANE_HEIGHT = 500.0;
	private final double EXECUTE_BTN_WIDTH = 80.0;
	private final double EXECUTE_BTN_HEIGHT = 30.0;
	private final double WINDOW_WIDTH = 900.0;
	private final double WINDOW_HEIGHT = 600.0;

	public MainViewController(JJUI uiController, DataLists lists) {
		this.lists = lists;
		setUIController(uiController);
	}

	public NotificationPane initialize() {
		setUpMainView();

		return notificationPane;
	}

	private void setUpMainView(){
		setUpPaneControllers();
		setUpMainPane();
		setUpLeftPane();
		setUpRightPane();
		setUpBottomPane();
	}

	private void setUpPaneControllers(){
		dayPickerPaneController = new DayPickerPaneController(this, lists);
		floatingTaskPaneController = new FloatingTaskPaneController(this, lists);
		searchPaneController = new SearchPaneController(this, lists);
		todayPaneController = new TodayPaneController(this, lists);
		upcomingPaneController = new UpcomingPaneController(this, lists);
	}

	private void setUpMainPane(){
		mainPane = new BorderPane();
		mainPane.setPrefWidth(WINDOW_WIDTH);
		mainPane.setPrefHeight(WINDOW_HEIGHT);
		mainPane.setPadding(new Insets(14.0));
		
		notificationPane = new NotificationPane(mainPane);
		notificationPane.setShowFromTop(false);
	}

	private void setUpLeftPane(){
		leftPane = dayPickerPaneController.getDayPickerPane();
		leftPane.setPrefWidth(PANE_WIDTH);
		leftPane.setPrefHeight(PANE_HEIGHT);

		mainPane.setLeft(leftPane);
	}

	private void setUpRightPane(){
		rightPane = new MasterDetailPane();
		rightPane.setMasterNode(floatingTaskPaneController.getFloatingTaskPane());
		rightPane.setDetailNode(searchPaneController.getSearchPane());
		rightPane.setDetailSide(Side.BOTTOM);
		rightPane.setShowDetailNode(false);
		rightPane.setPrefWidth(PANE_WIDTH);
		rightPane.setPrefHeight(PANE_HEIGHT);
		rightPane.setDividerPosition(0.5);
		rightPane.setAnimated(true);

		mainPane.setRight(rightPane);
	}

	private void setUpBottomPane(){
		bottomPane = new AnchorPane();
		BorderPane.setMargin(bottomPane, new Insets(BORDER_WIDTH, BORDER_WIDTH, BOTTOM_BORDER, BORDER_WIDTH));
		setUpCommandBar();
		setUpExecuteBtn();

		bottomPane.getChildren().addAll(commandBar, executeBtn);
		mainPane.setBottom(bottomPane);
	}

	private void setUpCommandBar(){
		commandBar = new TextField();
		commandBar.setPromptText("Enter Command");
		commandBar.setOnAction(event -> handleCommand());
		AnchorPane.setLeftAnchor(commandBar, BORDER_WIDTH);
		AnchorPane.setBottomAnchor(commandBar, BORDER_WIDTH);
		AnchorPane.setRightAnchor(commandBar, COMMAND_BAR_RIGHT_BORDER);
	}

	private void setUpExecuteBtn(){
		executeBtn = new Button("Execute");
		executeBtn.setPrefWidth(EXECUTE_BTN_WIDTH);
		executeBtn.setPrefHeight(EXECUTE_BTN_HEIGHT);
		executeBtn.setOnAction(event -> handleCommand());
		AnchorPane.setBottomAnchor(executeBtn, BORDER_WIDTH);
		AnchorPane.setRightAnchor(executeBtn, BORDER_WIDTH);
	}

	public void updateData(DataLists tempList){
		this.lists = tempList;
		dayPickerPaneController.refreshData(lists);
		floatingTaskPaneController.refreshData(lists);
		searchPaneController.refreshData(lists);
		todayPaneController.refreshData(lists);
		upcomingPaneController.refreshData(lists);
	}
	
	public void showNotification(String msg){
		notificationPane.setText(msg);
		notificationPane.show();
		
		Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
            	notificationPane.hide();
            }
        });
        new Thread(sleeper).start();
	}
	
	public void showHelp(){
		
	}
	
	public void showSearchResults(){
		DataLists searchResults = uiController.getSearchResults();
		searchPaneController.refreshData(searchResults);
		rightPane.setShowDetailNode(true);
	}
	
	public void hideSearch(){
		rightPane.setShowDetailNode(false);
	}

	public void focusCommandBar(){
		commandBar.requestFocus();
	}

	private void handleCommand() {
		if (commandBar.getText() != null) {
			uiController.executeCommand(commandBar.getText());
			commandBar.setText("");
		}
	}

	public void setUIController(JJUI uiController){
		this.uiController = uiController;
	}

}
