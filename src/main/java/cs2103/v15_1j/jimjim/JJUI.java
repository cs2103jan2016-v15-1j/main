package cs2103.v15_1j.jimjim;

import cs2103.v15_1j.jimjim.view.MainViewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class JJUI extends Application implements UI {

	private Controller con;
	private Stage primaryStage;
	private MainViewController mainViewController;

	private final String APPLICATION_NAME = "JimJim";

	public JJUI() {
		con = new JJController();
		Storage storage = new JJStorage();
		storage.setSaveFiles("tasks.json", "events.json");
		Parser parser = new JJParser();
		con.setParser(parser);
		con.setStorage(storage);
		mainViewController = new MainViewController();
		mainViewController.setUIController(this);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		setStage(primaryStage);
		showTaskView();
	}

	private void setStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(APPLICATION_NAME);
	}

	public static void main(String[] args) {
		launch(args);
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
		mainViewController.refreshUI(con.getDisplayList());
	}

	public String executeCommand(String userCommand){
		String temp =  con.execute(userCommand);
		refreshUI();
		return temp;
	}

	@Override
	public void setController(Controller con){
		this.con = con;
	}
}
