package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

public class UpcomingPaneController {
	private GridPane upcomingGridPane;
	private ScrollPane upcomingScrollPane;

	private MainViewController con;

	private DataLists lists;

	private final double COLUMN_WIDTH = 300.0;

	private int noOfDaysToShow;

	public UpcomingPaneController(MainViewController con, DataLists lists){
		this.con = con;
		this.lists = lists;
		noOfDaysToShow = 7;
		initialize();
	}

	public ScrollPane getUpcomingPane(){
		return upcomingScrollPane;
	}

	private void initialize(){
		setUpUpcomingPane();
		showUpcoming();
	}

	private void setUpUpcomingPane(){
		upcomingGridPane = new GridPane();
		upcomingGridPane.prefWidth(COLUMN_WIDTH);
		upcomingGridPane.setHgap(10);

		upcomingScrollPane = new ScrollPane();
		upcomingScrollPane.setContent(upcomingGridPane);
		upcomingScrollPane.setFocusTraversable(false);
		upcomingScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	}

	public void refreshData(DataLists lists){
		this.lists = lists;
		showUpcoming();
	}

	private void showUpcoming(){
		upcomingGridPane.getChildren().clear();
		int rowNo = -1;

		for(int i = 0; i < noOfDaysToShow; i++){
			rowNo++;

			LocalDateTime showDate = LocalDateTime.now().plusDays(i);
			DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");

			Label dayLabel = new Label(showDate.format(dateFmt));
			upcomingGridPane.add(dayLabel, 0, rowNo, 4, 1);

			int noOfEvents = addEventFromDate(showDate);
			int noOfTasks = addDeadlineTaskFromTime(showDate);

			rowNo += (noOfEvents+noOfTasks);

			if ((noOfEvents+noOfTasks) == 0){
				rowNo++;
				Label emptyLabel = new Label("No Events or Tasks");
				upcomingGridPane.add(emptyLabel, 0, rowNo, 4, 1);
			}

		}

		rowNo++;
		Button showMoreBtn = new Button("Show More");
		showMoreBtn.setOnAction(event -> showMore());
		GridPane.setHalignment(showMoreBtn, HPos.CENTER);
		upcomingGridPane.add(showMoreBtn, 0, rowNo, 4, 1);
	}

	private void showMore() {
		noOfDaysToShow += 7;
		showUpcoming();
	}

	private int addEventFromDate(LocalDateTime date){
		int noOfEvents = 0;
		int counter = 0;

		for(Event event: lists.getEventsList()){
			counter++;
			if(checkOnDate(event, date)){
				noOfEvents++;

				Circle dot = new Circle(3.0, Color.RED);
				GridPane.setHalignment(dot, HPos.CENTER);
				upcomingGridPane.addColumn(0, dot);

				Label idLabel = new Label("[E"+counter+"]");
				upcomingGridPane.addColumn(1, idLabel);

				Label eventLabel = new Label();
				eventLabel.textProperty().bindBidirectional(event.taskNameProperty());
				eventLabel.setTextAlignment(TextAlignment.LEFT);
				upcomingGridPane.addColumn(2, eventLabel);

				for(EventTime et: event.getDateTimes()){

					Label dateLabel = new Label(et.toString());
					dateLabel.setTextAlignment(TextAlignment.RIGHT);
					upcomingGridPane.addColumn(3, dateLabel);
				}
			}
		}

		return noOfEvents;
	}

	private int addDeadlineTaskFromTime(LocalDateTime date){
		int noOfTasks = 0;
		int counter = 0;

		for(DeadlineTask task: lists.getDeadlineTasksList()){
			counter++;

			if(checkOnDate(task, date)){
				noOfTasks++;

				CheckBox cb = new CheckBox();
				cb.selectedProperty().bindBidirectional(task.completedProperty());
				GridPane.setHalignment(cb, HPos.CENTER);
				upcomingGridPane.addColumn(0, cb);

				Label idLabel = new Label("[D"+counter+"]");
				upcomingGridPane.addColumn(1, idLabel);

				Label taskLabel = new Label();
				taskLabel.textProperty().bindBidirectional(task.taskNameProperty());
				upcomingGridPane.addColumn(2, taskLabel);

				DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("h:mm a");
				Label dateTimeLabel = new Label(task.getDateTime().format(dateFmt));
				dateTimeLabel.setTextAlignment(TextAlignment.RIGHT);
				upcomingGridPane.addColumn(3, dateTimeLabel);
			}
		}

		return noOfTasks;
	}

	private boolean checkOnDate(DeadlineTask t, LocalDateTime dateTime){
		boolean sameDate = false;

		if(t.getDateTime().toLocalDate().equals(dateTime.toLocalDate())){
			sameDate = true;
		} 

		return sameDate;
	}

	private boolean checkOnDate(Event e, LocalDateTime dateTime){
		boolean sameDate = false;
		LocalDate date = dateTime.toLocalDate();

		for(EventTime et: e.getDateTimes()){
			LocalDate eventStartDate = et.getStartDateTime().toLocalDate();
			LocalDate eventEndDate = et.getEndDateTime().toLocalDate();

			if(eventStartDate.equals(date)){
				sameDate = true;
			} else if(eventEndDate.equals(date)){
				sameDate = true;
			} else if (eventStartDate.isBefore(date) && eventEndDate.isAfter(date)){
				sameDate = true;
			}
		}

		return sameDate;
	}
}
