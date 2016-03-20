package cs2103.v15_1j.jimjim.ui;

import cs2103.v15_1j.jimjim.model.DataLists;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MainViewController {

	private AnchorPane mainPane;
	private Pane leftPane;
	private BorderPane rightPane;
	private TextField commandBar;
	private Label statusLbl;
	private Button executeBtn;

	private JJUI uiController;
	private DayPickerPaneController dayPickerPaneController;
	private TaskPaneController taskPaneController;
	private TodayPaneController todayPaneController;
	private UpcomingPaneController upcomingPaneController;

	private DataLists lists;

	private enum Panes {
		FLOATING_TASK, UPCOMING, TODAY
	}

	private final double BORDER_WIDTH = 14.0;
	private final double COMMAND_BAR_RIGHT_BORDER = 105.0;
	private final double PANE_WIDTH = 300.0;
	private final double PANE_HEIGHT = 500.0;
	private final double EXECUTE_BTN_WIDTH = 80.0;
	private final double EXECUTE_BTN_HEIGHT = 30.0;
	private final double EVENT_LBL_TOP_BORDER = 270.0;
	private final double NO_BORDER = 0.0;
	private final double STATUS_LBL_BOTTOM_BORDER = 47.0;
	private final double WINDOW_WIDTH = 700.0;
	private final double WINDOW_HEIGHT = 600.0;

	public MainViewController(JJUI uiController, DataLists lists) {
		this.lists = lists;
		setUIController(uiController);
	}

	public AnchorPane initialize() {
		setUpMainView();

		return mainPane;
	}

	private void setUpMainView(){
		setUpPaneControllers();
		setUpLeftPane();
		setUpRightPane();
		setUpCommandBar();
		setUpExecuteBtn();
		setUpStatusLbl();
		setUpMainPane();
	}

	private void setUpPaneControllers(){
		dayPickerPaneController = new DayPickerPaneController(this, lists);
		taskPaneController = new TaskPaneController(this, lists);
		todayPaneController = new TodayPaneController(this, lists);
		upcomingPaneController = new UpcomingPaneController(this, lists);
	}

	private void setUpLeftPane(){
		leftPane = dayPickerPaneController.getDayPickerPane();
		leftPane.setPrefWidth(PANE_WIDTH);
		leftPane.setPrefHeight(PANE_HEIGHT);

		AnchorPane.setTopAnchor(leftPane, BORDER_WIDTH);
		AnchorPane.setLeftAnchor(leftPane, BORDER_WIDTH);
	}

	private void setUpRightPane(){
		rightPane = new BorderPane();
		setRightPaneContent(Panes.FLOATING_TASK);
		rightPane.setTop(setUpRightPaneButtonBar());
		rightPane.setPrefWidth(PANE_WIDTH);
		rightPane.setPrefHeight(PANE_HEIGHT);

		AnchorPane.setTopAnchor(rightPane, BORDER_WIDTH);
		AnchorPane.setRightAnchor(rightPane, BORDER_WIDTH);
	}

	private void setRightPaneContent(Panes pane){
		if(pane == Panes.FLOATING_TASK){
			rightPane.setCenter(taskPaneController.getTaskPane());
		}
		else if (pane == Panes.UPCOMING){
			rightPane.setCenter(upcomingPaneController.getUpcomingPane());
		}
		else if (pane == Panes.TODAY){
			rightPane.setCenter(todayPaneController.getTodayPane());
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
		buttonBar.getChildren().add(todayBtn);

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

	private void setUpMainPane(){
		mainPane = new AnchorPane();
		mainPane.setPrefWidth(WINDOW_WIDTH);
		mainPane.setPrefHeight(WINDOW_HEIGHT);
		mainPane.getChildren().addAll(leftPane, rightPane, commandBar, executeBtn, statusLbl);
	}

	public void updateData(DataLists tempList){
		this.lists = tempList;
		dayPickerPaneController.refreshData(lists);
		taskPaneController.refreshData(lists);
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
			uiController.refreshUI();
		}
	}

	public void displayMessage(String msg){
		statusLbl.setText(msg);
	}

	public void setUIController(JJUI uiController){
		this.uiController = uiController;
	}

}
