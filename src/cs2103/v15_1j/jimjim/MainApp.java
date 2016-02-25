package cs2103.v15_1j.jimjim;

import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskWrapper;
import cs2103.v15_1j.jimjim.view.MainViewController;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainApp extends Application  {

	private Stage primaryStage;
	private File saveFile;
    
    /**
     * The data as an observable list of Tasks.
     */
    private ObservableList<Task> taskData;

    public MainApp() throws IOException{
    	
    	// Initializing the List with Callbacks to act as listeners when Completed is changed
    	this.taskData = FXCollections.observableArrayList(new Callback<Task, Observable[]>() {

    	    @Override
    	    public Observable[] call(Task param) {
    	        return new Observable[] {param.completedProperty()};
    	    }
    	});
    	
    	saveFile = new File("resources/taskData.xml");
    	
    }
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JimJim");
        

		loadPersonDataFromFile(saveFile);
        showMainView();
        
        
        taskData.addListener(new ListChangeListener<Task>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends Task> t) {
                while (t.next()) {
                    if (t.wasUpdated()) {
                    	savePersonDataToFile();
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
     * Adds a task to the taskData
     * @param task to be added
     */
    public void addTask(Task task){
    	taskData.add(task);
    	savePersonDataToFile();
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
    
    /**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public void loadPersonDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(TaskWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        TaskWrapper wrapper = (TaskWrapper) um.unmarshal(saveFile);

	        taskData.clear();
	        //System.out.println("1"+wrapper.getTasks().get(0).getTaskName());
	        taskData.addAll(wrapper.getTasks());

	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data "+e.getMessage());
	        e.printStackTrace();
	        alert.setContentText("Could not load data from file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void savePersonDataToFile() {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(TaskWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        TaskWrapper wrapper = new TaskWrapper();
	        wrapper.setPersons(taskData);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, saveFile);
	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + saveFile.getPath());

	        alert.showAndWait();
	    }
	}
	
	
}
