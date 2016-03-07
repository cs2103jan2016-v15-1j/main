package cs2103.v15_1j.jimjim.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.PropertySheet;

import cs2103.v15_1j.jimjim.JJUI;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class TaskViewController {

	private AnchorPane mainTaskPane;
	private TextField commandBar;
	private Label statusLbl;
	private Button executeBtn;
	private MasterDetailPane taskPane;

	private ObservableList<Task> taskData;
	private JJUI uiController;

	private final double BORDER_WIDTH = 14.0;
	private final double COMMAND_BAR_RIGHT_BORDER = 105.0;
	private final double DETAIL_VIEW_FIRST_ROW_POSITION = 10.0;
	private final double DETAIL_VIEW_SECOND_ROW_POSITION = 50.0;
	private final double DETAIL_VIEW_THIRD_ROW_POSITION = 90.0;
	private final double DETAIL_VIEW_FOURTH_ROW_POSITION = 130.0;
	private final double DETAIL_VIEW_FIRST_COLUMN_POSITION = 10.0;
	private final double DETAIL_VIEW_SECOND_COLUMN_POSITION = 90.0;
	private final double DETAIL_VIEW_TEXTFIELD_WIDTH = 120.0;
	private final double DIVIDER_POSITION = 0.6;
	private final double EXECUTE_BTN_WIDTH = 80.0;
	private final double EXECUTE_BTN_HEIGHT = 30.0;
	private final double STATUS_LBL_BOTTOM_BORDER = 47.0;
	private final double TASK_COMPLETED_COLUMN_WIDTH = 40.0;
	private final double WINDOW_WIDTH = 700.0;
	private final double WINDOW_HEIGHT = 600.0;

	public TaskViewController() {
		taskData = FXCollections.observableArrayList(new Callback<Task, Observable[]>() {

			@Override
			public Observable[] call(Task param) {
				return new Observable[] {param.completedProperty()};
			}
		});

		taskData.addListener(new ListChangeListener<Task>() {

			@Override
			public void onChanged(ListChangeListener.Change<? extends Task> t) {
				while (t.next()) {
					if (t.wasUpdated()) {
						displayMessage("Task Completed");
						taskData.remove(t.getFrom());
						//TO-DO: Method to Handle Completed Items
					}
				}
			}
		});
	}

	public AnchorPane initialize() {
		return setUpTaskView();
	}

	private AnchorPane setUpTaskView(){
		setUpTaskPane();
		setUpCommandBar();
		setUpExecuteBtn();
		setUpStatusLbl();
		setUpMainTaskPane();
		return mainTaskPane;
	}

	private void setUpCommandBar(){
		commandBar = new TextField();
		commandBar.setPromptText("Enter Command");
		commandBar.setOnAction(event -> handleCommand());
		AnchorPane.setLeftAnchor(commandBar, BORDER_WIDTH);
		AnchorPane.setBottomAnchor(commandBar, BORDER_WIDTH);
		AnchorPane.setRightAnchor(commandBar, COMMAND_BAR_RIGHT_BORDER);
	}

	private void setUpExecuteBtn(){
		executeBtn = new Button("Execute");
		executeBtn.setPrefWidth(EXECUTE_BTN_WIDTH);
		executeBtn.setPrefHeight(EXECUTE_BTN_HEIGHT);
		executeBtn.setOnAction(event -> handleCommand());
		AnchorPane.setBottomAnchor(executeBtn, BORDER_WIDTH);
		AnchorPane.setRightAnchor(executeBtn, BORDER_WIDTH);
	}

	private void setUpStatusLbl(){
		statusLbl = new Label("");
		AnchorPane.setLeftAnchor(statusLbl, BORDER_WIDTH);
		AnchorPane.setBottomAnchor(statusLbl, STATUS_LBL_BOTTOM_BORDER);
	}

	private void setUpMainTaskPane(){
		mainTaskPane = new AnchorPane();
		mainTaskPane.setPrefWidth(WINDOW_WIDTH);
		mainTaskPane.setPrefHeight(WINDOW_HEIGHT);

		AnchorPane.setTopAnchor(taskPane, 20.0);
		AnchorPane.setLeftAnchor(taskPane, 20.0);
		AnchorPane.setRightAnchor(taskPane, 20.0);
		mainTaskPane.getChildren().addAll(taskPane, commandBar, executeBtn, statusLbl);
	}

	private void setUpTaskPane(){
		taskPane = new MasterDetailPane();
		TableView<Task> taskTable = setUpTable();

		taskPane.setAnimated(true);
		taskPane.setMasterNode(taskTable);
		taskPane.setDetailNode(setUpDetailNode());
		taskPane.setDetailSide(Side.RIGHT);
		taskPane.setShowDetailNode(false);
		taskPane.setDividerPosition(DIVIDER_POSITION);
	}

	private PropertySheet setUpDetailNode(){
		return new PropertySheet();
	}

	private AnchorPane setUpDetailNode(Task task){
		AnchorPane detailPane = new AnchorPane();
		Button closeBtn = setUpCloseBtn();
		Label nameLbl = setUpNameLbl();
		TextField nameTF = setUpNameTF(task);
		Label dateLbl = setUpDateLbl();
		DatePicker taskDatePicker = setUpTaskDatePicker(task);
		Button deleteBtn = setUpDeleteBtn(task);

		detailPane.getChildren().addAll(closeBtn,nameLbl, nameTF, dateLbl, taskDatePicker, deleteBtn);

		return detailPane;
	}

	private Button setUpCloseBtn(){
		Button closeBtn = new Button("X");
		closeBtn.setOnAction(event -> {
			taskPane.setShowDetailNode(false);
		});
		AnchorPane.setTopAnchor(closeBtn, DETAIL_VIEW_FIRST_ROW_POSITION);
		AnchorPane.setLeftAnchor(closeBtn, DETAIL_VIEW_FIRST_COLUMN_POSITION);
		return closeBtn;
	}

	private Label setUpNameLbl(){
		Label nameLbl = new Label("Task Name:");
		AnchorPane.setTopAnchor(nameLbl, DETAIL_VIEW_SECOND_ROW_POSITION);
		AnchorPane.setLeftAnchor(nameLbl, DETAIL_VIEW_FIRST_COLUMN_POSITION);

		return nameLbl;
	}

	private TextField setUpNameTF(Task task){
		TextField nameTF = new TextField();
		nameTF.setPrefWidth(DETAIL_VIEW_TEXTFIELD_WIDTH);
		nameTF.textProperty().bindBidirectional(task.taskNameProperty());
		AnchorPane.setTopAnchor(nameTF, DETAIL_VIEW_SECOND_ROW_POSITION);
		AnchorPane.setLeftAnchor(nameTF, DETAIL_VIEW_SECOND_COLUMN_POSITION);
		return nameTF;
	}

	private Label setUpDateLbl(){
		Label dateLbl = new Label("Date:");
		AnchorPane.setTopAnchor(dateLbl, DETAIL_VIEW_THIRD_ROW_POSITION);
		AnchorPane.setLeftAnchor(dateLbl, DETAIL_VIEW_FIRST_COLUMN_POSITION);

		return dateLbl;
	}

	private DatePicker setUpTaskDatePicker(Task task){
		DatePicker taskDatePicker = new DatePicker();
		taskDatePicker.setPrefWidth(DETAIL_VIEW_TEXTFIELD_WIDTH);
		taskDatePicker.valueProperty().bindBidirectional(task.dateProperty());
		taskDatePicker.setOnAction(event -> {
			LocalDate date = taskDatePicker.getValue();
			task.setDate(date);
			uiController.refreshUI();
		});
		AnchorPane.setTopAnchor(taskDatePicker, DETAIL_VIEW_THIRD_ROW_POSITION);
		AnchorPane.setLeftAnchor(taskDatePicker, DETAIL_VIEW_SECOND_COLUMN_POSITION);

		return taskDatePicker;
	}

	private Button setUpDeleteBtn(Task task){
		Button deleteBtn = new Button("Delete");
		deleteBtn.setOnAction(event -> {
			taskPane.setShowDetailNode(false);
			taskData.remove(task);
			//TO-DO: Remove from Storage
		});
		AnchorPane.setTopAnchor(deleteBtn, DETAIL_VIEW_FOURTH_ROW_POSITION);
		AnchorPane.setLeftAnchor(deleteBtn, DETAIL_VIEW_FIRST_COLUMN_POSITION);
		AnchorPane.setRightAnchor(deleteBtn, DETAIL_VIEW_FIRST_COLUMN_POSITION);
		return deleteBtn;
	}


	private TableView<Task> setUpTable(){
		TableColumn<Task, Boolean> taskCompletedColumn = setUpTaskCompletedColumn();
		TableColumn<Task, String> taskNameColumn = setUpTaskNameColumn();
		TableColumn<Task, LocalDateTime> taskDateTimeColumn = setUpTaskDateTimeColumn();
		TableView<Task> taskTable = setUpTaskTable(taskCompletedColumn, taskNameColumn, taskDateTimeColumn);

		return taskTable;
	}

	private TableColumn<Task, String> setUpTaskNameColumn(){
		TableColumn<Task, String> taskNameColumn = new TableColumn<Task, String>();
		taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());

		return taskNameColumn;
	}

	private TableColumn<Task, Boolean> setUpTaskCompletedColumn(){
		TableColumn<Task, Boolean> taskCompletedColumn = new TableColumn<Task, Boolean>();
		taskCompletedColumn.setEditable(true);
		taskCompletedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(taskCompletedColumn));
		taskCompletedColumn.setCellValueFactory(cellData -> cellData.getValue().completedProperty());
		taskCompletedColumn.setPrefWidth(TASK_COMPLETED_COLUMN_WIDTH);
		taskCompletedColumn.setResizable(false);

		return taskCompletedColumn;
	}

	private TableColumn<Task, LocalDateTime> setUpTaskDateTimeColumn(){
		TableColumn<Task, LocalDateTime> taskDateColumn = new TableColumn<Task, LocalDateTime>();


		DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM hh:mm");
		taskDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
		taskDateColumn.setCellFactory(column -> {
			return new TableCell<Task, LocalDateTime>() {
				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(myDateFormatter.format(item));
					}
				}
			};
		});

		return taskDateColumn;
	}

	@SuppressWarnings("unchecked")
	private TableView<Task> setUpTaskTable(TableColumn<Task, Boolean> taskCompletedColumn, TableColumn<Task, String>
	taskNameColumn, TableColumn<Task, LocalDateTime> taskDateTimeColumn){
		TableView<Task> taskTable = new TableView<Task>();
		taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		taskTable.getColumns().addAll(taskCompletedColumn, taskNameColumn, taskDateTimeColumn);
		taskTable.setEditable(true);
		taskTable.setItems(taskData);

		taskTable.setRowFactory( tv -> {
			TableRow<Task> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
					Task rowData = row.getItem();
					taskPane.setDetailNode(setUpDetailNode(rowData));
					taskPane.setShowDetailNode(true);
				}
			});
			return row ;
		});

		return taskTable;
	}

	public void refreshUI(List<TaskEvent> tempList){
		taskData.clear();
		for(TaskEvent te: tempList){
			taskData.add((Task) te);
		}
	}

	public void focusCommandBar(){
		commandBar.requestFocus();
	}

	private void handleCommand() {
		if (commandBar.getText() != null) {
			String status = uiController.executeCommand(commandBar.getText());
			displayMessage(status);
			commandBar.setText("");
		}
	}

	private void displayMessage(String msg){
		statusLbl.setText(msg);
	}

	public ObservableList<Task> getTaskData() {
		return taskData;
	}

	public void setUIController(JJUI uiController){
		this.uiController = uiController;
	}

}
