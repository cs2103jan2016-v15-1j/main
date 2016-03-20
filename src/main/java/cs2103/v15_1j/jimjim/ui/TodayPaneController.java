package cs2103.v15_1j.jimjim.ui;

import cs2103.v15_1j.jimjim.model.DataLists;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class TodayPaneController {
	private GridPane todayGridPane;
	private ScrollPane todayScrollPane;

	private MainViewController con;

	private DataLists lists;

	private final double COLUMN_WIDTH = 300.0;

	public TodayPaneController(MainViewController con, DataLists lists){
		this.con = con;
		this.lists = lists;
		initialize();
	}

	public ScrollPane getTodayPane(){
		return todayScrollPane;
	}

	private void initialize(){
		setUpTodayPane();
		getTasks();
	}

	private void setUpTodayPane(){
		todayGridPane = new GridPane();
		todayGridPane.prefWidth(COLUMN_WIDTH);

		todayScrollPane = new ScrollPane();
		todayScrollPane.setContent(todayGridPane);
		todayScrollPane.setFocusTraversable(false);
		todayScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	}

	public void refreshData(DataLists lists){
		this.lists = lists;
		getTasks();
	}

	private void getTasks(){
		todayGridPane.getChildren().clear();
		Label taskLabel = new Label("Feature to be Added.");

		todayGridPane.addColumn(0, taskLabel);
	}
}
