package cs2103.v15_1j.jimjim;

import java.io.IOException;
import java.util.List;

import cs2103.v15_1j.jimjim.model.Task;
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
    private ObservableList<Task> taskData;
    private Controller con;
    
    public JJUI() {
    	con = new JJControllerUI();
    	taskData = FXCollections.observableArrayList(new Callback<Task, Observable[]>() {

    	    @Override
    	    public Observable[] call(Task param) {
    	        return new Observable[] {param.completedProperty()};
    	    }
    	});
    	
    	
    	
    	refreshUI();
    }
    
    private void refreshUI(){
    	taskData.clear();
    	List<TaskEvent> tempList = con.getDisplayList();
    	for(TaskEvent te: tempList){
    		taskData.add(new Task(te));
    	}
    }
    
	@Override
	public void setController(Controller controller) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JimJim");
        showMainView();
        
        
        taskData.addListener(new ListChangeListener<Task>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends Task> t) {
                while (t.next()) {
                    if (t.wasUpdated()) {
                    	taskData.remove(t.getFrom());
                    }
                  }
            }
        });
		
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
     * Returns the data as an observable list of Tasks. 
     * @return
     */
    public ObservableList<Task> getTaskData() {
        return taskData;
    }
    
    /**
     * Shows the main view
     */
    public void showMainView() {
        try {
            // Load Main View.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainView.fxml"));
            AnchorPane mainView = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            Scene scene = new Scene(mainView);
            primaryStage.setScene(scene);
            
            // Give the controller access to the main app.
            MainViewController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public String executeCommand(String userCommand){
    	String temp =  con.execute(userCommand);
    	refreshUI();
    	return temp;
    }
}
