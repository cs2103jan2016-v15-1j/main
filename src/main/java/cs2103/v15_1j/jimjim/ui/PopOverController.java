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

	//@@author A0139963N
	public PopOverController(String title, int noOfColumns){
		this.rowNo = 0;
		this.noOfColumns = noOfColumns;

		this.pane = new GridPane();
		pane.setHgap(10);
		BorderPane.setMargin(pane, new Insets(SMALL_BORDER_WIDTH, SMALL_BORDER_WIDTH, SMALL_BORDER_WIDTH, 
				SMALL_BORDER_WIDTH));

		BorderPane paneWrapper = new BorderPane();
		paneWrapper.setCenter(pane);

		this.popOver = new PopOver(paneWrapper);
		popOver.setTitle(title);
		popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_RIGHT);
		popOver.getStyleClass().add("popover");
	}

	public PopOver getPopOver(){
		return popOver;
	}

	public void clear(){
		pane.getChildren().clear();
		rowNo = 0;
	}

	public void addHeader(String message){
		Label headerLabel = new Label(message);
		pane.add(headerLabel, 0, ++rowNo, noOfColumns, 1);
	}

	public void addMessage(String message, int column){
		Circle dot = new Circle(3.0, Color.BLUE);
		GridPane.setHalignment(dot, HPos.CENTER);
		pane.addColumn((column*2)+0, dot);

		Label helpLabel = new Label(message);
		pane.addColumn((column*2)+1, helpLabel);
	}
}
