package cs2103.v15_1j.jimjim.ui;

import java.util.ArrayList;
import java.util.Map;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BottomPaneController {

	private JFXTextField commandBar;
	private JFXButton executeBtn;
	private JFXButton helpBtn;

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

	private final double BORDER_WIDTH = 14.0;
	private final int notificationTimeoutLength = 3000;
	private final double NOTIFICATION_PANE_HEIGHT = 50.0;
	private final double BTN_BORDER = 7.0;
	private final double EXECUTE_BTN_WIDTH = 80.0;
	private final double EXECUTE_BTN_HEIGHT = 30.0;
	private final double HELP_BTN_WIDTH = 30.0;

	public BottomPaneController(MainViewController con){
		this.con = con;
		initialize();
	}

	public BorderPane getBottomPane(){
		return bottomPane;
	}

	private void initialize(){
		setUpCommandHistory();
		setUpCommandBar();
		setUpExecuteBtn();
		setUpHelpPopOver();
		setUpAliasPopOver();
		setUpNotificationPane();
		setUpHelpBtn();
		setUpBottomPane();
	}

	private void setUpBottomPane(){
		bottomPane = new BorderPane();
		BorderPane.setMargin(bottomPane, new Insets(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));

		BorderPane buttonPane = new BorderPane();
		buttonPane.setLeft(helpBtn);
		buttonPane.setRight(executeBtn);
		
		bottomPane.setCenter(commandBar);
		bottomPane.setRight(buttonPane);
		bottomPane.setBottom(notificationPane);
	}

	private void setUpNotificationPane(){
		AnchorPane notificationPaneWrapper = new AnchorPane();
		notificationPaneWrapper.setMinHeight(NOTIFICATION_PANE_HEIGHT);

		notificationPane = new NotificationPane(notificationPaneWrapper);
		notificationPane.getStyleClass().add("notification-pane");
		notificationPane.setCloseButtonVisible(false);
		notificationPane.setShowFromTop(false);
	}

	private void setUpCommandHistory(){
		commandHistory = new ArrayList<String>();
		commandHistoryPosition = -1;
		tempCommand = "";
	}

	private void setUpHelpPopOver(){
		helpPopOverController = new PopOverController("Help", 4);
		helpPopOver = helpPopOverController.getPopOver();
		
		helpPopOverController.addHeader("Syntax");
		helpPopOverController.addHeader("{id} is listed next to the Task/Event in []");
		helpPopOverController.addHeader("");

		helpPopOverController.addMessage("{name}", 0);
		helpPopOverController.addMessage("{name} by {date} {time}", 0);
		helpPopOverController.addMessage("{name} from {date} {time} to {date} {time}", 0);
		helpPopOverController.addMessage("delete {id}", 0);
		helpPopOverController.addMessage("mark {id} (as done)", 0);
		helpPopOverController.addMessage("unmark {id}", 0);
		helpPopOverController.addMessage("rename {id} to {name}", 0);
		helpPopOverController.addMessage("reschedule {id} to {date} {time}", 1);
		helpPopOverController.addMessage("extend {id} to {date}", 1);
		helpPopOverController.addMessage("search {filter}, {filter} ...", 1);
		helpPopOverController.addMessage("hide search", 1);
		helpPopOverController.addMessage("help", 1);
		helpPopOverController.addMessage("undo", 1);
		helpPopOverController.addMessage("redo", 1);
	}
	
	private void setUpAliasPopOver(){
		aliasPopOverController = new PopOverController("Aliases", 2);
		aliasPopOver = aliasPopOverController.getPopOver();
	}

	private void setUpCommandBar(){
		commandBar = new JFXTextField();
		commandBar.getStyleClass().add("command-bar");
		commandBar.setPromptText("Enter Command");
		commandBar.setOnAction(event -> handleCommand());
		commandBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				helpPopOver.hide();
				aliasPopOver.hide();
				if (keyEvent.getCode() == KeyCode.UP) {
					previousCommand();
					keyEvent.consume();
				}
				else if (keyEvent.getCode() == KeyCode.DOWN) {
					nextCommand();
					keyEvent.consume();
				}
			}
		});
		
		BorderPane.setMargin(commandBar, new Insets(0, BORDER_WIDTH, 0, 0));
	}

	private void setUpExecuteBtn(){
		executeBtn = new JFXButton("Execute");
		executeBtn.getStyleClass().add("button-raised");
		executeBtn.setPrefWidth(EXECUTE_BTN_WIDTH);
		executeBtn.setPrefHeight(EXECUTE_BTN_HEIGHT);
		executeBtn.setOnAction(event -> handleCommand());
		BorderPane.setMargin(executeBtn, new Insets(0, BTN_BORDER, 0, 0));
	}

	private void setUpHelpBtn(){
		helpBtn = new JFXButton("?");
		helpBtn.getStyleClass().add("help-button");
		helpBtn.setPrefWidth(HELP_BTN_WIDTH);
		helpBtn.setPrefHeight(EXECUTE_BTN_HEIGHT);
		helpBtn.setOnAction(event -> toggleHelp());
		BorderPane.setMargin(helpBtn, new Insets(0, BTN_BORDER, 0, 0));
	}


	private void previousCommand(){
		if(commandHistoryPosition == -1){
			tempCommand = commandBar.getText();
		}

		if((commandHistoryPosition + 1) < commandHistory.size()){
			commandHistoryPosition++;
			commandBar.setText(commandHistory.get(commandHistoryPosition));
			commandBar.positionCaret(commandBar.getText().length());
		}
	}

	private void nextCommand(){
		if((commandHistoryPosition - 1) >= 0){
			commandHistoryPosition--;
			commandBar.setText(commandHistory.get(commandHistoryPosition));
			commandBar.positionCaret(commandBar.getText().length());
		}
		else if (commandHistoryPosition != -1) {
			commandHistoryPosition--;
			commandBar.setText(tempCommand);
			commandBar.positionCaret(commandBar.getText().length());
		}

	}

	public void showNotification(String msg){
		notificationPane.setText(msg);
		notificationPane.show();

		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(notificationTimeoutLength);
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

	public void focusCommandBar(){
		commandBar.requestFocus();
	}

	public void toggleHelp(){
		helpPopOver.show(helpBtn);
	}
	
	public void showAliases(Map<String, String> aliasList){
		aliasPopOverController.clear();
		aliasPopOverController.addHeader("Aliases");
		aliasPopOverController.addHeader("");
		for(String key: aliasList.keySet()){
			aliasPopOverController.addMessage(key + " = " + aliasList.get(key), 0);
		}
		aliasPopOver.show(commandBar);
	}

	private void handleCommand() {
		if (!commandBar.getText().equals("")) {
			helpPopOver.hide();
			con.executeCommand(commandBar.getText());
			commandHistory.add(0, commandBar.getText());
			commandBar.setText("");
			commandBar.setPromptText("Enter Command");
		}
	}
}


