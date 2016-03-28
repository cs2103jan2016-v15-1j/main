package cs2103.v15_1j.jimjim.ui;

import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXCheckBox;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

public class SearchPaneController {
	private GridPane searchGridPane;
	private ScrollPane searchScrollPane;

	private MainViewController con;

	private DataLists masterList;
	private DataLists searchResultsList;

	private final double COLUMN_WIDTH = 300.0;

	public SearchPaneController(MainViewController con, DataLists masterList){
		this.con = con;
		this.masterList = masterList;
		this.searchResultsList = new DataLists();
		initialize();
	}

	public ScrollPane getSearchPane(){
		return searchScrollPane;
	}

	private void initialize(){
		setUpSearchPane();
		showSearchResults();
	}

	private void setUpSearchPane(){
		searchGridPane = new GridPane();
		searchGridPane.prefWidth(COLUMN_WIDTH);
		searchGridPane.setHgap(10);
		searchGridPane.getStyleClass().add("pane");

		searchScrollPane = new ScrollPane();
		searchScrollPane.setContent(searchGridPane);
		searchScrollPane.getStyleClass().add("scrollpane");
		searchScrollPane.setFocusTraversable(false);
		searchScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		searchScrollPane.getStyleClass().add("pane");
	}

	public void refreshData(DataLists masterList, DataLists searchResultsList){
		this.masterList = masterList;
		this.searchResultsList = searchResultsList;
		showSearchResults();
	}

	private void showSearchResults(){
		searchGridPane.getChildren().clear();
		int rowNo = 0;

		Label titleLabel = new Label("Search Results");
		titleLabel.getStyleClass().add("header");
		searchGridPane.add(titleLabel, 0, rowNo, 4, 1);

		int counter = 0;
		
		for(Event event: searchResultsList.getEventsList()){
			counter++;
			Circle dot = new Circle(3.0, Color.RED);
			GridPane.setHalignment(dot, HPos.CENTER);
			searchGridPane.addColumn(0, dot);

			int id = masterList.indexOf(event) + 1;
			Label idLabel = new Label("[E"+id+"]");
			idLabel.getStyleClass().add("id-label");
			searchGridPane.addColumn(1, idLabel);

			Label eventLabel = new Label();
			eventLabel.getStyleClass().add("event-label");
			eventLabel.textProperty().bindBidirectional(event.taskNameProperty());
			eventLabel.setTextAlignment(TextAlignment.LEFT);
			searchGridPane.addColumn(2, eventLabel);

			for(EventTime et: event.getDateTimes()){
				Label dateLabel = new Label(et.toString());
				dateLabel.getStyleClass().add("event-label");
				dateLabel.setTextAlignment(TextAlignment.RIGHT);
				searchGridPane.addColumn(3, dateLabel);
			}
		}

		for(DeadlineTask task: searchResultsList.getDeadlineTasksList()){
			counter++;
			JFXCheckBox cb = new JFXCheckBox();
			cb.getStyleClass().add("custom-jfx-check-box");
			cb.selectedProperty().bindBidirectional(task.completedProperty());
			cb.setDisable(true);
			GridPane.setHalignment(cb, HPos.CENTER);
			searchGridPane.addColumn(0, cb);

			int id = masterList.indexOf(task) + 1;
			Label idLabel = new Label("[D"+id+"]");
			searchGridPane.addColumn(1, idLabel);

			Label taskLabel = new Label();
			taskLabel.textProperty().bindBidirectional(task.taskNameProperty());
			searchGridPane.addColumn(2, taskLabel);

			DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd MMM h:mm a");
			Label dateTimeLabel = new Label(task.getDateTime().format(dateFmt));
			dateTimeLabel.setTextAlignment(TextAlignment.RIGHT);

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
			
			searchGridPane.addColumn(3, dateTimeLabel);
		}

		for(FloatingTask task: searchResultsList.getFloatingTasksList()){
			counter++;
			JFXCheckBox cb = new JFXCheckBox();
			cb.getStyleClass().add("custom-jfx-check-box");
			cb.setDisable(true);
			cb.selectedProperty().bindBidirectional(task.completedProperty());
			searchGridPane.addColumn(0, cb);
			cb.setOnMouseClicked(event -> showSearchResults());

			int id = masterList.indexOf(task) + 1;
			Label idLabel = new Label("[F"+id+"]");
			searchGridPane.addColumn(1, idLabel);

			Label taskLabel = new Label();
			taskLabel.textProperty().bindBidirectional(task.taskNameProperty());
			searchGridPane.addColumn(2, taskLabel);

			if(!task.getCompleted()){
				idLabel.getStyleClass().add("id-label");
				taskLabel.getStyleClass().add("task-label");
			}
			else {
				idLabel.getStyleClass().add("completed-task-label");
				taskLabel.getStyleClass().add("completed-task-label");
			}

			Label blankLabel = new Label();
			searchGridPane.addColumn(3, blankLabel);
		}
		
		if(counter == 0){
			Label emptyLabel = new Label("No results match your search term");
			emptyLabel.getStyleClass().add("red-label");
			searchGridPane.add(emptyLabel, 0, 1, 4, 1);
		}
	}

}
