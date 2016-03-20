package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.PropertySheet;

import cs2103.v15_1j.jimjim.model.DeadlineTask;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class TaskViewController {

	private ObservableList<DeadlineTask> taskData;
	private MasterDetailPane taskPane;
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
	private final double TASK_COMPLETED_COLUMN_WIDTH = 40.0;
	private final double TASK_PANE_TOP_BORDER = 30.0;
	private final double TASK_PANE_BOTTOM_BORDER = 350.0;

	public TaskViewController(MainViewController mainViewController){
		this.mainViewController = mainViewController;

		taskData = FXCollections.observableArrayList();
		/*taskData = FXCollections.observableArrayList(new Callback<Task, Observable[]>() {

			@Override
			public Observable[] call(Task param) {
				return new Observable[] {param.completedProperty()};
			}
		});*/

		/*taskData.addListener(new ListChangeListener<Task>() {

			@Override
			public void onChanged(ListChangeListener.Change<? extends Task> t) {
				while (t.next()) {
					if (t.wasUpdated()) {
						mainViewController.displayMessage("Task Completed");
						taskData.remove(t.getFrom());
						//TO-DO: Method to Handle Completed Items
					}
				}
			}
		});*/
	}

	public MasterDetailPane setUpTaskPane(){
		taskPane = new MasterDetailPane();
		TableView<DeadlineTask> taskTable = setUpTable();

		taskPane.setAnimated(true);
		taskPane.setMasterNode(taskTable);
		taskPane.setDetailNode(setUpDetailNode());
		taskPane.setDetailSide(Side.RIGHT);
		taskPane.setShowDetailNode(false);
		taskPane.setDividerPosition(DIVIDER_POSITION);

		AnchorPane.setTopAnchor(taskPane, TASK_PANE_TOP_BORDER);
		AnchorPane.setLeftAnchor(taskPane, BORDER_WIDTH);
		AnchorPane.setRightAnchor(taskPane, BORDER_WIDTH);
		AnchorPane.setBottomAnchor(taskPane, TASK_PANE_BOTTOM_BORDER);

		return taskPane;
	}

	private PropertySheet setUpDetailNode(){
		return new PropertySheet();
	}

	private AnchorPane setUpDetailNode(DeadlineTask task){
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

	private TextField setUpNameTF(DeadlineTask task){
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

	private DatePicker setUpTaskDatePicker(DeadlineTask task){
		DatePicker taskDatePicker = new DatePicker();
		taskDatePicker.setPrefWidth(DETAIL_VIEW_TEXTFIELD_WIDTH);
		if(task.dateProperty() != null){
			taskDatePicker.valueProperty().bindBidirectional(task.dateProperty());
		}
		taskDatePicker.setOnAction(event -> {
			LocalDate date = taskDatePicker.getValue();
			task.setDate(date);
			mainViewController.refreshUI();
		});
		AnchorPane.setTopAnchor(taskDatePicker, DETAIL_VIEW_THIRD_ROW_POSITION);
		AnchorPane.setLeftAnchor(taskDatePicker, DETAIL_VIEW_SECOND_COLUMN_POSITION);

		return taskDatePicker;
	}

	private Button setUpDeleteBtn(DeadlineTask task){
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


	private TableView<DeadlineTask> setUpTable(){
		TableColumn numberCol = setUpTaskNoColumn();
		TableColumn<DeadlineTask, Boolean> taskCompletedColumn = setUpTaskCompletedColumn();
		TableColumn<DeadlineTask, String> taskNameColumn = setUpTaskNameColumn();
		TableColumn<DeadlineTask, LocalDateTime> taskDateTimeColumn = setUpTaskDateTimeColumn();
		TableView<DeadlineTask> taskTable = setUpTaskTable(numberCol, taskCompletedColumn, taskNameColumn, taskDateTimeColumn);

		return taskTable;
	}

	private TableColumn setUpTaskNoColumn(){
		TableColumn numberCol = new TableColumn( "#" );
		numberCol.setPrefWidth(TASK_COMPLETED_COLUMN_WIDTH);
		numberCol.setResizable(false);
		numberCol.setCellFactory( new Callback<TableColumn, TableCell>()
		{
			@Override
			public TableCell call( TableColumn p )
			{
				return new TableCell()
				{
					@Override
					public void updateItem( Object item, boolean empty )
					{
						super.updateItem( item, empty );
						setGraphic( null );
						setText( empty ? null : getIndex() + 1 + "" );
					}
				};
			}
		});

		return numberCol;
	}

	private TableColumn<DeadlineTask, String> setUpTaskNameColumn(){
		TableColumn<DeadlineTask, String> taskNameColumn = new TableColumn<DeadlineTask, String>();
		taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());

		return taskNameColumn;
	}

	private TableColumn<DeadlineTask, Boolean> setUpTaskCompletedColumn(){
		TableColumn<DeadlineTask, Boolean> taskCompletedColumn = new TableColumn<DeadlineTask, Boolean>();
		taskCompletedColumn.setEditable(true);
		taskCompletedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(taskCompletedColumn));
		taskCompletedColumn.setCellValueFactory(cellData -> cellData.getValue().completedProperty());
		taskCompletedColumn.setPrefWidth(TASK_COMPLETED_COLUMN_WIDTH);
		taskCompletedColumn.setResizable(false);

		return taskCompletedColumn;
	}

	private TableColumn<DeadlineTask, LocalDateTime> setUpTaskDateTimeColumn(){
		TableColumn<DeadlineTask, LocalDateTime> taskDateColumn = new TableColumn<DeadlineTask, LocalDateTime>();


		DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd.MM hh:mm");
		taskDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
		taskDateColumn.setCellFactory(column -> {
			return new TableCell<DeadlineTask, LocalDateTime>() {
				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(dateFmt.format(item));
					}
				}
			};
		});

		return taskDateColumn;
	}

	@SuppressWarnings("unchecked")
	private TableView<DeadlineTask> setUpTaskTable(TableColumn numberColumn, TableColumn<DeadlineTask, Boolean> taskCompletedColumn, TableColumn<DeadlineTask, String>
	taskNameColumn, TableColumn<DeadlineTask, LocalDateTime> taskDateTimeColumn){
		TableView<DeadlineTask> taskTable = new TableView<DeadlineTask>();
		taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		taskTable.getColumns().addAll(numberColumn, taskCompletedColumn, taskNameColumn, taskDateTimeColumn);
		taskTable.setEditable(true);
		taskTable.setItems(taskData);

		taskTable.setRowFactory( tv -> {
			TableRow<DeadlineTask> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
					DeadlineTask rowData = row.getItem();
					taskPane.setDetailNode(setUpDetailNode(rowData));
					taskPane.setShowDetailNode(true);
				}
			});
			return row ;
		});

		return taskTable;
	}

	public void refreshUI(List<DeadlineTask> tempList){
		taskData.clear();
		for(DeadlineTask task: tempList){
			taskData.add(task);
		}
	}

	public ObservableList<DeadlineTask> getTaskData() {
		return taskData;
	}

}
