package cs2103.v15_1j.jimjim.ui;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.MasterDetailPane;

import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class MainViewController {

	private AnchorPane mainPane;
	private TextField commandBar;
	private Label taskLbl;
	private Label eventLbl;
	private Label statusLbl;
	private Button executeBtn;
	private MasterDetailPane taskPane;
	private MasterDetailPane eventPane;

	private JJUI uiController;
	private TaskViewController taskViewController;
	private EventViewController eventViewController;

	private final double BORDER_WIDTH = 14.0;
	private final double COMMAND_BAR_RIGHT_BORDER = 105.0;
	private final double EXECUTE_BTN_WIDTH = 80.0;
	private final double EXECUTE_BTN_HEIGHT = 30.0;
	private final double EVENT_LBL_TOP_BORDER = 270.0;
	private final double STATUS_LBL_BOTTOM_BORDER = 47.0;
	private final double WINDOW_WIDTH = 700.0;
	private final double WINDOW_HEIGHT = 600.0;

	public MainViewController() {
		taskViewController = new TaskViewController(this);
		eventViewController = new EventViewController(this);
	}

	public AnchorPane initialize() {
		return setUpMainView();
	}

	private AnchorPane setUpMainView(){
		setUpTaskAndEventPanes();
		setUpLabels();
		setUpCommandBar();
		setUpExecuteBtn();
		setUpStatusLbl();
		setUpMainPane();
		return mainPane;
	}

	private void setUpTaskAndEventPanes(){
		taskPane = taskViewController.setUpTaskPane();
		eventPane = eventViewController.setUpEventPane();
	}

	private void setUpLabels(){
		taskLbl = new Label("Task");
		taskLbl.setFont(new Font(12));
		AnchorPane.setLeftAnchor(taskLbl, BORDER_WIDTH);
		AnchorPane.setTopAnchor(taskLbl, BORDER_WIDTH);

		eventLbl = new Label("Events");
		eventLbl.setFont(new Font(12));
		AnchorPane.setLeftAnchor(eventLbl, BORDER_WIDTH);
		AnchorPane.setTopAnchor(eventLbl, EVENT_LBL_TOP_BORDER);
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
		mainPane.getChildren().addAll(taskLbl, taskPane, eventLbl, eventPane, commandBar, executeBtn, statusLbl);
	}

	public void refreshUI(){
		uiController.refreshUI();
	}

	public void refreshUI(List<TaskEvent> tempList){

		List<Task> tempTaskList = new ArrayList<Task>();
		List<Event> tempEventList = new ArrayList<Event>();

		for(TaskEvent te: tempList){
			if(te instanceof Task){
				tempTaskList.add((Task) te);
			}
			else if(te instanceof Event){
				tempEventList.add((Event) te);
			}
		}

		taskViewController.refreshUI(tempTaskList);
		eventViewController.refreshUI(tempEventList);
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
