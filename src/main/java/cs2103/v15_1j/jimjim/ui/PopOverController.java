package cs2103.v15_1j.jimjim.ui;

import org.controlsfx.control.PopOver;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PopOverController {

	private GridPane pane;
	private PopOver popOver;

	private int rowNo;
	private int noOfColumns;

	private final double SMALL_BORDER_WIDTH = 7.0;

	// @@author A0139963N
	/**
	 * Constructor
	 * 
	 * @param title Title of the PopOver
	 * @param noOfColumns Number of Columns in the PopOver
	 */
	public PopOverController(String title, int noOfColumns) {
		this.rowNo = 0;
		this.noOfColumns = noOfColumns;

		initialize(title);
	}

	/**
	 * Retrieves the PopOver
	 * 
	 * @return PopOver
	 */
	public PopOver getPopOver() {
		return popOver;
	}

	/**
	 * Initializes the PopOver Components
	 * 
	 * @param title Title of the PopOver
	 */
	private void initialize(String title) {
		initializePane();
		initializePopOver(title);
	}

	private void initializePane() {
		this.pane = new GridPane();
		pane.setHgap(10);
		BorderPane.setMargin(pane,
				new Insets(SMALL_BORDER_WIDTH, SMALL_BORDER_WIDTH, SMALL_BORDER_WIDTH, SMALL_BORDER_WIDTH));
	}

	private void initializePopOver(String title) {
		BorderPane paneWrapper = new BorderPane();
		paneWrapper.setCenter(pane);

		this.popOver = new PopOver(paneWrapper);
		popOver.setTitle(title);
		popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_RIGHT);
		popOver.getStyleClass().add("popover");
	}

	/**
	 * Clear the elements in the PopOver
	 */
	public void clear() {
		pane.getChildren().clear();
		rowNo = 0;
	}

	/**
	 * Add a Header into the PopOver
	 * 
	 * @param message Header Text
	 */
	public void addHeader(String message) {
		Label headerLabel = new Label(message);
		pane.add(headerLabel, 0, ++rowNo, noOfColumns, 1);
	}

	/**
	 * Add a Message into the PopOver
	 * 
	 * @param message Message Text
	 * @param column Column to Add
	 */
	public void addMessage(String message, int column) {
		if(!message.equals("")){
			Circle dot = new Circle(3.0, Color.BLUE);
			GridPane.setHalignment(dot, HPos.CENTER);
			pane.addColumn((column * 2) + 0, dot);

			Label helpLabel = new Label(message);
			pane.addColumn((column * 2) + 1, helpLabel);
		} else {
			Label emptyLabel = new Label(message);
			Label emptyLabel2 = new Label(message);
			pane.addColumn((column * 2) + 0, emptyLabel);
			pane.addColumn((column * 2) + 1, emptyLabel2);
		}
	}
	
	/**
	 * Add a Row Spanning Message into the PopOver
	 * 
	 * @param message Message Text
	 * @param column Column to Add
	 */
	public void addEmptyDivider(int rowSpan) {
		int column = 0;
		rowSpan *= rowSpan;
		while(column < rowSpan){
			Label emptyLabel = new Label("");
			pane.addColumn(column++, emptyLabel);
		}
	}
}
