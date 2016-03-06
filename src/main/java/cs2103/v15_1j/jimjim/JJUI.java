package cs2103.v15_1j.jimjim;

import java.io.IOException;
import java.util.List;

import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.view.MainViewController;
import cs2103.v15_1j.jimjim.view.MainViewController;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class JJUI extends Application implements UI {

	private Stage primaryStage;
    
    private Controller con;
    private MainViewController mainViewController;
    
    public JJUI() {
    	con = new JJControllerUI();
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JimJim");
        showMainView();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    /**
     * Shows the main view
     */
    public void showMainView() {
        try {
            // Load Main View.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JJUI.class.getResource("view/MainView.fxml"));
            AnchorPane mainView = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            Scene scene = new Scene(mainView);
            primaryStage.setScene(scene);
            
            // Give the controller access to the main app.
            mainViewController = loader.getController();
            mainViewController.setUIController(this);
            refreshUI();
            //controller.setMainApp(this);
            //controller.focusCommandBar();
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    private void refreshUI(){
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
