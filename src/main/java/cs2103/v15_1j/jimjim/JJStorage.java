package cs2103.v15_1j.jimjim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
	private List<TaskEvent> list;
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
	public List<TaskEvent> load() throws IOException {
		// Return list if it is already initialized
		if (list != null) {
			return list;
		} else {
			// Else load from disk
			return loadFromDisk();
		}
	}
	
	// Make public for testing
	public List<TaskEvent> loadFromDisk() throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(saveFile));
		
		return gson.fromJson(bufferedReader, listOfTaskEventType);
	}

	@Override
	public boolean save(List<TaskEvent> list) {
		// Update cached version of list
		this.list = list;
		
		// Convert passed-in list to JSON
		String json = gson.toJson(list, listOfTaskEventType);
		try {
			// Create new file if it doesn't exist
			if (Files.notExists(saveFile.toPath())) {
				saveFile.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
			writer.write(json);
			writer.close();	
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
}
