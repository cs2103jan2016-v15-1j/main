package cs2103.v15_1j.jimjim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.file.Files;

class JJStorage implements Storage {
	private File saveFile;
	private Type listOfTaskEventType;
	private GsonBuilder builder;
	private Gson gson;
	
	public JJStorage() {
		listOfTaskEventType = new TypeToken<List<TaskEvent>>(){}.getType();
		builder = new GsonBuilder();
		gson = builder.create();
	}
	
	// Sets save file to be used for saving/loading
	public void setSaveFile(String fileName) {
		saveFile = new File(fileName);
	}
	
	// Return save file
	public File getSaveFile() {
		return saveFile;
	}

	@Override
	public List<TaskEvent> load() {
		BufferedReader bufferedReader;
		try {
			// Reads data from file
			bufferedReader = new BufferedReader(new FileReader(saveFile));
		} catch (FileNotFoundException e) {
			return null;
		}
		// Converts read data into JSON, using Type of List<TaskEvent>
		return gson.fromJson(bufferedReader, listOfTaskEventType);
	}

	@Override
	public boolean save(List<TaskEvent> list) {
		// Convert passed-in list to JSON
		String json = gson.toJson(list, listOfTaskEventType);
		try {
			// Create new file if it doesn't exist
			if (Files.notExists(saveFile.toPath())) {
				saveFile.createNewFile();
			}
			// Write JSON to save file
			BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
			writer.write(json);
			writer.close();	
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
}
