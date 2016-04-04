package cs2103.v15_1j.jimjim.ui;


import com.sun.javafx.scene.control.skin.DatePickerSkin;
import cs2103.v15_1j.jimjim.model.DataLists;
import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class DayPickerPaneController {

	private BorderPane dayPickerPane; 
	private GridPane dayDetailGridPane;
	private ScrollPane dayDetailScrollPane;

	private DatePicker calendarPicker;
	private DataLists masterList;
	private DataLists displayList;

	private MainViewController con;
	private TaskEventRowFactory rowFactory;

	private final double COLUMN_WIDTH = 500.0;

	public DayPickerPaneController(MainViewController con, DataLists masterList, DataLists displayList){
		this.masterList = masterList;
		this.displayList = displayList;
		setMainViewController(con);
		initialize();
	}

	public BorderPane getDayPickerPane(){
		return dayPickerPane;
	}

	public void refreshData(){
		rowFactory.clear();
		int noOfTaskEvents = rowFactory.showAllDeadlineTaskAndEvents(calendarPicker.getValue());
		if(noOfTaskEvents == 0){
			rowFactory.addLabel(calendarPicker.getValue());
			rowFactory.addLabel("No events or deadline tasks on this day", "red-label");
		}
	}

	private void initialize(){
		dayPickerPane = new BorderPane();
		setUpDatePicker();
		setUpDayDetailGridPane();
		setUpDayDetailScrollPane();
		setUpRowFactory();
	}

	private void setUpDatePicker(){
		calendarPicker = new DatePicker(LocalDate.now());
		calendarPicker.setFocusTraversable(false);
		calendarPicker.setId("date-picker");
		DatePickerSkin datePickerSkin = new DatePickerSkin(calendarPicker);
		datePickerSkin.getPopupContent().setOnMouseClicked(event -> con.updateData());
		Node datePickerNode = datePickerSkin.getPopupContent();

		BorderPane.setAlignment(datePickerNode, Pos.CENTER);
		dayPickerPane.setTop(datePickerNode);
	}

	private void setUpDayDetailGridPane(){
		dayDetailGridPane = new GridPane();
		dayDetailGridPane.maxWidth(COLUMN_WIDTH);
		dayDetailGridPane.setHgap(10);
	}

	private void setUpRowFactory(){
		rowFactory = new TaskEventRowFactory(masterList, displayList, dayDetailGridPane);
		refreshData();
	}

	private void setUpDayDetailScrollPane(){
		dayDetailScrollPane = new ScrollPane();
		dayDetailScrollPane.setContent(dayDetailGridPane);
		dayDetailScrollPane.getStyleClass().add("scrollpane");
		dayDetailScrollPane.setFocusTraversable(false);
		dayDetailScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		dayDetailScrollPane.setFitToWidth(true); 
		dayDetailScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		dayDetailScrollPane.setOnScrollFinished(event -> checkScrollPosition());

		BorderPane.setAlignment(dayDetailScrollPane, Pos.CENTER);
		dayPickerPane.setCenter(dayDetailScrollPane);
	}

	private void checkScrollPosition(){
		if(dayDetailScrollPane.getVvalue() == 0.0){
			calendarPicker.setValue(calendarPicker.getValue().minusDays(1));
			con.updateData();
		}
		else if (dayDetailScrollPane.getVvalue() == 1.0){
			calendarPicker.setValue(calendarPicker.getValue().plusDays(1));
			con.updateData();
		}
	}

	public void setMainViewController(MainViewController con){
		this.con = con;
	}
}
