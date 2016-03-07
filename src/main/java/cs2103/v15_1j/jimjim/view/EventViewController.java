package cs2103.v15_1j.jimjim.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.PropertySheet;

import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.EventTime;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EventViewController {

	private ObservableList<Event> eventData;
	private MasterDetailPane eventPane;
	private MainViewController mainViewController;
	
	private final double BORDER_WIDTH = 14.0;
	private final double DETAIL_VIEW_FIRST_ROW_POSITION = 10.0;
	private final double DETAIL_VIEW_SECOND_ROW_POSITION = 50.0;
	private final double DETAIL_VIEW_THIRD_ROW_POSITION = 90.0;
	private final double DETAIL_VIEW_FOURTH_ROW_POSITION = 130.0;
	private final double DETAIL_VIEW_FIRST_COLUMN_POSITION = 10.0;
	private final double DETAIL_VIEW_SECOND_COLUMN_POSITION = 90.0;
	private final double DETAIL_VIEW_TEXTFIELD_WIDTH = 120.0;
	private final double DIVIDER_POSITION = 0.6;
	private final double EVENT_PANE_TOP_BORDER = 300.0;
	private final double EVENT_PANE_BOTTOM_BORDER = 80.0;
	private final double POSITION_DISTANCE = 40.0;
	
	public EventViewController(MainViewController mainViewController){
		this.mainViewController = mainViewController;
		
		eventData = FXCollections.observableArrayList();
	}
	
	public MasterDetailPane setUpEventPane(){
		eventPane = new MasterDetailPane();
		TableView<Event> eventTable = setUpTable();

		eventPane.setAnimated(true);
		eventPane.setMasterNode(eventTable);
		eventPane.setDetailNode(setUpDetailNode());
		eventPane.setDetailSide(Side.RIGHT);
		eventPane.setShowDetailNode(false);
		eventPane.setDividerPosition(DIVIDER_POSITION);
		
		AnchorPane.setTopAnchor(eventPane, EVENT_PANE_TOP_BORDER);
		AnchorPane.setLeftAnchor(eventPane, BORDER_WIDTH);
		AnchorPane.setRightAnchor(eventPane, BORDER_WIDTH);
		AnchorPane.setBottomAnchor(eventPane, EVENT_PANE_BOTTOM_BORDER);
		
		return eventPane;
	}

	private PropertySheet setUpDetailNode(){
		return new PropertySheet();
	}

	private AnchorPane setUpDetailNode(Event event){
		AnchorPane detailPane = new AnchorPane();
		Button closeBtn = setUpCloseBtn();
		Label nameLbl = setUpNameLbl();
		TextField nameTF = setUpNameTF(event);
		//Button deleteBtn = setUpDeleteBtn(event);
		List<Label> dateLbls = setUpDateLabels();
		List<DatePicker> datePickers = setUpDatePickers(event);
		

		detailPane.getChildren().addAll(closeBtn,nameLbl, nameTF);
		detailPane.getChildren().addAll(dateLbls);
		detailPane.getChildren().addAll(datePickers);

		return detailPane;
	}
	
	private Button setUpCloseBtn(){
		Button closeBtn = new Button("X");
		closeBtn.setOnAction(event -> {
			eventPane.setShowDetailNode(false);
		});
		AnchorPane.setTopAnchor(closeBtn, DETAIL_VIEW_FIRST_ROW_POSITION);
		AnchorPane.setLeftAnchor(closeBtn, DETAIL_VIEW_FIRST_COLUMN_POSITION);
		return closeBtn;
	}

	private Label setUpNameLbl(){
		Label nameLbl = new Label("Event Name:");
		AnchorPane.setTopAnchor(nameLbl, DETAIL_VIEW_SECOND_ROW_POSITION);
		AnchorPane.setLeftAnchor(nameLbl, DETAIL_VIEW_FIRST_COLUMN_POSITION);

		return nameLbl;
	}

	private TextField setUpNameTF(Event event){
		TextField nameTF = new TextField();
		nameTF.setPrefWidth(DETAIL_VIEW_TEXTFIELD_WIDTH);
		nameTF.textProperty().bindBidirectional(event.taskNameProperty());
		AnchorPane.setTopAnchor(nameTF, DETAIL_VIEW_SECOND_ROW_POSITION);
		AnchorPane.setLeftAnchor(nameTF, DETAIL_VIEW_SECOND_COLUMN_POSITION);
		return nameTF;
	}
	
	private List<Label> setUpDateLabels(){
		List<Label> dateLbls = new ArrayList<Label>();
		Label startDateLbl = setUpDateLabel("Start Date:", 0);
		Label endDateLbl = setUpDateLabel("End Date:", 1);
		dateLbls.add(startDateLbl);
		dateLbls.add(endDateLbl);
		
		return dateLbls;
	}
	
	private List<DatePicker> setUpDatePickers(Event event){
		List<EventTime> eventDateTimes = event.getDateTimes();
		List<DatePicker> eventDatePickers = new ArrayList<DatePicker>();
		
		int counter = 0;
		for(EventTime et: eventDateTimes){
			eventDatePickers.add(setUpDatePicker(et.startDateProperty(), counter));
			counter++;
			eventDatePickers.add(setUpDatePicker(et.endDateProperty(), counter));
			counter++;
		}
		
		return eventDatePickers;
	}

	private Label setUpDateLabel(String text, int row){
		Label lbl = new Label(text);
		AnchorPane.setTopAnchor(lbl, DETAIL_VIEW_THIRD_ROW_POSITION+(row*POSITION_DISTANCE));
		AnchorPane.setLeftAnchor(lbl, DETAIL_VIEW_FIRST_COLUMN_POSITION);

		return lbl;
	}

	private DatePicker setUpDatePicker(ObjectProperty<LocalDate> dateProperty, int row){
		DatePicker datePicker = new DatePicker();
		datePicker.setPrefWidth(DETAIL_VIEW_TEXTFIELD_WIDTH);
		datePicker.valueProperty().bindBidirectional(dateProperty);
		datePicker.setOnAction(action -> {
			LocalDate date = datePicker.getValue();
			//event.setDate(date);
			mainViewController.refreshUI();
		});
		AnchorPane.setTopAnchor(datePicker, DETAIL_VIEW_THIRD_ROW_POSITION+(row*POSITION_DISTANCE));
		AnchorPane.setLeftAnchor(datePicker, DETAIL_VIEW_SECOND_COLUMN_POSITION);

		return datePicker;
	}

	private Button setUpDeleteBtn(Event event){
		Button deleteBtn = new Button("Delete");
		deleteBtn.setOnAction(action -> {
			eventPane.setShowDetailNode(false);
			eventData.remove(event);
			//TO-DO: Remove from Storage
		});
		AnchorPane.setTopAnchor(deleteBtn, DETAIL_VIEW_FOURTH_ROW_POSITION);
		AnchorPane.setLeftAnchor(deleteBtn, DETAIL_VIEW_FIRST_COLUMN_POSITION);
		AnchorPane.setRightAnchor(deleteBtn, DETAIL_VIEW_FIRST_COLUMN_POSITION);
		return deleteBtn;
	}


	private TableView<Event> setUpTable(){
		TableColumn<Event, String> eventNameColumn = setUpEventNameColumn();
		TableColumn<Event, List<EventTime>> eventDateTimesColumn = setUpEventDateTimeColumn();
		TableView<Event> eventTable = setUpEventTable(eventNameColumn, eventDateTimesColumn);

		return eventTable;
	}

	private TableColumn<Event, String> setUpEventNameColumn(){
		TableColumn<Event, String> taskNameColumn = new TableColumn<Event, String>();
		taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());

		return taskNameColumn;
	}

	private TableColumn<Event, List<EventTime>> setUpEventDateTimeColumn(){
		TableColumn<Event, List<EventTime>> eventDateTimeColumn = new TableColumn<Event, List<EventTime>>();


		DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd.MM hh:mm");
		eventDateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimesProperty());
		eventDateTimeColumn.setCellFactory(column -> {
			return new TableCell<Event, List<EventTime>>() {
				@Override
				protected void updateItem(List<EventTime> item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						for(EventTime et: item){
							setText(dateFmt.format(et.getStartDateTime()) + " - " + dateFmt.format(et.getEndDateTime()));
						}
						
					}
				}
			};
		});

		return eventDateTimeColumn;
	}

	@SuppressWarnings("unchecked")
	private TableView<Event> setUpEventTable(TableColumn<Event, String> eventNameColumn, 
			TableColumn<Event, List<EventTime>> eventDateTimesColumn){
		TableView<Event> eventTable = new TableView<Event>();
		eventTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		eventTable.getColumns().addAll(eventNameColumn, eventDateTimesColumn);
		eventTable.setEditable(true);
		eventTable.setItems(eventData);

		eventTable.setRowFactory( tv -> {
			TableRow<Event> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
					Event rowData = row.getItem();
					eventPane.setDetailNode(setUpDetailNode(rowData));
					eventPane.setShowDetailNode(true);
				}
			});
			return row ;
		});

		return eventTable;
	}
	
	public void refreshUI(List<Event> tempList){
		eventData.clear();
		for(Event event: tempList){
			eventData.add(event);
		}
	}
	
	public ObservableList<Event> getEventData() {
		return eventData;
	}

}
