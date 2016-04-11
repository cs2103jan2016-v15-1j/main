package cs2103.v15_1j.jolt.ui;

import java.time.LocalDate;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.Event;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
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

	private DatePicker dayPicker;
	
	private DataLists masterList;
	private DataLists displayList;

	private MainViewController con;
	private TaskEventRowFactory rowFactory;

	private final double COLUMN_WIDTH = 500.0;
	private final int ADD_EVENT_TIMEOUT_DURATION = 3000;
	private final int GRID_HORIZONTAL_SPACING = 10;

	//@@author A0139963N
	/**
	 * Constructor
	 * @param con Reference to MainViewController
	 * @param masterList Master List of Tasks and Events
	 * @param displayList List of Tasks and Events Displayed
	 */
	public DayPickerPaneController(MainViewController con, DataLists masterList, DataLists displayList) {
		this.masterList = masterList;
		this.displayList = displayList;
		this.con = con;
		initialize();
	}

	/**
	 * Returns the Day Picker Element
	 * @return Day Picker Pane
	 */
	public BorderPane getDayPickerPane() {
		return dayPickerPane;
	}

	/**
	 * Updates the UI
	 */
	public void refreshData() {
		rowFactory.clear();
		int noOfTaskEvents = rowFactory.showAllDeadlineTaskAndEvents(dayPicker.getValue());
		if (noOfTaskEvents == 0) {
			rowFactory.addLabel(dayPicker.getValue());
			rowFactory.addLabel("No events or deadline tasks on this day", "red-label");
		}
	}

	/**
	 * Initializes all elements within the Day Picker
	 */
	private void initialize() {
		setUpDatePicker();
		setUpDayDetailGridPane();
		setUpDayDetailScrollPane();
		setUpRowFactory();
		refreshData();
	}

	private void setUpDatePicker() {
		dayPickerPane = new BorderPane();
		
		dayPicker = new DatePicker(LocalDate.now());
		dayPicker.setFocusTraversable(false);
		dayPicker.setId("date-picker");
		DatePickerSkin datePickerSkin = new DatePickerSkin(dayPicker);
		datePickerSkin.getPopupContent().setOnMouseClicked(event -> con.refreshData());
		Node datePickerNode = datePickerSkin.getPopupContent();
		datePickerNode.setId("datePickerNode");

		BorderPane.setAlignment(datePickerNode, Pos.CENTER);
		dayPickerPane.setTop(datePickerNode);
	}

	private void setUpDayDetailGridPane() {
		dayDetailGridPane = new GridPane();
		dayDetailGridPane.maxWidth(COLUMN_WIDTH);
		dayDetailGridPane.setHgap(GRID_HORIZONTAL_SPACING);
		dayDetailGridPane.setId("dayDetailGridPane");
	}

	private void setUpRowFactory() {
		rowFactory = new TaskEventRowFactory(masterList, displayList, dayDetailGridPane);
	}

	private void setUpDayDetailScrollPane() {
		dayDetailScrollPane = new ScrollPane();
		dayDetailScrollPane.setContent(dayDetailGridPane);
		dayDetailScrollPane.getStyleClass().add("scrollpane");
		dayDetailScrollPane.setFocusTraversable(false);
		dayDetailScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		dayDetailScrollPane.setFitToWidth(true);
		dayDetailScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		dayDetailScrollPane.setOnScroll(event -> checkScrollPosition());
		dayDetailScrollPane.setId("dayDetailScrollPane");

		BorderPane.setAlignment(dayDetailScrollPane, Pos.CENTER);
		dayPickerPane.setCenter(dayDetailScrollPane);
	}

	/**
	 * Changes Day Picker to a day before if user scrolls to the top of the list
	 */
	private void checkScrollPosition() {
		if (dayDetailScrollPane.getVvalue() == 0.0) {
			dayPicker.setValue(dayPicker.getValue().minusDays(1));
			con.refreshData();
		}
	}

	/**
	 * Changes Day Picker to the Date of Event Added
	 * @param event Event Added
	 * @return Whether Event Clashes with another Event
	 */
	public boolean addEvent(Event event) {
		LocalDate temp = dayPicker.getValue();
		dayPicker.setValue(event.getStartDateTime().toLocalDate());
		refreshData();

		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(ADD_EVENT_TIMEOUT_DURATION);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				dayPicker.setValue(temp);
				refreshData();
			}
		});
		new Thread(sleeper).start();

		return rowFactory.checkIfEventClashes(event);
	}
}
