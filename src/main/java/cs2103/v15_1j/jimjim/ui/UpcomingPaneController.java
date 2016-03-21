package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import cs2103.v15_1j.jimjim.model.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
		
		for(int i = 0; i < noOfDaysToShow; i++){
			LocalDateTime showDate = LocalDateTime.now().plusDays(i);
			DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
			
			Label dayLabel = new Label(showDate.format(dateFmt));
			upcomingGridPane.addColumn(0, dayLabel);
			
			int noOfEvents = addEventFromDate(showDate);
			int noOfTasks = addDeadlineTaskFromTime(showDate);
			
			if ((noOfEvents+noOfTasks) == 0){
				Label emptyLabel = new Label("No Events or Tasks");
				upcomingGridPane.addColumn(0, emptyLabel);
			}
		}
		
		Button showMoreBtn = new Button("Show More");
		showMoreBtn.setOnAction(event -> showMore());
		upcomingGridPane.addColumn(0, showMoreBtn);
	}
	
	private void showMore() {
		noOfDaysToShow += 7;
		showUpcoming();
	}
	
	private int addEventFromDate(LocalDateTime date){
		int noOfEvents = 0;
		
		for(Event event: lists.getEventsList()){
			if(checkOnDate(event, date)){
				noOfEvents++;
				
				BorderPane row = new BorderPane();
				row.setPrefHeight(20.0);
				row.setPrefWidth(COLUMN_WIDTH-20.0);

				Circle dot = new Circle(3.0, Color.RED);
				BorderPane.setAlignment(dot, Pos.CENTER);

				row.setLeft(dot);

				Label eventLabel = new Label();
				eventLabel.textProperty().bindBidirectional(event.taskNameProperty());
				eventLabel.setTextAlignment(TextAlignment.LEFT);
				BorderPane.setAlignment(dot, Pos.CENTER_LEFT);

				row.setCenter(eventLabel);

				for(EventTime et: event.getDateTimes()){

					Label dateLabel = new Label(et.toString());
					dateLabel.setTextAlignment(TextAlignment.RIGHT);
					BorderPane.setAlignment(dot, Pos.CENTER_RIGHT);

					row.setRight(dateLabel);
				}

				upcomingGridPane.addColumn(0, row);
			}
		}
		
		return noOfEvents;
	}
	
	private int addDeadlineTaskFromTime(LocalDateTime date){
		int noOfTasks = 0;
		
		for(DeadlineTask task: lists.getDeadlineTasksList()){
			if(checkOnDate(task, date)){
				noOfTasks++;
				
				BorderPane row = new BorderPane();
				row.setPrefHeight(20.0);
				row.setPrefWidth(COLUMN_WIDTH);

				CheckBox cb = new CheckBox();
				cb.selectedProperty().bindBidirectional(task.completedProperty());
				BorderPane.setAlignment(cb, Pos.CENTER);
				row.setLeft(cb);

				Label taskLabel = new Label();
				taskLabel.textProperty().bindBidirectional(task.taskNameProperty());
				BorderPane.setAlignment(taskLabel, Pos.CENTER_LEFT);
				row.setCenter(taskLabel);

				DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd MMM h:mm a");
				Label dateTimeLabel = new Label(task.getDateTime().format(dateFmt));
				dateTimeLabel.setTextAlignment(TextAlignment.RIGHT);
				BorderPane.setAlignment(dateTimeLabel, Pos.CENTER_RIGHT);
				row.setRight(dateTimeLabel);

				upcomingGridPane.addColumn(0, row);
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

		for(EventTime et: e.getDateTimes()){
			LocalDateTime eventStartDate = et.getStartDateTime();
			LocalDateTime eventEndDate = et.getEndDateTime();

			if(eventStartDate.equals(dateTime) || eventStartDate.isAfter(dateTime)){
				sameDate = true;
			} else if(eventEndDate.equals(dateTime) || eventEndDate.isAfter(dateTime)){
				sameDate = true;
			}
		}

		return sameDate;
	}
}
