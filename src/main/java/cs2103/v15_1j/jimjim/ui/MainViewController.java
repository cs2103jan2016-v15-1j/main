package cs2103.v15_1j.jimjim.ui;

import cs2103.v15_1j.jimjim.model.DataLists;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MainViewController {

	private BorderPane mainPane;
	private Pane leftPane;
	private BorderPane rightPane;
	private AnchorPane bottomPane;
	private TextField commandBar;
	private Label statusLbl;
	private Button executeBtn;

	private JJUI uiController;
	private DayPickerPaneController dayPickerPaneController;
	private FloatingTaskPaneController floatingTaskPaneController;
	private SearchPaneController searchPaneController;
	private TodayPaneController todayPaneController;
	private UpcomingPaneController upcomingPaneController;

	private DataLists lists;

	private enum Panes {
		FLOATING_TASK, UPCOMING, TODAY, SEARCH
	}

	private final double BORDER_WIDTH = 14.0;
	private final double COMMAND_BAR_RIGHT_BORDER = 105.0;
	private final double PANE_WIDTH = 420.0;
	private final double PANE_HEIGHT = 500.0;
	private final double EXECUTE_BTN_WIDTH = 80.0;
	private final double EXECUTE_BTN_HEIGHT = 30.0;
	private final double STATUS_LBL_BOTTOM_BORDER = 47.0;
	private final double WINDOW_WIDTH = 900.0;
	private final double WINDOW_HEIGHT = 600.0;

	public MainViewController(JJUI uiController, DataLists lists) {
		this.lists = lists;
		setUIController(uiController);
	}

	public BorderPane initialize() {
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
	}

	private void setUpLeftPane(){
		leftPane = dayPickerPaneController.getDayPickerPane();
		leftPane.setPrefWidth(PANE_WIDTH);
		leftPane.setPrefHeight(PANE_HEIGHT);

		mainPane.setLeft(leftPane);
	}

	private void setUpRightPane(){
		rightPane = new BorderPane();
		setRightPaneContent(Panes.FLOATING_TASK);
		rightPane.setTop(setUpRightPaneButtonBar());
		rightPane.setPrefWidth(PANE_WIDTH);
		rightPane.setPrefHeight(PANE_HEIGHT);

		mainPane.setRight(rightPane);
	}

	private void setRightPaneContent(Panes pane){
		if(pane == Panes.FLOATING_TASK){
			rightPane.setCenter(floatingTaskPaneController.getFloatingTaskPane());
		}
		else if (pane == Panes.UPCOMING){
			rightPane.setCenter(upcomingPaneController.getUpcomingPane());
		}
		else if (pane == Panes.TODAY){
			rightPane.setCenter(todayPaneController.getTodayPane());
		}

		else if (pane == Panes.SEARCH){
			rightPane.setCenter(searchPaneController.getSearchPane());
		}

	}

	private HBox setUpRightPaneButtonBar(){
		HBox buttonBar = new HBox();
		ToggleGroup  rightPaneGroup = new ToggleGroup();

		ToggleButton floatingTasksBtn = new ToggleButton("Floating Tasks");
		floatingTasksBtn.setToggleGroup(rightPaneGroup);
		floatingTasksBtn.setSelected(true);
		floatingTasksBtn.setUserData(Panes.FLOATING_TASK);
		buttonBar.getChildren().add(floatingTasksBtn);

		ToggleButton upcomingBtn = new ToggleButton("Upcoming");
		upcomingBtn.setToggleGroup(rightPaneGroup);
		upcomingBtn.setUserData(Panes.UPCOMING);
		buttonBar.getChildren().add(upcomingBtn);

		ToggleButton todayBtn = new ToggleButton("Today");
		todayBtn.setToggleGroup(rightPaneGroup);
		todayBtn.setUserData(Panes.TODAY);
		//buttonBar.getChildren().add(todayBtn);

		ToggleButton searchBtn = new ToggleButton("Search");
		searchBtn.setToggleGroup(rightPaneGroup);
		searchBtn.setUserData(Panes.SEARCH);
		buttonBar.getChildren().add(searchBtn);

		rightPaneGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle toggle, Toggle new_toggle) {
				if (new_toggle != null){
					setRightPaneContent((Panes) rightPaneGroup.getSelectedToggle().getUserData());
				}
			}
		});

		buttonBar.setAlignment(Pos.CENTER);

		return buttonBar;
	}

	private void setUpBottomPane(){
		bottomPane = new AnchorPane();
		setUpCommandBar();
		setUpExecuteBtn();
		setUpStatusLbl();

		bottomPane.getChildren().addAll(commandBar, executeBtn, statusLbl);
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

	private void setUpStatusLbl(){
		statusLbl = new Label("");
		AnchorPane.setLeftAnchor(statusLbl, BORDER_WIDTH);
		AnchorPane.setBottomAnchor(statusLbl, STATUS_LBL_BOTTOM_BORDER);
	}

	public void updateData(DataLists tempList){
		this.lists = tempList;
		dayPickerPaneController.refreshData(lists);
		floatingTaskPaneController.refreshData(lists);
		searchPaneController.refreshData(lists);
		todayPaneController.refreshData(lists);
		upcomingPaneController.refreshData(lists);
	}

	public void focusCommandBar(){
		commandBar.requestFocus();
	}

	private void handleCommand() {
		if (commandBar.getText() != null) {
			String status = uiController.executeCommand(commandBar.getText());
			displayMessage(status);
			commandBar.setText("");
		}
	}

	public void displayMessage(String msg){
		statusLbl.setText(msg);
	}

	public void setUIController(JJUI uiController){
		this.uiController = uiController;
	}

}
