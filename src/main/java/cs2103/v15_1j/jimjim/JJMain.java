package cs2103.v15_1j.jimjim;

import javafx.application.Application;
import javafx.stage.Stage;

public class JJMain extends Application {

	private Controller con;
	private Storage storage;
	private Parser parser;
	private UI ui;
	private final String TASK_FILE_NAME = "tasks.json";
	private final String EVENT_FILE_NAME = "events.json";

	public JJMain(){
		con = new JJController();
		storage = new JJStorage();
		parser = new JJParser();
		ui = new JJUI();

		storage.setSaveFiles(TASK_FILE_NAME, EVENT_FILE_NAME);
		con.setParser(parser);
		con.setStorage(storage);
		ui.setController(con);
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ui.setStage(primaryStage);
	}
}
