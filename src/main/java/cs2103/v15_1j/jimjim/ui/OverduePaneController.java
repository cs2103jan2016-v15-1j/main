package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXCheckBox;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class OverduePaneController {

	private GridPane overdueGridPane;
	private ScrollPane overdueScrollPane;
	
	private DataLists masterList;
	private DataLists displayList;

	private MainViewController con;
	
	private DateTimeFormatter dateTimeFmt;
	
	private int rowNo;

	private final double COLUMN_WIDTH = 500.0;
	private final double ID_LABEL_WIDTH = 50.0;
	private final double NAME_LABEL_WIDTH = 250.0;
	private final double DATE_LABEL_WIDTH = 120.0;
	
	public OverduePaneController(MainViewController con, DataLists lists, DataLists displayLists){
		this.masterList = lists;
		this.displayList = displayLists;
		setMainViewController(con);
		initialize();
	}
	
	public ScrollPane getOverdueScrollPane(){
		return overdueScrollPane;
	}
	
	private void initialize(){
		setUpDateTimeFormatters();
		setUpOverduePane();
		showOverdueTasks();
	}
	
	private void setUpOverduePane(){
		overdueGridPane = new GridPane();
		overdueGridPane.prefWidth(COLUMN_WIDTH);
		overdueGridPane.setHgap(10);
		overdueGridPane.getStyleClass().add("pane");

		overdueScrollPane = new ScrollPane();
		overdueScrollPane.setContent(overdueGridPane);
		overdueScrollPane.getStyleClass().add("scrollpane");
		overdueScrollPane.setFocusTraversable(false);
		overdueScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		overdueScrollPane.getStyleClass().add("pane");
	}
	
	private void setUpDateTimeFormatters(){
		dateTimeFmt = DateTimeFormatter.ofPattern("dd MMM h:mm a");
	}

	public void refreshData(){
		showOverdueTasks();
	}
	
	private void showOverdueTasks(){
		rowNo = -1;
		overdueGridPane.getChildren().clear();
		
		Label overdueLabel = new Label("Overdue");
		overdueLabel.getStyleClass().add("overdue-label");
		overdueGridPane.add(overdueLabel, 0, ++rowNo, 4, 1);
		
		int noOfOverdue = 0;
		
		for(Event e: masterList.getEventsList()){
			if(checkOverdue(e)){
				add(e);
				noOfOverdue++;
			}
		}
		
		for(DeadlineTask t: masterList.getDeadlineTasksList()){
			if(checkOverdue(t)){
				add(t);
				noOfOverdue++;
			}
		}
		
		rowNo += noOfOverdue;
		
		if(noOfOverdue == 0){
			overdueGridPane.getChildren().remove(overdueLabel);
			
			Label noOverdueLabel = new Label("No Overdue Tasks");
			noOverdueLabel.getStyleClass().add("no-overdue-label");
			overdueGridPane.add(noOverdueLabel, 0, ++rowNo, 4, 1);
		}
		
	}
	
	private void add(Event event){
		displayList.addWithoutSorting(event);
		
		JFXCheckBox cb = new JFXCheckBox();
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(event.completedProperty());
		cb.setDisable(true);
		GridPane.setHalignment(cb, HPos.CENTER);
		overdueGridPane.addColumn(0, cb);

		Label idLabel = new Label("[E"+displayList.size('e')+"]");
		idLabel.getStyleClass().add("id-label");
		idLabel.setWrapText(true);
		idLabel.setPrefWidth(ID_LABEL_WIDTH);
		overdueGridPane.addColumn(1, idLabel);

		Label eventLabel = new Label();
		eventLabel.getStyleClass().add("event-label");
		eventLabel.textProperty().bindBidirectional(event.taskNameProperty());
		eventLabel.setTextAlignment(TextAlignment.LEFT);
		eventLabel.setWrapText(true);
		eventLabel.setPrefWidth(NAME_LABEL_WIDTH);
		overdueGridPane.addColumn(2, eventLabel);
		
		idLabel.getStyleClass().add("id-label");
		eventLabel.getStyleClass().add("event-label");

		for(EventTime et: event.getDateTimes()){
			Label dateLabel = new Label(et.toString());
			dateLabel.getStyleClass().add("event-label");
			dateLabel.setWrapText(true);
			dateLabel.setPrefWidth(DATE_LABEL_WIDTH);
			dateLabel.setTextAlignment(TextAlignment.RIGHT);
			overdueGridPane.addColumn(3, dateLabel);
		}
	}

	private void add(DeadlineTask task){
		displayList.addWithoutSorting(task);

		JFXCheckBox cb = new JFXCheckBox();
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(task.completedProperty());
		cb.setDisable(true);
		GridPane.setHalignment(cb, HPos.CENTER);
		overdueGridPane.addColumn(0, cb);

		Label idLabel = new Label("[D"+displayList.size('d')+"]");
		idLabel.setWrapText(true);
		idLabel.setPrefWidth(ID_LABEL_WIDTH);
		overdueGridPane.addColumn(1, idLabel);

		Label taskLabel = new Label();
		taskLabel.textProperty().bindBidirectional(task.taskNameProperty());
		taskLabel.setWrapText(true);
		taskLabel.setPrefWidth(NAME_LABEL_WIDTH);
		overdueGridPane.addColumn(2, taskLabel);

		Label dateTimeLabel = new Label(task.getDateTime().format(dateTimeFmt));
		dateTimeLabel.setTextAlignment(TextAlignment.RIGHT);
		dateTimeLabel.setWrapText(true);
		dateTimeLabel.setPrefWidth(DATE_LABEL_WIDTH);

		idLabel.getStyleClass().add("id-label");
		taskLabel.getStyleClass().add("task-label");
		dateTimeLabel.getStyleClass().add("task-label");
		
		overdueGridPane.addColumn(3, dateTimeLabel);
	}
	
	private boolean checkOverdue(Event e){
		LocalDateTime nowDateTime = LocalDateTime.now();
		boolean overdue = false;
		
		if(!e.getCompleted() && e.getLatestDateTime().isBefore(nowDateTime)){
			overdue = true;
		}

		return overdue;
	}
	
	private boolean checkOverdue(DeadlineTask t){
		LocalDateTime nowDateTime = LocalDateTime.now();
		boolean overdue = false;
		
		if(!t.getCompleted() && t.getDateTime().isBefore(nowDateTime)){
			overdue = true;
		}

		return overdue;
	}
	
	public void setMainViewController(MainViewController con){
		this.con = con;
	}
}
