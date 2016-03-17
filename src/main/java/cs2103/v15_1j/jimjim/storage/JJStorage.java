package cs2103.v15_1j.jimjim.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import cs2103.v15_1j.jimjim.DataLists;
import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.lang.reflect.Type;
import java.nio.file.Files;

public class JJStorage implements Storage {
	private File savedTasksFile;
	private File savedEventsFile;
	private Type listOfTaskType;
	private Type listOfEventType;
	private GsonBuilder builder;
	private Gson gson;

	public JJStorage() {
		listOfTaskType = new TypeToken<List<Task>>(){}.getType();
		listOfEventType = new TypeToken<List<Event>>(){}.getType();
		builder = new GsonBuilder();
		builder.registerTypeAdapter(ObjectProperty.class, new PropertyTypeAdapter());
		builder.registerTypeAdapter(StringProperty.class, new PropertyTypeAdapter());
		builder.registerTypeAdapter(IntegerProperty.class, new PropertyTypeAdapter());
		builder.registerTypeAdapter(BooleanProperty.class, new PropertyTypeAdapter());
		gson = builder.create();
	}

	// Sets save file to be used for saving/loading
	public void setSaveFiles(String savedTasksFileName, String savedEventsFileName) {
		savedTasksFile = new File(savedTasksFileName);
		savedEventsFile = new File(savedEventsFileName);
	}

	// Return save file
	public File getSavedTasksFile() {
		return savedTasksFile;
	}

	public File getSavedEventsFile() {
		return savedEventsFile;
	}

	@Override
	public DataLists load() {
		BufferedReader tasksBufferedReader;
		BufferedReader eventsBufferedReader;
		try {
			// Reads data from file
			tasksBufferedReader = new BufferedReader(new FileReader(savedTasksFile));
			eventsBufferedReader = new BufferedReader(new FileReader(savedEventsFile));
		} catch (FileNotFoundException e) {
			return null;
		}
		// Converts read data back into according
		List<TaskEvent> taskEventList = new ArrayList<TaskEvent>();
		List<Task> tasksList = gson.fromJson(tasksBufferedReader, listOfTaskType);
		List<Event> eventsList = gson.fromJson(eventsBufferedReader, listOfEventType);

		taskEventList.addAll(tasksList);
		taskEventList.addAll(eventsList);

		return taskEventList;
	}

	@Override
	public boolean save(DataLists list) {
		List<Task> tasksList = new ArrayList<Task>();
		List<Event> eventsList = new ArrayList<Event>();

		// Split List<TaskEvent> into separate lists
		for (int i=0; i<list.size(); i++) {
			TaskEvent item = list.get(i);
			if (item instanceof Task) {
				tasksList.add((Task) item);
			} else if (item instanceof Event) {
				eventsList.add((Event) item);
			} else {
				// Fail if object is not of either Task or Event type
				return false;
			}
		}
		// Convert each list to separate JSON string
		String tasksJSON = gson.toJson(tasksList, listOfTaskType);
		String eventsJSON = gson.toJson(eventsList, listOfEventType);

		return writeJSONToFile(tasksJSON, eventsJSON);
	}

	private boolean writeJSONToFile(String tasksJSON, String eventsJSON) {
		try {
			// Create new file if it doesn't exist
			if (Files.notExists(savedTasksFile.toPath())) {
				savedTasksFile.createNewFile();
			}
			if (Files.notExists(savedEventsFile.toPath())) {
				savedEventsFile.createNewFile();
			}
			// Write the JSON to saved file
			BufferedWriter tasksWriter = new BufferedWriter(new FileWriter(savedTasksFile));
			BufferedWriter eventsWriter = new BufferedWriter(new FileWriter(savedEventsFile));

			tasksWriter.write(tasksJSON);
			tasksWriter.close();	

			eventsWriter.write(eventsJSON);
			eventsWriter.close();
		} catch (IOException e) {
			return false;
		}

		return true;
	}
}
