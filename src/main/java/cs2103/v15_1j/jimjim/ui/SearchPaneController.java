package cs2103.v15_1j.jimjim.ui;

import java.time.LocalDate;

import cs2103.v15_1j.jimjim.model.DataLists;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class SearchPaneController {
	private GridPane searchGridPane;
	private ScrollPane searchScrollPane;

	private MainViewController con;
	private TaskEventRowFactory rowFactory;

	private DataLists searchResultsList;
	private DataLists displayList;

	private final double COLUMN_WIDTH = 500.0;

	//@@author Jeremy
	public SearchPaneController(MainViewController con, DataLists searchResultsList, DataLists displayLists){
		this.con = con;
		this.searchResultsList = searchResultsList;
		this.displayList = displayLists;
		initialize();
	}

	public ScrollPane getSearchPane(){
		return searchScrollPane;
	}

	private void initialize(){
		setUpSearchPane();
		setUpRowFactory();
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
	}

	private void setUpRowFactory(){
		rowFactory = new TaskEventRowFactory(searchResultsList, displayList, searchGridPane);
		refreshData();
	}

	public void refreshData(){
		rowFactory.clear();
		rowFactory.addLabel("Search Results", "header");
		if(!searchResultsList.isEmpty()){
			rowFactory.showAllDeadlineTaskAndEvents(null);
			rowFactory.showAllFloatingTasks(true);
		}
		else {
			rowFactory.addLabel("No Results Found.", "red-label");
		}
	}

}
