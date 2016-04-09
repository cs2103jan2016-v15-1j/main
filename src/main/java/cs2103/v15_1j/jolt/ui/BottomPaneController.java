package cs2103.v15_1j.jolt.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BottomPaneController {

	private JFXTextField commandBar;
	private JFXButton executeBtn;
	private JFXButton helpBtn;
	private JFXButton settingsBtn;

	private Stage primaryStage;
	private BorderPane bottomPane;
	private NotificationPane notificationPane;
	private PopOver helpPopOver;
	private PopOver aliasPopOver;

	private MainViewController con;
	private PopOverController helpPopOverController;
	private PopOverController aliasPopOverController;

	private ArrayList<String> commandHistory;
	private int commandHistoryPosition;
	private String tempCommand;

	private final int NOTIFICATION_TIMEOUT_DURATION = 3000;
	private final int DEFAULT_COMMAND_HISTORY_POS = -1;
	private final double BORDER_WIDTH = 14.0;
	private final double BTN_BORDER = 7.0;
	private final double EXECUTE_BTN_WIDTH = 80.0;
	private final double EXECUTE_BTN_HEIGHT = 30.0;
	private final double HELP_BTN_WIDTH = 30.0;
	private final double NOTIFICATION_PANE_HEIGHT = 50.0;

	//@@author A0139963N
	/**
	 * Constructor
	 * 
	 * @param con Reference to the MainViewController class
	 * @param primaryStage Reference to the primaryStage for FileChooser
	 */
	public BottomPaneController(MainViewController con, Stage primaryStage) {
		this.con = con;
		this.primaryStage = primaryStage;
		initialize();
	}

	/**
	 * Returns the Bottom Pane Element
	 * 
	 * @return Bottom Pane
	 */
	public BorderPane getBottomPane() {
		return bottomPane;
	}

	/**
	 * Initializes all the elements in the Bottom Pane
	 */
	private void initialize() {
		setUpCommandHistory();
		setUpCommandBar();
		setUpExecuteBtn();
		setUpSettingsBtn();
		setUpHelpPopOver();
		setUpAliasPopOver();
		setUpNotificationPane();
		setUpHelpBtn();
		setUpBottomPane();
	}

	private void setUpBottomPane() {
		bottomPane = new BorderPane();
		BorderPane.setMargin(bottomPane, new Insets(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));

		// Combines Buttons into a single Pane
		BorderPane buttonPane = new BorderPane();
		buttonPane.setLeft(settingsBtn);
		buttonPane.setCenter(helpBtn);
		buttonPane.setRight(executeBtn);

		bottomPane.setCenter(commandBar);
		bottomPane.setRight(buttonPane);
		bottomPane.setBottom(notificationPane);
	}

	private void setUpNotificationPane() {
		AnchorPane notificationPaneWrapper = new AnchorPane();
		notificationPaneWrapper.setMinHeight(NOTIFICATION_PANE_HEIGHT);

		notificationPane = new NotificationPane(notificationPaneWrapper);
		notificationPane.getStyleClass().add("notification-pane");
		notificationPane.setCloseButtonVisible(false);
		notificationPane.setShowFromTop(false);
	}

	private void setUpCommandHistory() {
		commandHistory = new ArrayList<String>();
		commandHistoryPosition = DEFAULT_COMMAND_HISTORY_POS;
		tempCommand = "";
	}

	private void setUpHelpPopOver() {
		helpPopOverController = new PopOverController("Help", 4);
		helpPopOver = helpPopOverController.getPopOver();
	}

	private void setUpAliasPopOver() {
		aliasPopOverController = new PopOverController("Aliases", 2);
		aliasPopOver = aliasPopOverController.getPopOver();
	}

	private void setUpCommandBar() {
		commandBar = new JFXTextField();
		commandBar.getStyleClass().add("command-bar");
		commandBar.setPromptText("Enter Command");
		commandBar.setOnAction(event -> handleUserCommand());
		
		//Process Keystrokes in the Command Bar
		commandBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				helpPopOver.hide();
				aliasPopOver.hide();
				if (keyEvent.getCode() == KeyCode.UP) {
					getPreviousCommand();
					keyEvent.consume();
				} else if (keyEvent.getCode() == KeyCode.DOWN) {
					getNextCommand();
					keyEvent.consume();
				}
			}
		});

		BorderPane.setMargin(commandBar, new Insets(0, BORDER_WIDTH, 0, 0));
	}

	private void setUpExecuteBtn() {
		executeBtn = new JFXButton("Execute");
		executeBtn.getStyleClass().add("button-raised");
		executeBtn.setPrefWidth(EXECUTE_BTN_WIDTH);
		executeBtn.setPrefHeight(EXECUTE_BTN_HEIGHT);
		executeBtn.setOnAction(event -> handleUserCommand());

		BorderPane.setMargin(executeBtn, new Insets(0, BTN_BORDER, 0, 0));
	}

	private void setUpHelpBtn() {
		helpBtn = new JFXButton("?");
		helpBtn.getStyleClass().add("help-button");
		helpBtn.setPrefWidth(HELP_BTN_WIDTH);
		helpBtn.setPrefHeight(EXECUTE_BTN_HEIGHT);
		helpBtn.setOnAction(event -> showHelp());

		BorderPane.setMargin(helpBtn, new Insets(0, BTN_BORDER, 0, 0));
	}

	private void setUpSettingsBtn() {
		settingsBtn = new JFXButton();
		settingsBtn.getStyleClass().add("button-raised");
		settingsBtn.setMaxWidth(HELP_BTN_WIDTH);
		settingsBtn.setMaxHeight(EXECUTE_BTN_HEIGHT);
		settingsBtn.setOnAction(event -> showFilePicker());

		Image cogIcon = new Image("images/Cog.png");
		settingsBtn.setGraphic(new ImageView(cogIcon));

		BorderPane.setMargin(settingsBtn, new Insets(0, BTN_BORDER, 0, 0));

	}

	/**
	 * Retrieve the Previous Command Entered
	 */
	private void getPreviousCommand() {
		if (commandHistoryPosition == DEFAULT_COMMAND_HISTORY_POS) {
			tempCommand = commandBar.getText();
		}

		if ((commandHistoryPosition + 1) < commandHistory.size()) {
			commandHistoryPosition++;
			commandBar.setText(commandHistory.get(commandHistoryPosition));
			commandBar.positionCaret(commandBar.getText().length());
		}
	}

	/**
	 * Retrieve the Command that was entered next
	 */
	private void getNextCommand() {
		if ((commandHistoryPosition - 1) >= 0) {
			commandHistoryPosition--;
			commandBar.setText(commandHistory.get(commandHistoryPosition));
			commandBar.positionCaret(commandBar.getText().length());
		} else if (commandHistoryPosition != DEFAULT_COMMAND_HISTORY_POS) {
			commandHistoryPosition--;
			commandBar.setText(tempCommand);
			commandBar.positionCaret(commandBar.getText().length());
		}

	}

	private void showFilePicker() {
		String filePath = con.getFilePath();

		FileChooser fileChooser = new FileChooser();
		File currentSaveFile = new File(filePath);
		fileChooser.setInitialDirectory(currentSaveFile.getParentFile());
		fileChooser.setInitialFileName(currentSaveFile.getName());

		String tempFilePath = fileChooser.showSaveDialog(primaryStage).getPath();

		if (tempFilePath != null) {
			filePath = tempFilePath;
			con.setFilePath(filePath);
		}
	}

	private void handleUserCommand() {
		if (!commandBar.getText().equals("")) {
			con.executeCommand(commandBar.getText());
			commandHistoryPosition = DEFAULT_COMMAND_HISTORY_POS;
			commandHistory.add(0, commandBar.getText());
			commandBar.setText("");
			commandBar.setPromptText("Enter Command");
		}
	}
	
	private void configureHelp(String helpType) {
		helpPopOverController.clear();
		helpPopOverController.addHeader("Syntax");
		helpPopOverController.addHeader("{id} is listed next to the Task/Event in []");
		helpPopOverController.addHeader("Note: elements inside parenthesis () are optional");
		helpPopOverController.addHeader("");

		switch (helpType) {

		case "common":
			configureHelpCommonData();
			break;

		case "add":
			configureHelpAddData();
			break;

		case "delete":
			configureHelpDeleteData();
			break;

		case "mark":
			configureHelpMarkData();
			break;

		case "unmark":
			configureHelpUnmarkData();
			break;

		case "date":
			configureHelpDateData();
			break;

		case "time":
			configureHelpTimeData();
			break;

		case "change":
			configureHelpChangeData();
			break;

		case "search":
			configureHelpSearchData();
			break;

		case "alias":
			configureHelpAliasData();
			break;

		default:
			configureHelpCommonData();
			break;
		}
	}
	
	private void configureHelpCommonData(){
		helpPopOverController.addMessage("{string}", 0);
		helpPopOverController.addMessage("E.g. Learn German vocab", 1);
		
		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("{string} by {date} {time}", 0);
		helpPopOverController.addMessage("E.g. Prepare meeting agenda by 2pm May 10,", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("Complete report by May 10  ({date} and {time} can swap position)", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("{string} (on) {date} from {time} to {time}", 0);
		helpPopOverController.addMessage("E.g. Meeting with boss on Tuesday from 2pm to 4pm,", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("Meeting with boss from 4pm to 6pm", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("delete {id}", 0);
		helpPopOverController.addMessage("E.g. delete d1", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("mark {id} (as done)", 0);
		helpPopOverController.addMessage("E.g. mark d1", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("unmark {id}", 0);
		helpPopOverController.addMessage("E.g. unmark d1", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("search {filter}, {filter} ...", 0);
		helpPopOverController.addMessage("E.g. search contain meeting, after next monday", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("undo", 0);
		helpPopOverController.addMessage("redo", 1);
	}
	
	private void configureHelpAddData(){
		helpPopOverController.addMessage("{string}", 0);
		helpPopOverController.addMessage("Adds a floating task.", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("E.g. Learn German vocab", 1);

		helpPopOverController.addEmptyDivider(2);

		helpPopOverController.addMessage("{string} by {date} {time}", 0);
		helpPopOverController.addMessage("Adds a task with deadline on the date and time specified", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("E.g. Prepare meeting agenda by 2pm May 10", 1);

		helpPopOverController.addEmptyDivider(2);

		helpPopOverController.addMessage("{string} (on) {date} from {time} to {time}", 0);
		helpPopOverController.addMessage("New event on the same date, with the specified timing.", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("E.g. Meeting with boss on Tuesday from 2pm to 4pm", 1);

		helpPopOverController.addEmptyDivider(2);

		helpPopOverController.addMessage("{string} from {date} {time} to {date} {time}", 0);
		helpPopOverController.addMessage("New event with the specified date and timing.", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("E.g. Overnight camp from 1pm 7 April to 11am 8 April", 1);

		helpPopOverController.addEmptyDivider(2);

		helpPopOverController.addMessage("{string} (at/on) {time} {date}", 0);
		helpPopOverController.addMessage("Create a new event on the specified date and time", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("E.g. Meeting at 10am tomorrow", 1);
	}
	
	private void configureHelpDeleteData(){
		helpPopOverController.addMessage("delete {id}", 0);
		helpPopOverController.addMessage("Delete Task or Event", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("E.g. delete d1", 1);
	}
	
	private void configureHelpMarkData(){
		helpPopOverController.addMessage("mark {id} (as done)", 0);
		helpPopOverController.addMessage("Marks the task/event as completed", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("E.g. mark d1", 1);
	}
	
	private void configureHelpUnmarkData(){
		helpPopOverController.addMessage("unmark {id}", 0);
		helpPopOverController.addMessage("Unmarks the task/event as not yet completed", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("E.g. unmark d1", 1);
	}
	
	private void configureHelpSearchData(){
		helpPopOverController.addMessage("search {filter}, {filter} ...", 0);
		helpPopOverController.addMessage("Search for tasks/events that satisfy all the filters", 1);
		helpPopOverController.addMessage("", 0);
		helpPopOverController.addMessage("E.g. search contain meeting, after next monday", 1);

		helpPopOverController.addEmptyDivider(2);

		helpPopOverController.addMessage("hide search", 0);
		helpPopOverController.addMessage("Hides the search results", 1);

		helpPopOverController.addEmptyDivider(2);

		helpPopOverController.addMessage("{filter} can be one of the following:", 0);
		helpPopOverController.addMessage("", 1);
		
		helpPopOverController.addMessage("before {date}", 0);
		helpPopOverController.addMessage("", 1);
		
		helpPopOverController.addMessage("after {date}", 0);
		helpPopOverController.addMessage("", 1);
		
		helpPopOverController.addMessage("on {date}", 0);
		helpPopOverController.addMessage("", 1);
		
		helpPopOverController.addMessage("at {time}", 0);
		helpPopOverController.addMessage("searching at the specified time +-30 minutes", 1);
		
		helpPopOverController.addMessage("before {time}", 0);
		helpPopOverController.addMessage("", 1);
		
		helpPopOverController.addMessage("after {time}", 0);
		helpPopOverController.addMessage("", 1);
		
		helpPopOverController.addMessage("before {date} {time}", 0);
		helpPopOverController.addMessage("", 1);
		
		helpPopOverController.addMessage("after {date} {time}", 0);
		helpPopOverController.addMessage("", 1);
		
		helpPopOverController.addMessage("(contain(s)) {words}", 0);
		helpPopOverController.addMessage("search for certain keywords", 1);
	}
	
	private void configureHelpChangeData(){
		helpPopOverController.addMessage("{date} can be omitted if only time needs to be changed.", 0);
		helpPopOverController.addMessage("{time} can be omitted if only date needs to be changed.", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("rename/change/change name {id} to {string}", 0);
		helpPopOverController.addMessage("Changes the name of a task/event", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("reschedule/change start {id} to {date} {time}", 0);
		helpPopOverController.addMessage("Changes the deadline of a task, or the starting time of an event", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("extend/change end {id} to {date} {time}", 0);
		helpPopOverController.addMessage("Changes the ending time of an event.", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("shift {id} to {date} {time}", 0);
		helpPopOverController.addMessage("Changes the starting time of an event, maintaining duration.", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("change {id} to from {date} {time} to {date} {time}", 0);
		helpPopOverController.addMessage("Changes both the starting and ending time of an event.", 1);
	
	}
	
	private void configureHelpDateData(){
		helpPopOverController.addMessage("today", 0);
		helpPopOverController.addMessage("", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("tomorrow", 0);
		helpPopOverController.addMessage("", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("{day}/{month}/{year}", 0);
		helpPopOverController.addMessage("e.g. 31/12/2016 (can also use - instead of /)", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("{day}/{month}", 0);
		helpPopOverController.addMessage("e.g. 31/12 (Current Year will be used)", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("{day} {month} {year}", 0);
		helpPopOverController.addMessage("e.g. 10 Jan 2015", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("{day} {month}", 0);
		helpPopOverController.addMessage("e.g. 10 Jan (Current Year will be used)", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("(this) {day of the week}", 0);
		helpPopOverController.addMessage("e.g. this tuesday means the 1st Tuesday after today", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("next {day of the week}", 0);
		helpPopOverController.addMessage("e.g. next tuesday means the Tuesday of next week", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("Notes:", 0);
		helpPopOverController.addMessage("", 1);
		
		helpPopOverController.addMessage("{day} or just the first 3 letters", 0);
		helpPopOverController.addMessage("e.g. Mon", 1);
		
		helpPopOverController.addMessage("{month} or just the first 3 letters", 0);
		helpPopOverController.addMessage("e.g. Jan", 1);
		
		helpPopOverController.addMessage("{day} can have ordinal", 0);
		helpPopOverController.addMessage("e.g. 8th", 1);
		
	}
	
	private void configureHelpTimeData(){
		
		helpPopOverController.addMessage("{hour} (24 hour format)", 0);
		helpPopOverController.addMessage("", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("{hour}.{minute} or {hour}:{minute}", 0);
		helpPopOverController.addMessage("e.g. 10.20, 23:35", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("{hour} am/p.m.", 0);
		helpPopOverController.addMessage("e.g. 11 a.m., 12pm", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("{hour}.{minute} am/p.m. or {hour}:{minute} am/p.m.", 0);
		helpPopOverController.addMessage("e.g. 10.30 pm", 1);
		
	}
	
	private void configureHelpAliasData(){
		
		helpPopOverController.addMessage("alias add {aliasable keyword} {word}", 0);
		helpPopOverController.addMessage("Makes {word} an alias for the {aliasable keyword}", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("alias delete {word}", 0);
		helpPopOverController.addMessage("Deletes {word} from the alias table", 1);

		helpPopOverController.addEmptyDivider(2);
		
		helpPopOverController.addMessage("alias list/show", 0);
		helpPopOverController.addMessage("Displays all aliases and their keyphrases", 1);
		
	}
	
	/**
	 * Displays a Notification
	 * 
	 * @param msg Message to be shown
	 */
	public void showNotification(String msg) {
		notificationPane.setText(msg);
		notificationPane.show();

		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(NOTIFICATION_TIMEOUT_DURATION);
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

	/**
	 * Calls the Application's Focus to the Command Bar
	 */
	public void focusCommandBar() {
		commandBar.requestFocus();
	}

	/**
	 * Toggles whether or not to show Help
	 */
	public void showHelp() {
		configureHelp("index");
		helpPopOver.show(helpBtn);
	}
	
	public void showHelp(String page){
		configureHelp(page);
		helpPopOver.show(helpBtn);
	}

	/**
	 * Show the Alias List
	 * 
	 * @param aliasList List of Aliases
	 */
	public void showAliases(Map<String, String> aliasList) {
		aliasPopOverController.clear();
		aliasPopOverController.addHeader("Aliases");
		aliasPopOverController.addHeader("");
		for (String key : aliasList.keySet()) {
			aliasPopOverController.addMessage(key + " = " + aliasList.get(key), 0);
		}
		aliasPopOver.show(commandBar);
	}
}
