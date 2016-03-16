package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.MasterDetailPane;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MainViewController {

	private AnchorPane mainPane;
	private AnchorPane calendarPane;
	private GridPane dayEventTaskPane;
	private GridPane floatingTaskPane;
	private TextField commandBar;
	private DatePicker calendarPicker;
	private Label statusLbl;
	private Button executeBtn;

	private JJUI uiController;
	
	private List<Task> taskList;
	private List<Event> eventList;

	private final double BORDER_WIDTH = 14.0;
	private final double COMMAND_BAR_RIGHT_BORDER = 105.0;
	private final double COLUMN_WIDTH = 300.0;
	private final double EXECUTE_BTN_WIDTH = 80.0;
	private final double EXECUTE_BTN_HEIGHT = 30.0;
	private final double EVENT_LBL_TOP_BORDER = 270.0;
	private final double NO_BORDER = 0.0;
	private final double STATUS_LBL_BOTTOM_BORDER = 47.0;
	private final double WINDOW_WIDTH = 700.0;
	private final double WINDOW_HEIGHT = 600.0;

	public MainViewController() {
	}

	public AnchorPane initialize() {
		return setUpMainView();
	}

	private AnchorPane setUpMainView(){
		setUpCalendar();
		setUpDayTaskEventPane();
		setUpFloatingTaskPane();
		setUpCommandBar();
		setUpExecuteBtn();
		setUpStatusLbl();
		setUpMainPane();
		
		refreshUI();
		
		return mainPane;
	}
	
	private void setUpCalendar(){

		calendarPane = new AnchorPane();
		calendarPicker = new DatePicker(LocalDate.now());
		DatePickerSkin datePickerSkin = new DatePickerSkin(calendarPicker);
        datePickerSkin.getPopupContent().setOnMouseClicked(event -> refreshUI());
        Node popupContent = datePickerSkin.getPopupContent();

		AnchorPane.setTopAnchor(popupContent, NO_BORDER);
		AnchorPane.setLeftAnchor(popupContent, NO_BORDER);
		AnchorPane.setRightAnchor(popupContent, NO_BORDER);
		AnchorPane.setBottomAnchor(popupContent, NO_BORDER);
		
        calendarPane.prefHeight(COLUMN_WIDTH);
        calendarPane.prefWidth(COLUMN_WIDTH);
        calendarPane.getChildren().add(popupContent);
        AnchorPane.setTopAnchor(calendarPane, BORDER_WIDTH);
		AnchorPane.setLeftAnchor(calendarPane, BORDER_WIDTH);
	}
	
	private void setUpDayTaskEventPane(){
		dayEventTaskPane = new GridPane();
		dayEventTaskPane.prefHeight(COLUMN_WIDTH);
		dayEventTaskPane.prefWidth(COLUMN_WIDTH);
		
        AnchorPane.setTopAnchor(dayEventTaskPane, 250.0);
		AnchorPane.setLeftAnchor(dayEventTaskPane, BORDER_WIDTH);
	}
	
	private void setUpFloatingTaskPane(){
		floatingTaskPane = new GridPane();
		floatingTaskPane.prefHeight(COLUMN_WIDTH);
		floatingTaskPane.prefWidth(COLUMN_WIDTH);
		
        AnchorPane.setTopAnchor(floatingTaskPane, BORDER_WIDTH);
		AnchorPane.setLeftAnchor(floatingTaskPane, COLUMN_WIDTH);
	}
	
	private void populateDayTaskEventPane(){
		dayEventTaskPane.getChildren().clear();
		for(Event event: eventList){
			AnchorPane row = new AnchorPane();
			row.setPrefHeight(20.0);
			row.setPrefWidth(COLUMN_WIDTH);
			
			if(checkDatePickerDate(event)){
			
				Label eventLabel = new Label();
				eventLabel.textProperty().bindBidirectional(event.taskNameProperty());
				AnchorPane.setTopAnchor(eventLabel, 5.0);
				AnchorPane.setLeftAnchor(eventLabel, 5.0);
				
				row.getChildren().add(eventLabel);
				
				for(EventTime et: event.getDateTimes()){
					Label dateLabel = new Label(et.toString());
					dateLabel.setTextAlignment(TextAlignment.RIGHT);
					AnchorPane.setTopAnchor(dateLabel, 5.0);
					AnchorPane.setRightAnchor(dateLabel, 5.0);
					
					row.getChildren().add(dateLabel);
				}
			}
			dayEventTaskPane.addColumn(0, row);
		}
	}
	
	private void populateFloatingTaskPane(){
		floatingTaskPane.getChildren().clear();
		for(Task t: taskList){
			AnchorPane row = new AnchorPane();
			row.setPrefHeight(20.0);
			row.setPrefWidth(COLUMN_WIDTH);
			
			CheckBox cb = new CheckBox();
			cb.selectedProperty().bindBidirectional(t.completedProperty());
			AnchorPane.setTopAnchor(cb, 5.0);
			AnchorPane.setLeftAnchor(cb, 5.0);
			
			Label taskLabel = new Label();
			taskLabel.textProperty().bindBidirectional(t.taskNameProperty());
			AnchorPane.setTopAnchor(taskLabel, 5.0);
			AnchorPane.setLeftAnchor(taskLabel, 25.0);
			
			row.getChildren().addAll(cb, taskLabel);
			
			floatingTaskPane.addColumn(0, row);
		}
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
		mainPane.getChildren().addAll(calendarPane, dayEventTaskPane, floatingTaskPane, commandBar, executeBtn, statusLbl);
	}

	private void refreshUI(){
		uiController.refreshUI();
		populateDayTaskEventPane();
		populateFloatingTaskPane();
	}

	public void updateData(List<TaskEvent> tempList){
		taskList = new ArrayList<Task>();
		eventList = new ArrayList<Event>();

		for(TaskEvent te: tempList){
			if(te instanceof Task){
				taskList.add((Task) te);
			}
			else if(te instanceof Event){
				eventList.add((Event) te);
			}
		}
	}

	public void focusCommandBar(){
		commandBar.requestFocus();
	}

	private void handleCommand() {
		if (commandBar.getText() != null) {
			String status = uiController.executeCommand(commandBar.getText());
			refreshUI();
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
	
	private boolean checkDatePickerDate(Event e){
		boolean sameDate = false;
		
		for(EventTime et: e.getDateTimes()){
			LocalDate eventStartDate = et.getStartDateTime().toLocalDate();
			LocalDate eventEndDate = et.getEndDateTime().toLocalDate();
			LocalDate selectedDate = calendarPicker.getValue();
			if(eventStartDate.equals(selectedDate)){
				sameDate = true;
			} else if(eventEndDate.equals(selectedDate)){
				sameDate = true;
			} else if(eventStartDate.isBefore(selectedDate) && eventEndDate.isAfter(selectedDate)){
				sameDate = true;
			}
		}
		
		return sameDate;
	}

}
