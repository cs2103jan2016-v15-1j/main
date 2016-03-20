package cs2103.v15_1j.jimjim.ui;

import java.util.List;

import cs2103.v15_1j.jimjim.controller.Controller;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class JJUI implements UI {

	private Controller con;
	private Stage primaryStage;
	private MainViewController mainViewController;

	private final String APPLICATION_NAME = "JimJim";

	public JJUI() {

	}

	public JJUI(Controller con){
		this.con = con;
		mainViewController = new MainViewController(this, getDataLists());
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
		AnchorPane taskView = mainViewController.initialize();
		Scene scene = new Scene(taskView);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		refreshUI();
		mainViewController.focusCommandBar();

		primaryStage.show();
	}

	public void refreshUI(){
		mainViewController.updateData(getDataLists());
	}

	private DataLists getDataLists(){
		DataLists tempList = con.getDisplayList();
		assert (tempList) != null;

		return tempList;
	}

	public String executeCommand(String userCommand){
		String temp =  con.execute(userCommand);
		assert (temp) != null;
		refreshUI();
		return temp;
	}

	public void setController(Controller con){
		this.con = con;
	}
}
