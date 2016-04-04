package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.jfoenix.controls.JFXCheckBox;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class TaskEventRowFactory {

	private DataLists masterList;
	private DataLists displayList;
	private GridPane pane;

	private Integer rowNo;
	private Integer selectedDateRowNo;

	private DateTimeFormatter dateFmt;
	private DateTimeFormatter dateTimeFmt;

	private final double ID_LABEL_WIDTH = 50.0;
	private final double NAME_LABEL_WIDTH = 350.0;
	private final double DATE_LABEL_WIDTH = 200.0;
	private final int NO_OF_COLUMNS = 3;

	public TaskEventRowFactory(DataLists dataList, DataLists displayList, GridPane pane){
		this.masterList = dataList;
		this.displayList = displayList;
		this.pane = pane;
		this.rowNo = new Integer(0);

		dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
		dateTimeFmt = DateTimeFormatter.ofPattern("dd MMM h:mm a");
	}

	public void clear(){
		rowNo = new Integer(-1);
		pane.getChildren().clear();
	}

	public int showAllTaskEvents(){
		LocalDate currentDate = null;
		int eventCounter = 0;
		int deadlineTaskCounter = 0;
		int addedCounter = 0;

		List<Event> eventsList = masterList.getEventsList();
		List<DeadlineTask> deadlineTasksList = masterList.getDeadlineTasksList();

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

				if(!nextEventDate.isAfter(nextDeadlineTaskDate)){
					itemToAdd = nextEvent;
					eventCounter++;
				} else {
					itemToAdd = nextDeadlineTask;
					deadlineTaskCounter++;
				}
			}

			LocalDate tempDate = getLatestDate(currentDate, itemToAdd);

			if(!currentDate.equals(tempDate)){
				currentDate = tempDate;
				addLabel(currentDate);
			}

			addedCounter++;
			addTaskEvent(itemToAdd);
		}

		for(FloatingTask task: masterList.getFloatingTasksList()){
			addedCounter++;
			addTaskEvent(task);
		}
		
		return addedCounter;
	}

	public int showTaskEventsFromDate(LocalDate selectedDate){
		LocalDate currentDate = null;
		int eventCounter = 0;
		int deadlineTaskCounter = 0;
		int addedCounter = 0;

		List<Event> eventsList = masterList.getEventsList();
		List<DeadlineTask> deadlineTasksList = masterList.getDeadlineTasksList();

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

				if(!nextEventDate.isAfter(nextDeadlineTaskDate)){
					itemToAdd = nextEvent;
					eventCounter++;
				} else {
					itemToAdd = nextDeadlineTask;
					deadlineTaskCounter++;
				}
			}

			if(checkNotBefore(selectedDate, itemToAdd)){
				LocalDate tempDate = getLatestDate(currentDate, itemToAdd);

				if(currentDate == null){
					currentDate = tempDate;
					
					if(!selectedDate.equals(currentDate)){
						addLabel(selectedDate);
						addLabel("No events or deadline tasks on this day", "red-label");
					}
					
					addLabel(currentDate);
				} else if (!currentDate.equals(tempDate)){
					currentDate = tempDate;
					addLabel(currentDate);
				}

				addedCounter++;
				addTaskEvent(itemToAdd);
			}
		}

		return addedCounter;
	}

	public int showTaskEventsOnDate(LocalDate date){
		int noOfEvents = showEventOnDate(date);
		int noOfTasks = showDeadlineTaskOnDate(date);

		rowNo += (noOfEvents+noOfTasks);

		if ((noOfEvents+noOfTasks) != 0){
			addEmptyLabel();
		}

		return noOfEvents + noOfTasks;
	}

	public int showOverdue(){
		int noOfOverdue = 0;

		for(Event e: masterList.getEventsList()){
			if(checkOverdue(e)){
				addTaskEvent(e);
				noOfOverdue++;
			}
		}

		for(DeadlineTask t: masterList.getDeadlineTasksList()){
			if(checkOverdue(t)){
				addTaskEvent(t);
				noOfOverdue++;
			}
		}

		rowNo += noOfOverdue;

		return noOfOverdue;
	}

	public boolean showAllFloatingTasks(boolean showCompleted){
		boolean hasCompleted = false;

		for(FloatingTask t: masterList.getFloatingTasksList()){
			if(!t.getCompleted()){
				rowNo++;
				addTaskEvent(t);
			}
			else {
				hasCompleted = true;
			}
		}

		if(showCompleted){
			for(FloatingTask t: masterList.getFloatingTasksList()){
				if(t.getCompleted()){
					rowNo++;
					addTaskEvent(t);
				}
			}
		}

		return hasCompleted;
	}

	private int showEventOnDate(LocalDate date){
		int noOfEvents = 0;

		for(Event event: masterList.getEventsList()){
			if(checkOnDate(event, date)){
				noOfEvents++;
				addTaskEvent(event);
			}
		}

		return noOfEvents;
	}

	private int showDeadlineTaskOnDate(LocalDate date){
		int noOfTasks = 0;

		for(DeadlineTask task: masterList.getDeadlineTasksList()){
			if(checkOnDate(task, date)){
				noOfTasks++;
				addTaskEvent(task);
			}
		}

		return noOfTasks;
	}

	private void addTaskEvent(TaskEvent taskEvent){
		if(taskEvent instanceof Event){
			addTaskEvent((Event) taskEvent);
		} else if (taskEvent instanceof DeadlineTask){
			addTaskEvent((DeadlineTask) taskEvent);
		} else if (taskEvent instanceof FloatingTask){
			addTaskEvent((FloatingTask) taskEvent);
		}
	}

	private void addTaskEvent(Event event){	
		int id = displayList.size('e') + 1;
		if(displayList.contains(event)){
			id = displayList.indexOf(event) + 1;
		}
		else {
			displayList.addWithoutSorting(event);
		}
		
		JFXCheckBox cb = new JFXCheckBox();
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(event.completedProperty());
		cb.setDisable(true);
		GridPane.setHalignment(cb, HPos.CENTER);
		pane.add(cb, 0, ++rowNo, 1, 1);

		Label idLabel = new Label("[E"+id+"]");
		idLabel.setWrapText(true);
		idLabel.setPrefWidth(ID_LABEL_WIDTH);
		pane.add(idLabel, 1, rowNo, 1, 1);

		Label eventLabel = new Label();
		eventLabel.textProperty().bindBidirectional(event.taskNameProperty());
		eventLabel.setTextAlignment(TextAlignment.LEFT);
		eventLabel.setWrapText(true);
		eventLabel.setPrefWidth(NAME_LABEL_WIDTH);
		pane.add(eventLabel, 2, rowNo, 1, 1);

		Label dateLabel = new Label(event.toDateTimeString());
		dateLabel.setWrapText(true);
		dateLabel.setPrefWidth(DATE_LABEL_WIDTH);
		dateLabel.setTextAlignment(TextAlignment.RIGHT);
		pane.add(dateLabel, 2, ++rowNo, 1, 1);

		if(checkOverdue(event)){
			idLabel.getStyleClass().add("overdue-label");
			eventLabel.getStyleClass().add("overdue-label");
			dateLabel.getStyleClass().add("overdue-label");
		}
		else if(!event.getCompleted()){
			idLabel.getStyleClass().add("id-label");
			eventLabel.getStyleClass().add("event-label");
			dateLabel.getStyleClass().add("event-label");
		}
		else {
			idLabel.getStyleClass().add("completed-task-label");
			eventLabel.getStyleClass().add("completed-task-label");
			dateLabel.getStyleClass().add("completed-task-label");
		}
	}

	private void addTaskEvent(DeadlineTask task){
		int id = displayList.size('d') + 1;
		if(displayList.contains(task)){
			id = displayList.indexOf(task) + 1;
		}
		else {
			displayList.addWithoutSorting(task);
		}

		JFXCheckBox cb = new JFXCheckBox();
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(task.completedProperty());
		cb.setDisable(true);
		GridPane.setHalignment(cb, HPos.CENTER);
		pane.add(cb, 0, ++rowNo, 1, 1);

		
		Label idLabel = new Label("[D"+id+"]");
		idLabel.setWrapText(true);
		idLabel.setPrefWidth(ID_LABEL_WIDTH);
		pane.add(idLabel, 1, rowNo, 1, 1);

		Label taskLabel = new Label();
		taskLabel.textProperty().bindBidirectional(task.taskNameProperty());
		taskLabel.setWrapText(true);
		taskLabel.setPrefWidth(NAME_LABEL_WIDTH);
		pane.add(taskLabel, 2, rowNo, 1, 1);

		Label dateTimeLabel = new Label(task.getDateTime().format(dateTimeFmt));
		dateTimeLabel.setTextAlignment(TextAlignment.RIGHT);
		dateTimeLabel.setWrapText(true);
		dateTimeLabel.setPrefWidth(DATE_LABEL_WIDTH);
		pane.add(dateTimeLabel, 2, ++rowNo, 1, 1);

		if(checkOverdue(task)){
			idLabel.getStyleClass().add("overdue-label");
			taskLabel.getStyleClass().add("overdue-label");
			dateTimeLabel.getStyleClass().add("overdue-label");
		}
		else if(!task.getCompleted()){
			idLabel.getStyleClass().add("id-label");
			taskLabel.getStyleClass().add("task-label");
			dateTimeLabel.getStyleClass().add("task-label");
		}
		else {
			idLabel.getStyleClass().add("completed-task-label");
			taskLabel.getStyleClass().add("completed-task-label");
			dateTimeLabel.getStyleClass().add("completed-task-label");
		}
	}

	private void addTaskEvent(FloatingTask t){
		int id = displayList.size('f') + 1;
		if(displayList.contains(t)){
			id = displayList.indexOf(t) + 1;
		}
		else {
			displayList.addWithoutSorting(t);
		}

		JFXCheckBox cb = new JFXCheckBox();
		cb.getStyleClass().add("custom-jfx-check-box");
		cb.selectedProperty().bindBidirectional(t.completedProperty());
		cb.setDisable(true);
		pane.add(cb, 0, ++rowNo, 1, 1);

		Label idLabel = new Label("[F"+id+"]");
		pane.add(idLabel, 1, rowNo, 1, 1);

		Label taskLabel = new Label();
		taskLabel.textProperty().bindBidirectional(t.taskNameProperty());
		taskLabel.setWrapText(true);
		taskLabel.setPrefWidth(NAME_LABEL_WIDTH);
		pane.add(taskLabel, 2, rowNo, 1, 1);

		if(!t.getCompleted()){
			idLabel.getStyleClass().add("id-label");
			taskLabel.getStyleClass().add("task-label");
		}
		else {
			idLabel.getStyleClass().add("completed-task-label");
			taskLabel.getStyleClass().add("completed-task-label");
		}
	}

	public void addLabel(String text, String styleClass){
		Label label = new Label(text);
		label.getStyleClass().add(styleClass);
		pane.add(label, 0, ++rowNo, NO_OF_COLUMNS, 1);
	}

	public void addLabel(LocalDate date){
		Label dateLabel = new Label(date.format(dateFmt));
		dateLabel.getStyleClass().add("date-label");
		pane.add(dateLabel, 0, ++rowNo, NO_OF_COLUMNS, 1);
	}

	private void addEmptyLabel(){
		Label emptyLabel = new Label("");
		pane.add(emptyLabel, 0, ++rowNo, NO_OF_COLUMNS, 1);
	}

	private boolean checkOnDate(DeadlineTask t, LocalDate date){
		boolean onDate = false;
		LocalDate taskDate = t.getDateTime().toLocalDate();

		if(taskDate.equals(date)){
			onDate = true;
		} 

		return onDate;
	}

	private boolean checkOnDate(Event e, LocalDate date){
		boolean onDate = false;

		LocalDate eventStartDate = e.getStartDateTime().toLocalDate();
		LocalDate eventEndDate = e.getEndDateTime().toLocalDate();

		if(eventStartDate.equals(date)){
			onDate = true;
		} else if(eventEndDate.equals(date)){
			onDate = true;
		} else if (eventStartDate.isBefore(date) && eventEndDate.isAfter(date)){
			onDate = true;
		}

		return onDate;
	}

	private boolean checkOverdue(Event e){
		LocalDateTime nowDateTime = LocalDateTime.now();
		boolean overdue = false;

		if(!e.getCompleted() && e.getEndDateTime().isBefore(nowDateTime)){
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

	private boolean checkNotBefore(LocalDate date, TaskEvent taskEvent){
		if(taskEvent instanceof Event){
			LocalDate eventDate = ((Event) taskEvent).getStartDateTime().toLocalDate();
			return !eventDate.isBefore(date);
		}
		else if(taskEvent instanceof DeadlineTask) {
			LocalDate deadlineTaskDate = ((DeadlineTask) taskEvent).getDateTime().toLocalDate();
			return !deadlineTaskDate.isBefore(date);
		}
		else {
			return false;
		}
	}

	private LocalDate getLatestDate(LocalDate currentDate, TaskEvent taskEvent){
		if(currentDate == null){
			if(taskEvent instanceof Event){
				currentDate = ((Event) taskEvent).getStartDateTime().toLocalDate();
			}
			else {
				currentDate = ((DeadlineTask) taskEvent).getDateTime().toLocalDate();
			}
		}
		else{
			if(taskEvent instanceof Event){
				LocalDate eventDate = ((Event) taskEvent).getStartDateTime().toLocalDate();
				if(!currentDate.equals(eventDate)){
					currentDate = eventDate;
				}
			}
			else {
				LocalDate taskDate = ((DeadlineTask) taskEvent).getDateTime().toLocalDate();
				if(!currentDate.equals(taskDate)){
					currentDate = taskDate;
				}
			}
		}

		return currentDate;
	}
	
	public int getSelectedDateRowNo(){
		return selectedDateRowNo;
	}
}
