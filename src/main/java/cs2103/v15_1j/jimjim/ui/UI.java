package cs2103.v15_1j.jimjim.ui;

import cs2103.v15_1j.jimjim.controller.Controller;
import javafx.stage.Stage;

public interface UI {
	public void setController(Controller controller);
	public void setStage(Stage primaryStage);
}
