package cs2103.v15_1j.jimjim.ui;

import cs2103.v15_1j.jimjim.controller.Controller;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class JJUI implements UI {

	private Controller con;
	private Stage primaryStage;
	private MainViewController mainViewController;

	private final String APPLICATION_NAME = "JimJim";

	public JJUI(Controller con){
		this.con = con;
		mainViewController = new MainViewController(this, getDataLists(), getDisplayList(), getSearchResults());
	}

	public void setStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(APPLICATION_NAME);
		showTaskView();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void showTaskView() {
		BorderPane mainView = mainViewController.initialize();
		Scene scene = new Scene(mainView);
		scene.getStylesheets().add("css/ui.css");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		mainViewController.focusCommandBar();

		refreshUI();

		primaryStage.show();
	}

	public void refreshUI(){
		mainViewController.updateData();
	}

	public void refreshUI(UIFeedback feedback){
		mainViewController.updateData();
		feedback.execute(mainViewController);
	}

	private DataLists getDataLists(){
		DataLists tempList = con.getMasterList();
		assert (tempList) != null;

		return tempList;
	}
	
	private DataLists getDisplayList(){
		DataLists tempList = con.getDisplayList();
		assert (tempList) != null;

		return tempList;
	}

	public DataLists getSearchResults(){
		DataLists tempList = con.getSearchResultsList();
		assert (tempList) != null;

		return tempList;
	}

	public void executeCommand(String userCommand){
		UIFeedback temp =  con.execute(userCommand);
		assert (temp) != null;

		refreshUI(temp);
	}

	public void setController(Controller con){
		this.con = con;
	}
}
