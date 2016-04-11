package cs2103.v15_1j.jolt;

import cs2103.v15_1j.jolt.controller.Controller;
import cs2103.v15_1j.jolt.controller.JoltController;
import cs2103.v15_1j.jolt.parser.JoltParser;
import cs2103.v15_1j.jolt.parser.Parser;
import cs2103.v15_1j.jolt.searcher.JoltSearcher;
import cs2103.v15_1j.jolt.searcher.Searcher;
import cs2103.v15_1j.jolt.storage.JoltStorage;
import cs2103.v15_1j.jolt.storage.Storage;
import cs2103.v15_1j.jolt.ui.JoltUI;
import cs2103.v15_1j.jolt.ui.UI;
import javafx.application.Application;
import javafx.stage.Stage;

public class JoltMain extends Application {

	private Controller con;
	private UI ui;
	private final String CONFIG_FILE_NAME = "config.json";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		con = new JoltController();
		Storage storage = new JoltStorage();
		Parser parser = new JoltParser();
		Searcher searcher = new JoltSearcher();

		storage.setConfigFile(CONFIG_FILE_NAME);
		con.setParser(parser);
		con.setStorage(storage);
		con.setSearcher(searcher);
		con.init();

		ui = new JoltUI();
		ui.setController(con);
		ui.initialize();
		ui.setStage(primaryStage);
	}
}
