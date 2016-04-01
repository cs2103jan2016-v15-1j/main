package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.jfoenix.controls.JFXCheckBox;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class DayPickerPaneController {

	private BorderPane dayPickerPane; 
	private GridPane dayDetailGridPane;
	private ScrollPane dayDetailScrollPane;

	private DatePicker calendarPicker;
	private DataLists masterList;
	private DataLists displayList;

	private MainViewController con;
	
	private DateTimeFormatter dateFmt;
	private DateTimeFormatter dateTimeFmt;
	private DateTimeFormatter timeFmt;
	
	private int rowNo;

	private final double COLUMN_WIDTH = 500.0;
	private final double ID_LABEL_WIDTH = 50.0;
	private final double NAME_LABEL_WIDTH = 250.0;
	private final double DATE_LABEL_WIDTH = 120.0;

	public DayPickerPaneController(MainViewController con, DataLists lists, DataLists displayLists){
		this.masterList = lists;
		this.displayList = displayLists;
		setMainViewController(con);
		initialize();
	}

	public BorderPane getDayPickerPane(){
		return dayPickerPane;
	}

	public void refreshData(){
		getDayDetails();
	}

	private void initialize(){
		dayPickerPane = new BorderPane();
		setUpDateTimeFormatters();
		setUpDatePicker();
		setUpDayDetailGridPane();
		setUpDayDetailScrollPane();
	}
	
	private void setUpDateTimeFormatters(){
		dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
		dateTimeFmt = DateTimeFormatter.ofPattern("dd MMM h:mm a");
		timeFmt = DateTimeFormatter.ofPattern("h:mm a");
	}

	private void setUpDatePicker(){
		calendarPicker = new DatePicker(LocalDate.now());
		calendarPicker.setFocusTraversable(false);
		calendarPicker.setId("date-picker");
		DatePickerSkin datePickerSkin = new DatePickerSkin(calendarPicker);
		datePickerSkin.getPopupContent().setOnMouseClicked(event -> getDayDetails());
		Node datePickerNode = datePickerSkin.getPopupContent();

		BorderPane.setAlignment(datePickerNode, Pos.CENTER);
		dayPickerPane.setTop(datePickerNode);
	}
	
	private void setUpDayDetailGridPane(){
		dayDetailGridPane = new GridPane();
		dayDetailGridPane.maxWidth(COLUMN_WIDTH);
		dayDetailGridPane.setHgap(10);

		getDayDetails();
	}

	private void setUpDayDetailScrollPane(){
		dayDetailScrollPane = new ScrollPane();
		dayDetailScrollPane.setContent(dayDetailGridPane);
		dayDetailScrollPane.getStyleClass().add("scrollpane");
		dayDetailScrollPane.setFocusTraversable(false);
		dayDetailScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		dayDetailScrollPane.setFitToWidth(true); 

		BorderPane.setAlignment(dayDetailScrollPane, Pos.CENTER);
		dayPickerPane.setCenter(dayDetailScrollPane);
	}

	private void getDayDetails(){
		dayDetailGridPane.getChildren().clear();
		
		rowNo = -1;
		LocalDate currentDate = calendarPicker.getValue();
		LocalDate lastDate = getLastTaskEventDate();
		
		if(lastDate != null){
		
			int noOfDays = differenceInDays(currentDate, lastDate);
			showSelectedDay(currentDate);
			
			while(noOfDays >= 0){
				noOfDays--;
				currentDate = currentDate.plusDays(1);
				showDay(currentDate);
			}
		}
		else {
			addNoEventTaskLabel(currentDate);
		}
		
	}
	
	private void showSelectedDay(LocalDate selectedDate){
		Label selectedDateLabel = new Label(selectedDate.format(dateFmt));
		selectedDateLabel.getStyleClass().add("date-label");
		dayDetailGridPane.add(selectedDateLabel, 0, ++rowNo, 4, 1);

		int noOfEvents = addEventOnDate(selectedDate);
		int noOfTasks = addDeadlineTaskOnTime(selectedDate);

		rowNo += (noOfEvents+noOfTasks);

		if ((noOfEvents+noOfTasks) == 0){
			addNoEventTaskLabel(selectedDate);
		}
		
		Label emptyLabel = new Label("");
		dayDetailGridPane.add(emptyLabel, 0, ++rowNo, 4, 1);
	}
	
	private void showDay(LocalDate currentDate){
		Label dayLabel = new Label(currentDate.format(dateFmt));
		dayLabel.getStyleClass().add("date-label");
		dayDetailGridPane.add(dayLabel, 0, ++rowNo, 4, 1);

		int noOfEvents = addEventOnDate(currentDate);
		int noOfTasks = addDeadlineTaskOnTime(currentDate);

		rowNo += (noOfEvents+noOfTasks);
		
		Label tempEmptyLabel = new Label("");
		dayDetailGridPane.add(tempEmptyLabel, 0, ++rowNo, 4, 1);

		if ((noOfEvents+noOfTasks) == 0){
			dayDetailGridPane.getChildren().remove(dayLabel);
			dayDetailGridPane.getChildren().remove(tempEmptyLabel);
		}
	}
	
	private void addNoEventTaskLabel(LocalDate currentDate){
		
		if(rowNo == -1){
			Label selectedDateLabel = new Label(currentDate.format(dateFmt));
			selectedDateLabel.getStyleClass().add("date-label");
			dayDetailGridPane.add(selectedDateLabel, 0, ++rowNo, 4, 1);
		}
		
		Label emptyLabel = new Label("No events or deadline tasks on this day");
		emptyLabel.getStyleClass().add("red-label");
		dayDetailGridPane.add(emptyLabel, 0, ++rowNo, 4, 1);
	}
	
	private void addToDayDetails(Event event){
		displayList.addWithoutSorting(event);
		
		JFXCheckBox cb = new JFXCheckBox();
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(event.completedProperty());
		cb.setDisable(true);
		GridPane.setHalignment(cb, HPos.CENTER);
		dayDetailGridPane.addColumn(0, cb);

		Label idLabel = new Label("[E"+displayList.size('e')+"]");
		idLabel.getStyleClass().add("id-label");
		idLabel.setWrapText(true);
		idLabel.setPrefWidth(ID_LABEL_WIDTH);
		dayDetailGridPane.addColumn(1, idLabel);

		Label eventLabel = new Label();
		eventLabel.getStyleClass().add("event-label");
		eventLabel.textProperty().bindBidirectional(event.taskNameProperty());
		eventLabel.setTextAlignment(TextAlignment.LEFT);
		eventLabel.setWrapText(true);
		eventLabel.setPrefWidth(NAME_LABEL_WIDTH);
		dayDetailGridPane.addColumn(2, eventLabel);
		
		Label dateLabel = new Label(event.toDateTimeString());
		dateLabel.setWrapText(true);
		dateLabel.setPrefWidth(DATE_LABEL_WIDTH);
		dateLabel.setTextAlignment(TextAlignment.RIGHT);
		dayDetailGridPane.addColumn(3, dateLabel);
		
		if(!event.getCompleted()){
			idLabel.getStyleClass().add("id-label");
			eventLabel.getStyleClass().add("event-label");
			dateLabel.getStyleClass().add("event-label");
		}
		else {
			idLabel.getStyleClass().add("completed-task-label");
			eventLabel.getStyleClass().add("completed-task-label");
			dateLabel.getStyleClass().add("completed-task-label");
		}
	}

	private void addToDayDetails(DeadlineTask task){
		displayList.addWithoutSorting(task);

		JFXCheckBox cb = new JFXCheckBox();
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(task.completedProperty());
		cb.setDisable(true);
		GridPane.setHalignment(cb, HPos.CENTER);
		dayDetailGridPane.addColumn(0, cb);

		Label idLabel = new Label("[D"+displayList.size('d')+"]");
		idLabel.setWrapText(true);
		idLabel.setPrefWidth(ID_LABEL_WIDTH);
		dayDetailGridPane.addColumn(1, idLabel);

		Label taskLabel = new Label();
		taskLabel.textProperty().bindBidirectional(task.taskNameProperty());
		taskLabel.setWrapText(true);
		taskLabel.setPrefWidth(NAME_LABEL_WIDTH);
		dayDetailGridPane.addColumn(2, taskLabel);

		Label dateTimeLabel = new Label(task.getDateTime().format(timeFmt));
		
		dateTimeLabel.setTextAlignment(TextAlignment.RIGHT);
		dateTimeLabel.setWrapText(true);
		dateTimeLabel.setPrefWidth(DATE_LABEL_WIDTH);

		if(!task.getCompleted()){
			idLabel.getStyleClass().add("id-label");
			taskLabel.getStyleClass().add("task-label");
			dateTimeLabel.getStyleClass().add("task-label");
		}
		else {
			idLabel.getStyleClass().add("completed-task-label");
			taskLabel.getStyleClass().add("completed-task-label");
			dateTimeLabel.getStyleClass().add("completed-task-label");
		}
		
		dayDetailGridPane.addColumn(3, dateTimeLabel);
	}
	
	private int addEventOnDate(LocalDate date){
		int noOfEvents = 0;

		for(Event event: masterList.getEventsList()){
			boolean showEvent = checkOnDate(event, date);
			
			if(!date.equals(calendarPicker.getValue()) && event.getCompleted()){
				showEvent = false;
			}
			
			if(showEvent){
				noOfEvents++;
				addToDayDetails(event);
			}
		}

		return noOfEvents;
	}

	private int addDeadlineTaskOnTime(LocalDate date){
		int noOfTasks = 0;

		for(DeadlineTask task: masterList.getDeadlineTasksList()){
			boolean showTask = checkOnDate(task, date);
			
			if(!date.equals(calendarPicker.getValue()) && task.getCompleted()){
				showTask = false;
			}

			if(showTask){
				noOfTasks++;
				addToDayDetails(task);
			}
		}

		return noOfTasks;
	}
	
	private boolean checkOnDate(DeadlineTask t, LocalDate date){
		boolean onDate = false;
		LocalDate taskDate = t.getDateTime().toLocalDate();

		if(taskDate.equals(date)){
			onDate = true;
		} 

		return onDate;
	}

	private boolean checkOnDate(Event e, LocalDate date){
		boolean onDate = false;

		LocalDate eventStartDate = e.getStartDateTime().toLocalDate();
		LocalDate eventEndDate = e.getEndDateTime().toLocalDate();

		if(eventStartDate.equals(date)){
			onDate = true;
		} else if(eventEndDate.equals(date)){
			onDate = true;
		} else if (eventStartDate.isBefore(date) && eventEndDate.isAfter(date)){
			onDate = true;
		}

		return onDate;
	}
	
	private LocalDate getLastTaskEventDate(){
		if(!masterList.getEventsList().isEmpty() && !masterList.getDeadlineTasksList().isEmpty()){
			Event lastEvent = masterList.getEventsList().get(masterList.getEventsList().size() - 1);
			DeadlineTask lastDeadlineTask = masterList.getDeadlineTasksList().get(masterList.getDeadlineTasksList().size() - 1);
			
			if(lastEvent.getEndDateTime().isAfter(lastDeadlineTask.getDateTime())){
				return lastEvent.getEndDateTime().toLocalDate();
			} else {
				return lastDeadlineTask.getDateTime().toLocalDate();
			}
		}
		else if (!masterList.getEventsList().isEmpty()){
			Event lastEvent = masterList.getEventsList().get(masterList.getEventsList().size() - 1);
			
			return lastEvent.getEndDateTime().toLocalDate();
		}
		else if (!masterList.getDeadlineTasksList().isEmpty()){
			DeadlineTask lastDeadlineTask = masterList.getDeadlineTasksList().get(masterList.getDeadlineTasksList().size() - 1);
			
			return lastDeadlineTask.getDateTime().toLocalDate();
		}
		else {
			return null;
		}
		
	}
	
	private int differenceInDays(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }
	
	public void setMainViewController(MainViewController con){
		this.con = con;
	}
}
