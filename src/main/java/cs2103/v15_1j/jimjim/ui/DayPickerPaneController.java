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
		getDayDetails();
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
		getDayDetails();
	}

	private void getDayDetails(){
		rowFactory.showTaskEventsFromDate(calendarPicker.getValue());
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

	public void setMainViewController(MainViewController con){
		this.con = con;
	}
}
