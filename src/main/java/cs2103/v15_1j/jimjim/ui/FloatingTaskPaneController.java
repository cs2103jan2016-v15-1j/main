package cs2103.v15_1j.jimjim.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class FloatingTaskPaneController {

	private GridPane floatingTaskGridPane;
	private ScrollPane floatingTaskScrollPane;

	private MainViewController con;
	private TaskEventRowFactory rowFactory;

	private DataLists masterList;
	private DataLists displayList;

	private final double COLUMN_WIDTH = 500.0;
	private final double TASK_LABEL_WIDTH = 350.0;

	private boolean showCompleted;
	private boolean hasCompleted;

	public FloatingTaskPaneController(MainViewController con, DataLists lists, DataLists displayLists){
		this.con = con;
		this.masterList = lists;
		this.displayList = displayLists;
		showCompleted = false;
		hasCompleted = false;
		initialize();
	}

	public ScrollPane getFloatingTaskPane(){
		return floatingTaskScrollPane;
	}

	private void initialize(){
		setUpFloatingTaskPane();
		//showFloatingTasks();
	}

	private void setUpFloatingTaskPane(){
		floatingTaskGridPane = new GridPane();
		floatingTaskGridPane.prefWidth(COLUMN_WIDTH);
		floatingTaskGridPane.setHgap(10);
		floatingTaskGridPane.getStyleClass().add("pane");

		floatingTaskScrollPane = new ScrollPane();
		floatingTaskScrollPane.setContent(floatingTaskGridPane);
		floatingTaskScrollPane.getStyleClass().add("scrollpane");
		floatingTaskScrollPane.setFocusTraversable(false);
		floatingTaskScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		floatingTaskScrollPane.getStyleClass().add("pane");
		
		rowFactory = new TaskEventRowFactory(masterList, displayList, floatingTaskGridPane, new Integer(0));
	}

	public void refreshData(){
		//showFloatingTasks();
	}

	
}