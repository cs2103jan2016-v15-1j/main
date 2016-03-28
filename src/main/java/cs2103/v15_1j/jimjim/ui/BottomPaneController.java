package cs2103.v15_1j.jimjim.ui;

import java.util.ArrayList;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BottomPaneController {

	private TextField commandBar;
	private Button executeBtn;
	private Button helpBtn;

	private BorderPane bottomPane;
	private GridPane helpPane;
	private NotificationPane notificationPane;
	private PopOver helpPopOver;

	private MainViewController con;

	private ArrayList<String> commandHistory;
	private int commandHistoryPosition;
	private String tempCommand;

	private final double SMALL_BORDER_WIDTH = 7.0;
	private final double BORDER_WIDTH = 14.0;
	private final int notificationTimeoutLength = 3000;
	private final double COMMAND_BAR_RIGHT_BORDER = 105.0;
	private final double NOTIFICATION_PANE_HEIGHT = 30.0;
	private final double EXECUTE_BTN_WIDTH = 80.0;
	private final double EXECUTE_BTN_HEIGHT = 30.0;
	
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
		setUpNotificationPane();
		setUpHelpBtn();
		setUpBottomPane();
	}
	
	private void setUpBottomPane(){
		bottomPane = new BorderPane();
		BorderPane.setMargin(bottomPane, new Insets(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
		
		AnchorPane centreBottomPane = new AnchorPane();
		centreBottomPane.getChildren().addAll(commandBar, executeBtn);

		AnchorPane topBottomPane = new AnchorPane();
		topBottomPane.getChildren().addAll(helpBtn);
		
		bottomPane.setTop(topBottomPane);
		bottomPane.setCenter(centreBottomPane);
		bottomPane.setBottom(notificationPane);
	}
	
	private void setUpNotificationPane(){
		AnchorPane notificationPaneWrapper = new AnchorPane();
		notificationPaneWrapper.setMinHeight(NOTIFICATION_PANE_HEIGHT);
		
		notificationPane = new NotificationPane(notificationPaneWrapper);
		notificationPane.setShowFromTop(false);
	}

	private void setUpCommandHistory(){
		commandHistory = new ArrayList<String>();
		commandHistoryPosition = -1;
		tempCommand = "";
	}
	
	private void setUpHelpPopOver(){
		this.helpPane = new GridPane();
		helpPane.setHgap(10);
		BorderPane.setMargin(helpPane, new Insets(SMALL_BORDER_WIDTH, SMALL_BORDER_WIDTH, SMALL_BORDER_WIDTH, 
				SMALL_BORDER_WIDTH));
		
		BorderPane helpPaneWrapper = new BorderPane();
		helpPaneWrapper.setCenter(helpPane);
		
		this.helpPopOver = new PopOver(helpPaneWrapper);
		helpPopOver.setTitle("Help");
		helpPopOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);

		Label syntaxLabel = new Label("Syntax");
		helpPane.add(syntaxLabel, 0, 0, 2, 1);
		
		Label idLabel = new Label("{id} is listed next to the Task/Event in []");
		helpPane.add(idLabel, 0, 1, 2, 1);
		
		Label emptyLabel = new Label("");
		helpPane.add(emptyLabel, 0, 2, 2, 1);
		
		addHelpMessage("{name}");
		addHelpMessage("{name} by {date} {time}");
		addHelpMessage("{name} from {date} {time} to {date} {time}");
		addHelpMessage("delete {id}");
		addHelpMessage("mark {id} (as done)");
		addHelpMessage("unmark {id}");
		addHelpMessage("rename {id} to {name}");
		addHelpMessage("reschedule {id} to {date} {time}");
		addHelpMessage("extend {id} to {date}");
		addHelpMessage("search {filter}, {filter} ...");
		addHelpMessage("clear");
		addHelpMessage("help");
		addHelpMessage("undo");
		addHelpMessage("redo");
	}

	private void setUpCommandBar(){
		commandBar = new TextField();
		commandBar.setPromptText("Enter Command");
		commandBar.setOnAction(event -> handleCommand());
		commandBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
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
	
	private void setUpHelpBtn(){
		helpBtn = new Button("Help");
		helpBtn.setPrefWidth(EXECUTE_BTN_WIDTH);
		helpBtn.setPrefHeight(EXECUTE_BTN_HEIGHT);
		helpBtn.setOnAction(event -> showHelp());
		AnchorPane.setTopAnchor(helpBtn, BORDER_WIDTH);
		AnchorPane.setLeftAnchor(helpBtn, BORDER_WIDTH);
	}
	
	private void addHelpMessage(String helpMessage){
		Circle dot = new Circle(3.0, Color.BLUE);
		GridPane.setHalignment(dot, HPos.CENTER);
		helpPane.addColumn(0, dot);

		Label helpLabel = new Label(helpMessage);
		helpPane.addColumn(1, helpLabel);
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

	public void showHelp(){
		helpPopOver.show(helpBtn);
	}

	private void handleCommand() {
		if (commandBar.getText() != null) {
			con.executeCommand(commandBar.getText());
			commandHistory.add(0, commandBar.getText());
			commandBar.setText("");
		}
	}
}
