package cs2103.v15_1j.jimjim.view;

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
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class MainViewController {

	@FXML
    private AnchorPane mainPane;
	@FXML
    private TextField commandBar;
	
	private ObservableList<Task> taskData;
	private JJUI uiController;
	
	public MainViewController() {
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
                    	taskData.remove(t.getFrom());
                    }
                  }
            }
        });
	}
	
	@FXML
    private void initialize() {
		 MasterDetailPane pane = new MasterDetailPane();
		 TableView<Task> taskTable = setUpTable();
		 
		 pane.setMasterNode(taskTable);
		 pane.setDetailNode(new PropertySheet());
		 pane.setDetailSide(Side.RIGHT);
		 pane.setShowDetailNode(false);
		 
		 AnchorPane.setTopAnchor(pane, 20.0);
		 AnchorPane.setLeftAnchor(pane, 20.0);
		 AnchorPane.setRightAnchor(pane, 20.0);
		 mainPane.getChildren().add(pane);
	}
	
	private TableView<Task> setUpTable(){
		TableView<Task> table = new TableView<Task>();
		TableColumn<Task, Boolean> taskCompletedColumn = new TableColumn<Task, Boolean>();
	    TableColumn<Task, String> taskNameColumn = new TableColumn<Task, String>();
	    TableColumn<Task, String> taskDateColumn = new TableColumn<Task, String>();
	    
        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        taskCompletedColumn.setEditable(true);
        taskCompletedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(taskCompletedColumn));
        taskCompletedColumn.setCellValueFactory(cellData -> cellData.getValue().completedProperty());
        taskDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(taskCompletedColumn, taskNameColumn, taskDateColumn);
        table.setEditable(true);
        table.setItems(taskData);
        
        return table;
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
	
	/**
     * Called when the user adds a command in the commandBar
     * TO-DO: Rename to handleCommands
     */
    @FXML
    private void handleCommand() {
    	if (commandBar.getText() != null) {
    		commandBar.setPromptText(uiController.executeCommand(commandBar.getText()));
            commandBar.setText("");

            
        } else {
            // Nothing entered.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(uiController.getPrimaryStage());
            alert.setTitle("Empty Command");
            alert.setHeaderText("No Command Entered");
            alert.setContentText("Please enter a command.");

            alert.showAndWait();
        }
    }
	
	/**
     * Returns the data as an observable list of Tasks. 
     * @return
     */
    public ObservableList<Task> getTaskData() {
        return taskData;
    }
    
    public void setUIController(JJUI uiController){
    	this.uiController = uiController;
    }
	
}
