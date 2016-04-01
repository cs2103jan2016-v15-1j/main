package cs2103.v15_1j.jimjim.ui;

import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXCheckBox;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

public class SearchPaneController {
	private GridPane searchGridPane;
	private ScrollPane searchScrollPane;

	private MainViewController con;
	private TaskEventRowFactory rowFactory;

	private DataLists masterList;
	private DataLists searchResultsList;
	private DataLists displayList;
	
	private Integer rowNo;

	private final double COLUMN_WIDTH = 500.0;
	private final double NAME_LABEL_WIDTH = 250.0;
	private final double DATE_LABEL_WIDTH = 100.0;
	private final double FLOATING_NAME_LABEL_WIDTH = 350.0;

	public SearchPaneController(MainViewController con, DataLists masterList, DataLists displayLists, DataLists searchResultsList){
		this.con = con;
		this.masterList = masterList;
		this.displayList = displayLists;
		this.searchResultsList = searchResultsList;
		initialize();
	}

	public ScrollPane getSearchPane(){
		return searchScrollPane;
	}

	private void initialize(){
		setUpSearchPane();
		rowFactory.showSearchResults();
	}

	private void setUpSearchPane(){
		searchGridPane = new GridPane();
		searchGridPane.prefWidth(COLUMN_WIDTH);
		searchGridPane.setHgap(10);
		searchGridPane.getStyleClass().add("pane");

		searchScrollPane = new ScrollPane();
		searchScrollPane.setContent(searchGridPane);
		searchScrollPane.getStyleClass().add("scrollpane");
		searchScrollPane.setFocusTraversable(false);
		searchScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		searchScrollPane.getStyleClass().add("pane");
		
		rowFactory = new TaskEventRowFactory(searchResultsList, displayList, searchGridPane, rowNo);
	}

	public void refreshData(){
		rowFactory.showSearchResults();
	}

}
