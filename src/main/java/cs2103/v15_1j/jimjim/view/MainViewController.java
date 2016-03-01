package cs2103.v15_1j.jimjim.view;

import cs2103.v15_1j.jimjim.JJUI;
import cs2103.v15_1j.jimjim.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;

public class MainViewController {

	@FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, Boolean> taskCompletedColumn;
    @FXML
    private TableColumn<Task, String> taskNameColumn;
    @FXML
    private TableColumn<Task, String> taskDateColumn;
    @FXML
    private TextField commandBar;
    
    // Reference to the main application.
    private JJUI mainApp;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MainViewController() {
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param jjui
     */
    public void setMainApp(JJUI jjui) {
        this.mainApp = jjui;

        // Add observable list data to the table
        taskTable.setItems(jjui.getTaskData());
    }
    

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the task table with task name and task completed
        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        taskCompletedColumn.setEditable(true);
        taskCompletedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(taskCompletedColumn));
        taskCompletedColumn.setCellValueFactory(cellData -> cellData.getValue().completedProperty());
        taskDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        
		//table display preference - should not affect this exercise/problem
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Called when the user adds a command in the commandBar
     * TO-DO: Rename to handleCommands
     */
    @FXML
    private void handleCommand() {
    	if (commandBar.getText() != null) {
    		commandBar.setPromptText(mainApp.executeCommand(commandBar.getText()));
            commandBar.setText("");

            
        } else {
            // Nothing entered.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Empty Command");
            alert.setHeaderText("No Command Entered");
            alert.setContentText("Please enter a command.");

            alert.showAndWait();
        }
    }

	
}
