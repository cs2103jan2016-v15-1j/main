package cs2103.v15_1j.jimjim;

import cs2103.v15_1j.jimjim.view.TaskViewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class JJUI extends Application implements UI {

    private Controller con;
    private Stage primaryStage;
    private TaskViewController taskViewController;
    
    private final String APPLICATION_NAME = "JimJim";
    
    public JJUI() {
    	con = new JJControllerUI();
    	taskViewController = new TaskViewController();
    	taskViewController.setUIController(this);
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
    	AnchorPane taskView = taskViewController.initialize();
    	Scene scene = new Scene(taskView);
    	primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        refreshUI();
        taskViewController.focusCommandBar();
        
        primaryStage.show();
    }
    
    public void refreshUI(){
    	taskViewController.refreshUI(con.getDisplayList());
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
