package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
		setUpDatePicker();
		setUpDayDetailGridPane();
		setUpDayDetailScrollPane();
	}

	private void setUpDatePicker(){
		calendarPicker = new DatePicker(LocalDate.now());
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
		int counter = 0;
		for(Event event: lists.getEventsList()){
			counter++;
			if(checkEventTaskDate(event)){
				Circle dot = new Circle(3.0, Color.RED);
				GridPane.setHalignment(dot, HPos.CENTER);
				dayDetailGridPane.addColumn(0, dot);

				Label eventIDLabel = new Label("[E"+counter+"]");
				dayDetailGridPane.addColumn(1, eventIDLabel);

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

		counter = 0;
		for(DeadlineTask task: lists.getDeadlineTasksList()){
			counter++;

			if(checkEventTaskDate(task)){
				CheckBox cb = new CheckBox();
				cb.selectedProperty().bindBidirectional(task.completedProperty());
				cb.setDisable(true);
				dayDetailGridPane.addColumn(0, cb);

				Label eventIDLabel = new Label("[T"+counter+"]");
				dayDetailGridPane.addColumn(1, eventIDLabel);

				Label taskLabel = new Label();
				taskLabel.textProperty().bindBidirectional(task.taskNameProperty());
				dayDetailGridPane.addColumn(2, taskLabel);

				DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mm a");
				Label timeLabel = new Label(task.getDateTime().format(timeFmt));
				timeLabel.setTextAlignment(TextAlignment.RIGHT);
				dayDetailGridPane.addColumn(3, timeLabel);
			}
		}
	}

	private boolean checkEventTaskDate(DeadlineTask t){
		boolean sameDate = false;

		LocalDate taskDate = t.getDateTime().toLocalDate();
		LocalDate selectedDate = calendarPicker.getValue();

		if(taskDate.equals(selectedDate)){
			sameDate = true;
		}

		return sameDate;
	}

	private boolean checkEventTaskDate(Event e){
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

	public void setMainViewController(MainViewController con){
		this.con = con;
	}
}
