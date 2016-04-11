package cs2103.v15_1j.jolt.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.jfoenix.controls.JFXCheckBox;

import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;
import cs2103.v15_1j.jolt.model.TaskEvent;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class TaskEventRowFactory {

	private DataLists dataList;
	private DataLists displayList;
	private GridPane pane;

	private Integer rowNo;

	private DateTimeFormatter dateFmt;
	private DateTimeFormatter dateTimeFmt;

	private final double ID_LABEL_WIDTH = 50.0;
	private final double NAME_LABEL_WIDTH = 350.0;
	private final double DATE_LABEL_WIDTH = 200.0;
	private final int NO_OF_COLUMNS = 3;

	// @@author A0139963N
	/**
	 * Constructor
	 * @param dataList Main Data List for Tasks and Events
	 * @param displayList Display List for Tasks and Events
	 * @param pane Pane to Display
	 */
	public TaskEventRowFactory(DataLists dataList, DataLists displayList, GridPane pane) {
		this.dataList = dataList;
		this.displayList = displayList;
		this.pane = pane;
		this.rowNo = new Integer(0);

		dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
		dateTimeFmt = DateTimeFormatter.ofPattern("dd MMM h:mm a");
	}

	/**
	 * Clear the Pane
	 */
	public void clear() {
		rowNo = new Integer(-1);
		pane.getChildren().clear();
	}

	/**
	 * Shows All Deadline Tasks and Events
	 * @param selectedDate Date Selected by User
	 * @return No of Events and Tasks Added
	 */
	public int showAllDeadlineTaskAndEvents(LocalDate selectedDate) {
		LocalDate currentDate = LocalDate.MIN;
		int eventCounter = 0;
		int deadlineTaskCounter = 0;
		int addedCounter = 0;
		boolean displayDate = true;

		if (selectedDate == null) {
			displayDate = false;
			selectedDate = LocalDate.MIN;
		}

		List<Event> eventsList = dataList.getEventsList();
		List<DeadlineTask> deadlineTasksList = dataList.getDeadlineTasksList();

		while ((eventCounter < eventsList.size()) || (deadlineTaskCounter < deadlineTasksList.size())) {
			TaskEvent itemToAdd;

			if (eventCounter >= eventsList.size()) {
				itemToAdd = deadlineTasksList.get(deadlineTaskCounter);
				deadlineTaskCounter++;
			} else if (deadlineTaskCounter >= deadlineTasksList.size()) {
				itemToAdd = eventsList.get(eventCounter);
				eventCounter++;
			} else {
				Event nextEvent = eventsList.get(eventCounter);
				LocalDate nextEventDate = nextEvent.getStartDateTime().toLocalDate();
				DeadlineTask nextDeadlineTask = deadlineTasksList.get(deadlineTaskCounter);
				LocalDate nextDeadlineTaskDate = nextDeadlineTask.getDateTime().toLocalDate();

				if (!nextEventDate.isAfter(nextDeadlineTaskDate)) {
					itemToAdd = nextEvent;
					eventCounter++;
				} else {
					itemToAdd = nextDeadlineTask;
					deadlineTaskCounter++;
				}
			}

			LocalDate itemToAddDate = LocalDate.MIN;
			if (itemToAdd instanceof Event) {
				itemToAddDate = ((Event) itemToAdd).getStartDateTime().toLocalDate();
			} else if (itemToAdd instanceof DeadlineTask) {
				itemToAddDate = ((DeadlineTask) itemToAdd).getDateTime().toLocalDate();
			}

			if (!selectedDate.isAfter(itemToAddDate)) {
				if (!currentDate.equals(itemToAddDate)) {
					if (displayDate && !itemToAddDate.equals(selectedDate)) {
						addLabel(selectedDate);
						addLabel("No events or deadline tasks on this day", "red-label");
					}

					displayDate = false;
					currentDate = itemToAddDate;
					addLabel(currentDate);
				}

				addedCounter++;
				if (itemToAdd instanceof Event) {
					Event currentEvent = (Event) itemToAdd;
					addTaskEvent(currentEvent, checkIfEventClashes(currentEvent));
				} else {
					addTaskEvent(itemToAdd);
				}
			}
		}

		return addedCounter;
	}

	/**
	 * Show All Tasks and Events on a Date
	 * @param date Selected DAte
	 * @return No of Tasks and Events
	 */
	public int showTaskEventsOnDate(LocalDate date) {
		int noOfEvents = showEventOnDate(date);
		int noOfTasks = showDeadlineTaskOnDate(date);

		rowNo += (noOfEvents + noOfTasks);

		if ((noOfEvents + noOfTasks) != 0) {
			addEmptyLabel();
		}

		return noOfEvents + noOfTasks;
	}

	/**
	 * Show Overdue Tasks and Events
	 * @return No of Overdue Tasks and Events
	 */
	public int showOverdue() {
		int noOfOverdue = 0;

		for (Event e : dataList.getEventsList()) {
			if (checkOverdue(e)) {
				addTaskEvent(e);
				noOfOverdue++;
			}
		}

		for (DeadlineTask t : dataList.getDeadlineTasksList()) {
			if (checkOverdue(t)) {
				addTaskEvent(t);
				noOfOverdue++;
			}
		}

		rowNo += noOfOverdue;

		return noOfOverdue;
	}

	/**
	 * Show all Floating Tasks
	 * @param shouldShowCompleted Whether Completed Tasks should be shown
	 * @return Whether there are any completed tasks
	 */
	public boolean showAllFloatingTasks(boolean shouldShowCompleted) {
		boolean hasCompleted = false;

		for (FloatingTask t : dataList.getFloatingTasksList()) {
			if (!t.getCompleted()) {
				rowNo++;
				addTaskEvent(t);
			} else {
				hasCompleted = true;
			}
		}

		if (shouldShowCompleted) {
			for (FloatingTask t : dataList.getFloatingTasksList()) {
				if (t.getCompleted()) {
					rowNo++;
					addTaskEvent(t);
				}
			}
		}

		return hasCompleted;
	}

	/**
	 * Show Events on a Selected Date
	 * @param date Selected Date
	 * @return Number of Events on the Date
	 */
	private int showEventOnDate(LocalDate date) {
		int noOfEvents = 0;
		Event previousEvent = null;

		for (Event event : dataList.getEventsList()) {
			if (checkOnDate(event, date)) {
				noOfEvents++;

				if (previousEvent == null) {
					previousEvent = event;
					addTaskEvent(event, false);
				} else {
					addTaskEvent(event, checkIfEventClashes(event));
				}

			}
		}

		return noOfEvents;
	}

	/**
	 * Show Deadline Tasks on a Selected Date
	 * @param date Selected Date
	 * @return Number of Deadline Tasks on the Date
	 */
	private int showDeadlineTaskOnDate(LocalDate date) {
		int noOfTasks = 0;

		for (DeadlineTask task : dataList.getDeadlineTasksList()) {
			if (checkOnDate(task, date)) {
				noOfTasks++;
				addTaskEvent(task);
			}
		}

		return noOfTasks;
	}

	/**
	 * Add a Task or Event to the Pane
	 * @param taskEvent Task of Event to be Added
	 */
	private void addTaskEvent(TaskEvent taskEvent) {
		if (taskEvent instanceof Event) {
			addTaskEvent(((Event) taskEvent), false);
		} else if (taskEvent instanceof DeadlineTask) {
			addTaskEvent((DeadlineTask) taskEvent);
		} else if (taskEvent instanceof FloatingTask) {
			addTaskEvent((FloatingTask) taskEvent);
		}
	}

	private void addTaskEvent(Event event, boolean clash) {
		int id = displayList.indexOf(event) + 1;

		if (id == 0) {
			id = displayList.size('e') + 1;
			displayList.add(event);
		}

		JFXCheckBox cb = new JFXCheckBox();
		cb.setId("cbE" + id);
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(event.completedProperty());
		cb.setDisable(true);
		GridPane.setHalignment(cb, HPos.CENTER);
		pane.add(cb, 0, ++rowNo, 1, 1);

		Label idLabel = new Label("[E" + id + "]");
		idLabel.setId("idLabelE" + id);
		idLabel.setWrapText(true);
		idLabel.setPrefWidth(ID_LABEL_WIDTH);
		pane.add(idLabel, 1, rowNo, 1, 1);

		Label eventLabel = new Label();
		eventLabel.setId("eventLabelE" + id);
		eventLabel.textProperty().bindBidirectional(event.taskNameProperty());
		eventLabel.setTextAlignment(TextAlignment.LEFT);
		eventLabel.setWrapText(true);
		eventLabel.setPrefWidth(NAME_LABEL_WIDTH);
		pane.add(eventLabel, 2, rowNo, 1, 1);

		Label dateTimeLabel = new Label(event.toDateTimeString());
		dateTimeLabel.setId("dateTimeLabelE" + id);
		
		if(event.getIsFullDay()){
			dateTimeLabel = new Label("Whole Day");
		} 
		dateTimeLabel.setWrapText(true);
		dateTimeLabel.setPrefWidth(DATE_LABEL_WIDTH);
		dateTimeLabel.setTextAlignment(TextAlignment.RIGHT);
		pane.add(dateTimeLabel, 2, ++rowNo, 1, 1);

		if (checkOverdue(event)) {
			idLabel.getStyleClass().add("overdue-label");
			eventLabel.getStyleClass().add("overdue-label");
			dateTimeLabel.getStyleClass().add("overdue-label");
		} else if (clash) {
			idLabel.getStyleClass().add("clash-event-label");
			eventLabel.getStyleClass().add("clash-event-label");
			dateTimeLabel.getStyleClass().add("clash-event-label");
		} else if (!event.getCompleted()) {
			idLabel.getStyleClass().add("id-label");
			eventLabel.getStyleClass().add("event-label");
			dateTimeLabel.getStyleClass().add("event-label");
		} else {
			idLabel.getStyleClass().add("completed-task-label");
			eventLabel.getStyleClass().add("completed-task-label");
			dateTimeLabel.getStyleClass().add("completed-task-label");
		}
	}

	private void addTaskEvent(DeadlineTask task) {
		int id = displayList.indexOf(task) + 1;

		if (id == 0) {
			id = displayList.size('d') + 1;
			displayList.add(task);
		}

		JFXCheckBox cb = new JFXCheckBox();
		cb.setId("cbD" + id);
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(task.completedProperty());
		cb.setDisable(true);
		GridPane.setHalignment(cb, HPos.CENTER);
		pane.add(cb, 0, ++rowNo, 1, 1);

		Label idLabel = new Label("[D" + id + "]");
		idLabel.setId("idLabelD" + id);
		idLabel.setWrapText(true);
		idLabel.setPrefWidth(ID_LABEL_WIDTH);
		pane.add(idLabel, 1, rowNo, 1, 1);

		Label taskLabel = new Label();
		taskLabel.textProperty().bindBidirectional(task.taskNameProperty());
		taskLabel.setId("taskLabelD" + id);
		taskLabel.setWrapText(true);
		taskLabel.setPrefWidth(NAME_LABEL_WIDTH);
		pane.add(taskLabel, 2, rowNo, 1, 1);

		Label dateTimeLabel = new Label(task.getDateTime().format(dateTimeFmt));
		taskLabel.setId("dateTimeLabelD" + id);
		dateTimeLabel.setTextAlignment(TextAlignment.RIGHT);
		dateTimeLabel.setWrapText(true);
		dateTimeLabel.setPrefWidth(DATE_LABEL_WIDTH);
		pane.add(dateTimeLabel, 2, ++rowNo, 1, 1);

		if (checkOverdue(task)) {
			idLabel.getStyleClass().add("overdue-label");
			taskLabel.getStyleClass().add("overdue-label");
			dateTimeLabel.getStyleClass().add("overdue-label");
		} else if (!task.getCompleted()) {
			idLabel.getStyleClass().add("id-label");
			taskLabel.getStyleClass().add("task-label");
			dateTimeLabel.getStyleClass().add("task-label");
		} else {
			idLabel.getStyleClass().add("completed-task-label");
			taskLabel.getStyleClass().add("completed-task-label");
			dateTimeLabel.getStyleClass().add("completed-task-label");
		}
	}

	private void addTaskEvent(FloatingTask t) {
		int id = displayList.indexOf(t) + 1;

		if (id == 0) {
			id = displayList.size('f') + 1;
			displayList.add(t);
		}

		JFXCheckBox cb = new JFXCheckBox();
		cb.setId("cbF" + id);
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(t.completedProperty());
		cb.setDisable(true);
		pane.add(cb, 0, ++rowNo, 1, 1);

		Label idLabel = new Label("[F" + id + "]");
		idLabel.setId("idLabelF" + id);
		pane.add(idLabel, 1, rowNo, 1, 1);

		Label taskLabel = new Label();
		taskLabel.setId("taskLabelF" + id);
		taskLabel.textProperty().bindBidirectional(t.taskNameProperty());
		taskLabel.setWrapText(true);
		taskLabel.setPrefWidth(NAME_LABEL_WIDTH);
		pane.add(taskLabel, 2, rowNo, 1, 1);

		if (!t.getCompleted()) {
			idLabel.getStyleClass().add("id-label");
			taskLabel.getStyleClass().add("task-label");
		} else {
			idLabel.getStyleClass().add("completed-task-label");
			taskLabel.getStyleClass().add("completed-task-label");
		}
	}

	/**
	 * Add a Label to the Pane
	 * @param text Label Text
	 * @param styleClass CSS Style Class of Label
	 */
	public void addLabel(String text, String styleClass) {
		Label label = new Label(text);
		label.getStyleClass().add(styleClass);
		pane.add(label, 0, ++rowNo, NO_OF_COLUMNS, 1);
	}

	/**
	 * Add a Date Label to the Pane
	 * @param date Selected Date
	 */
	public void addLabel(LocalDate date) {
		Label dateLabel = new Label(date.format(dateFmt));
		dateLabel.getStyleClass().add("date-label");
		pane.add(dateLabel, 0, ++rowNo, NO_OF_COLUMNS, 1);
	}

	private void addEmptyLabel() {
		Label emptyLabel = new Label("");
		pane.add(emptyLabel, 0, ++rowNo, NO_OF_COLUMNS, 1);
	}

	private boolean checkOnDate(DeadlineTask t, LocalDate date) {
		boolean onDate = false;
		LocalDate taskDate = t.getDateTime().toLocalDate();

		if (taskDate.equals(date)) {
			onDate = true;
		}

		return onDate;
	}

	private boolean checkOnDate(Event e, LocalDate date) {
		boolean onDate = false;

		LocalDate eventStartDate = e.getStartDateTime().toLocalDate();
		LocalDate eventEndDate = e.getEndDateTime().toLocalDate();

		if (eventStartDate.equals(date)) {
			onDate = true;
		} else if (eventEndDate.equals(date)) {
			onDate = true;
		} else if (eventStartDate.isBefore(date) && eventEndDate.isAfter(date)) {
			onDate = true;
		}

		return onDate;
	}

	private boolean checkOverdue(Event e) {
		LocalDateTime nowDateTime = LocalDateTime.now();
		boolean overdue = false;

		if (!e.getCompleted() && e.getEndDateTime().isBefore(nowDateTime)) {
			overdue = true;
		}

		return overdue;
	}

	private boolean checkOverdue(DeadlineTask t) {
		LocalDateTime nowDateTime = LocalDateTime.now();
		boolean overdue = false;

		if (!t.getCompleted() && t.getDateTime().isBefore(nowDateTime)) {
			overdue = true;
		}

		return overdue;
	}

	public boolean checkIfEventClashes(Event e) {
		boolean clashes = false;
		
		if(e.getIsFullDay()){
			return false;
		}
		
		LocalDateTime eventStartTime = e.getStartDateTime();
		LocalDateTime eventEndTime = e.getEndDateTime();

		for (Event otherEvent : displayList.getEventsList()) {
			if(!otherEvent.getIsFullDay()){
				LocalDateTime otherEventStartTime = otherEvent.getStartDateTime();
				LocalDateTime otherEventEndTime = otherEvent.getEndDateTime();
				if ((!eventStartTime.isBefore(otherEventStartTime) && eventStartTime.isBefore(otherEventEndTime))
						|| (eventEndTime.isAfter(otherEventStartTime) && !eventEndTime.isAfter(otherEventEndTime))) {
					clashes = true;
				}
			}
		}

		return clashes;
	}
}
