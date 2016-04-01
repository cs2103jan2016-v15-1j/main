package cs2103.v15_1j.jimjim;

import cs2103.v15_1j.jimjim.controller.Controller;
import cs2103.v15_1j.jimjim.controller.JJController;
import cs2103.v15_1j.jimjim.parser.JJParser;
import cs2103.v15_1j.jimjim.parser.Parser;
import cs2103.v15_1j.jimjim.searcher.JJSearcher;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.JJStorage;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.ui.JJUI;
import cs2103.v15_1j.jimjim.ui.UI;
import javafx.application.Application;
import javafx.stage.Stage;

public class JJMain extends Application {

	private Controller con;
	private UI ui;
	private final String CONFIG_FILE_NAME = "config.json";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		con = new JJController();
		Storage storage = new JJStorage();
		Parser parser = new JJParser();
		Searcher searcher = new JJSearcher();

		storage.setConfigFile(CONFIG_FILE_NAME);
		con.setParser(parser);
		con.setStorage(storage);
		con.setSearcher(searcher);
		con.init();

		ui = new JJUI(con);
		ui.setStage(primaryStage);
	}
}
