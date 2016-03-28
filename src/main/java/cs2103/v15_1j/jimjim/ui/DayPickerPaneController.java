package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

public class DayPickerPaneController {

	private BorderPane dayPickerPane;
	private GridPane dayDetailGridPane;
	private ScrollPane dayDetailScrollPane;

	private DatePicker calendarPicker;
	private DataLists lists;

	private MainViewController con;
	
	private DateTimeFormatter dateFmt;

	private final double COLUMN_WIDTH = 300.0;

	public DayPickerPaneController(MainViewController con, DataLists lists){
		this.lists = lists;
		setMainViewController(con);
		initialize();
	}

	public BorderPane getDayPickerPane(){
		return dayPickerPane;
	}

	public void refreshData(DataLists lists){
		this.lists = lists;
		getDayDetails();
	}

	private void initialize(){
		dayPickerPane = new BorderPane();
		dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
		setUpDatePicker();
		setUpDayDetailGridPane();
		setUpDayDetailScrollPane();
	}

	private void setUpDatePicker(){
		calendarPicker = new DatePicker(LocalDate.now());
		calendarPicker.setFocusTraversable(false);
		DatePickerSkin datePickerSkin = new DatePickerSkin(calendarPicker);
		datePickerSkin.getPopupContent().setOnMouseClicked(event -> getDayDetails());
		Node datePickerNode = datePickerSkin.getPopupContent();

		BorderPane.setAlignment(datePickerNode, Pos.CENTER);
		dayPickerPane.setTop(datePickerNode);
	}

	private void setUpDayDetailGridPane(){
		dayDetailGridPane = new GridPane();
		dayDetailGridPane.prefWidth(COLUMN_WIDTH);
		dayDetailGridPane.setHgap(10);

		getDayDetails();
	}

	private void setUpDayDetailScrollPane(){
		dayDetailScrollPane = new ScrollPane();
		dayDetailScrollPane.setContent(dayDetailGridPane);
		dayDetailScrollPane.setFocusTraversable(false);
		dayDetailScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		BorderPane.setAlignment(dayDetailScrollPane, Pos.CENTER);
		dayPickerPane.setCenter(dayDetailScrollPane);
	}

	private void getDayDetails(){
		dayDetailGridPane.getChildren().clear();
		
		LocalDate currentDate = calendarPicker.getValue();
		LocalDate lastDate = getLastTaskEventDate();
		
		if(lastDate != null){
		
			int noOfDays = differenceInDays(currentDate, lastDate);
			int rowNo = 0;
			
			Label selectedDateLabel = new Label(currentDate.format(dateFmt));
			dayDetailGridPane.add(selectedDateLabel, 0, rowNo, 4, 1);

			int noOfEvents = addEventFromDate(currentDate);
			int noOfTasks = addDeadlineTaskFromTime(currentDate);

			rowNo += (noOfEvents+noOfTasks);

			if ((noOfEvents+noOfTasks) == 0){
				rowNo++;
				Label emptyLabel = new Label("No events or deadline tasks on this day");
				dayDetailGridPane.add(emptyLabel, 0, rowNo, 4, 1);
			}

			currentDate = currentDate.plusDays(1);
			
			while(noOfDays >= 0){
				noOfDays--;
				rowNo++;
	
				Label dayLabel = new Label(currentDate.format(dateFmt));
				dayDetailGridPane.add(dayLabel, 0, rowNo, 4, 1);
	
				noOfEvents = addEventFromDate(currentDate);
				noOfTasks = addDeadlineTaskFromTime(currentDate);
	
				rowNo += (noOfEvents+noOfTasks);
	
				if ((noOfEvents+noOfTasks) == 0){
					dayDetailGridPane.getChildren().remove(dayLabel);
				}

				currentDate = currentDate.plusDays(1);
				
			}
		}
		else {
			Label selectedDateLabel = new Label(currentDate.format(dateFmt));
			dayDetailGridPane.add(selectedDateLabel, 0, 0, 4, 1);
			
			Label emptyLabel = new Label("No events or deadline tasks on this day");
			dayDetailGridPane.add(emptyLabel, 0, 1, 4, 1);
		}
		
	}
	
	private int addEventFromDate(LocalDate date){
		int noOfEvents = 0;
		int counter = 0;

		for(Event event: lists.getEventsList()){
			counter++;
			if(checkOnDate(event, date)){
				noOfEvents++;

				Circle dot = new Circle(3.0, Color.RED);
				GridPane.setHalignment(dot, HPos.CENTER);
				dayDetailGridPane.addColumn(0, dot);

				Label idLabel = new Label("[E"+counter+"]");
				dayDetailGridPane.addColumn(1, idLabel);

				Label eventLabel = new Label();
				eventLabel.textProperty().bindBidirectional(event.taskNameProperty());
				eventLabel.setTextAlignment(TextAlignment.LEFT);
				dayDetailGridPane.addColumn(2, eventLabel);

				for(EventTime et: event.getDateTimes()){

					Label dateLabel = new Label(et.toString());
					dateLabel.setTextAlignment(TextAlignment.RIGHT);
					dayDetailGridPane.addColumn(3, dateLabel);
				}
			}
		}

		return noOfEvents;
	}

	private int addDeadlineTaskFromTime(LocalDate date){
		int noOfTasks = 0;
		int counter = 0;

		for(DeadlineTask task: lists.getDeadlineTasksList()){
			counter++;

			if(checkOnDate(task, date)){
				noOfTasks++;

				CheckBox cb = new CheckBox();
				cb.selectedProperty().bindBidirectional(task.completedProperty());
				cb.setDisable(true);
				GridPane.setHalignment(cb, HPos.CENTER);
				dayDetailGridPane.addColumn(0, cb);

				Label idLabel = new Label("[D"+counter+"]");
				dayDetailGridPane.addColumn(1, idLabel);

				Label taskLabel = new Label();
				taskLabel.textProperty().bindBidirectional(task.taskNameProperty());
				dayDetailGridPane.addColumn(2, taskLabel);

				DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("h:mm a");
				Label dateTimeLabel = new Label(task.getDateTime().format(dateFmt));
				dateTimeLabel.setTextAlignment(TextAlignment.RIGHT);
				dayDetailGridPane.addColumn(3, dateTimeLabel);
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

		for(EventTime et: e.getDateTimes()){
			LocalDate eventStartDate = et.getStartDateTime().toLocalDate();
			LocalDate eventEndDate = et.getEndDateTime().toLocalDate();

			if(eventStartDate.equals(date)){
				onDate = true;
			} else if(eventEndDate.equals(date)){
				onDate = true;
			} else if (eventStartDate.isBefore(date) && eventEndDate.isAfter(date)){
				onDate = true;
			}
		}

		return onDate;
	}

	private boolean checkAfterDate(DeadlineTask t, LocalDate date){
		boolean afterDate = false;
		LocalDate taskDate = t.getDateTime().toLocalDate();

		if(taskDate.isAfter(date)){
			afterDate = true;
		} 

		return afterDate;
	}

	private boolean checkAfterDate(Event e, LocalDate date){
		boolean afterDate = false;

		for(EventTime et: e.getDateTimes()){
			LocalDate eventStartDate = et.getStartDateTime().toLocalDate();
			LocalDate eventEndDate = et.getEndDateTime().toLocalDate();

			if(eventStartDate.isAfter(date)){
				afterDate = true;
			} else if(eventEndDate.isAfter(date)){
				afterDate = true;
			} 
		}

		return afterDate;
	}
	
	private boolean checkOnOrAfterDate(DeadlineTask t, LocalDate date){
		boolean onOrAfterDate = false;
		LocalDate taskDate = t.getDateTime().toLocalDate();

		if(taskDate.equals(date) || taskDate.isAfter(date)){
			onOrAfterDate = true;
		} 

		return onOrAfterDate;
	}

	private boolean checkOnOrAfterDate(Event e, LocalDate date){
		boolean onOrAfterDate = false;

		for(EventTime et: e.getDateTimes()){
			LocalDate eventStartDate = et.getStartDateTime().toLocalDate();
			LocalDate eventEndDate = et.getEndDateTime().toLocalDate();

			if(eventStartDate.equals(date) || eventStartDate.isAfter(date)){
				onOrAfterDate = true;
			} else if(eventStartDate.equals(date) || eventEndDate.isAfter(date)){
				onOrAfterDate = true;
			} 
		}

		return onOrAfterDate;
	}
	
	private LocalDate getEventDate(Event e, LocalDate currentEventDate){
		for(EventTime et: e.getDateTimes()){
			LocalDate eventStartDate = et.getStartDateTime().toLocalDate();
			LocalDate eventEndDate = et.getEndDateTime().toLocalDate();

			if(eventStartDate.isBefore(currentEventDate)){
				return currentEventDate;
			}
			else {
				return eventStartDate;
			}
		}
		
		return null;
	}
	
	private LocalDate getLastTaskEventDate(){
		if(!lists.getEventsList().isEmpty() && !lists.getDeadlineTasksList().isEmpty()){
			Event lastEvent = lists.getEventsList().get(lists.getEventsList().size() - 1);
			DeadlineTask lastDeadlineTask = lists.getDeadlineTasksList().get(lists.getDeadlineTasksList().size() - 1);
			
			if(lastEvent.getLatestDateTime().isAfter(lastDeadlineTask.getDateTime())){
				return lastEvent.getLatestDateTime().toLocalDate();
			} else {
				return lastDeadlineTask.getDateTime().toLocalDate();
			}
		}
		else if (!lists.getEventsList().isEmpty()){
			Event lastEvent = lists.getEventsList().get(lists.getEventsList().size() - 1);
			
			return lastEvent.getLatestDateTime().toLocalDate();
		}
		else if (!lists.getDeadlineTasksList().isEmpty()){
			DeadlineTask lastDeadlineTask = lists.getDeadlineTasksList().get(lists.getDeadlineTasksList().size() - 1);
			
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
