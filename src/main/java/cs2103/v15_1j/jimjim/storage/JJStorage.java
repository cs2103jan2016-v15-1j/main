package cs2103.v15_1j.jimjim.storage;

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

import cs2103.v15_1j.jimjim.model.Event;
import cs2103.v15_1j.jimjim.model.FloatingTask;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.model.DeadlineTask;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.lang.reflect.Type;
import java.nio.file.Files;

public class JJStorage implements Storage {
    private File savedFloatingTasksFile;
    private File savedDeadlineTasksFile;
    private File savedEventsFile;
    private Type listOfFloatingTaskType;
    private Type listOfEventType;
    private Type listOfDeadlineTaskType;
    private GsonBuilder builder;
    private Gson gson;

    public JJStorage() {
        listOfFloatingTaskType = new TypeToken<List<FloatingTask>>() {
        }.getType();
        listOfDeadlineTaskType = new TypeToken<List<DeadlineTask>>() {
        }.getType();
        listOfEventType = new TypeToken<List<Event>>() {
        }.getType();
        builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectProperty.class, new PropertyTypeAdapter());
        builder.registerTypeAdapter(StringProperty.class, new PropertyTypeAdapter());
        builder.registerTypeAdapter(IntegerProperty.class, new PropertyTypeAdapter());
        builder.registerTypeAdapter(BooleanProperty.class, new PropertyTypeAdapter());
        gson = builder.create();
    }

    // Sets save file to be used for saving/loading
    public void setSaveFiles(String savedTasksFileName, String savedDeadlineTasksFileName, String savedEventsFileName) {
        savedFloatingTasksFile = new File(savedTasksFileName);
        savedDeadlineTasksFile = new File(savedDeadlineTasksFileName);
        savedEventsFile = new File(savedEventsFileName);
    }

    // Return save file
    public File getSavedFloatingTasksFile() {
        return savedFloatingTasksFile;
    }

    public File getSavedDeadlineTasksFile() {
        return savedDeadlineTasksFile;
    }

    public File getSavedEventsFile() {
        return savedEventsFile;
    }

    @Override
    public DataLists load() {
        BufferedReader tasksBufferedReader;
        BufferedReader deadlineTasksBufferedReader;
        BufferedReader eventsBufferedReader;
        try {
            // Reads data from file
            tasksBufferedReader = new BufferedReader(new FileReader(savedFloatingTasksFile));
            deadlineTasksBufferedReader = new BufferedReader(new FileReader(savedDeadlineTasksFile));
            eventsBufferedReader = new BufferedReader(new FileReader(savedEventsFile));
        } catch (FileNotFoundException e) {
            return new DataLists();
        }
        // Converts read data back into Java types
        List<FloatingTask> floatingTasksList = gson.fromJson(tasksBufferedReader, listOfFloatingTaskType);
        List<DeadlineTask> deadlineTasksList = gson.fromJson(deadlineTasksBufferedReader, listOfDeadlineTaskType);
        List<Event> eventsList = gson.fromJson(eventsBufferedReader, listOfEventType);

        try {
            tasksBufferedReader.close();
            deadlineTasksBufferedReader.close();
            eventsBufferedReader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new DataLists(deadlineTasksList, floatingTasksList, eventsList);
    }

    @Override
    public boolean save(DataLists list) {
        List<FloatingTask> tasksList = list.getFloatingTasksList();
        List<DeadlineTask> deadlineTasksList = list.getDeadlineTasksList();
        List<Event> eventsList = list.getEventsList();

        // Convert each list to separate JSON string
        String tasksJSON = gson.toJson(tasksList, listOfFloatingTaskType);
        String deadlineTasksJSON = gson.toJson(deadlineTasksList, listOfDeadlineTaskType);
        String eventsJSON = gson.toJson(eventsList, listOfEventType);

        return writeJSONToFile(tasksJSON, deadlineTasksJSON, eventsJSON);
    }

    private boolean writeJSONToFile(String tasksJSON, String deadlineTasksJSON, String eventsJSON) {
        try {
            // Create new file if it doesn't exist
            if (Files.notExists(savedFloatingTasksFile.toPath())) {
                savedFloatingTasksFile.createNewFile();
            }
            if (Files.notExists(savedDeadlineTasksFile.toPath())) {
                savedDeadlineTasksFile.createNewFile();
            }
            if (Files.notExists(savedEventsFile.toPath())) {
                savedEventsFile.createNewFile();
            }
            // Write the JSON to saved file
            BufferedWriter deadlineTasksWriter = new BufferedWriter(new FileWriter(savedDeadlineTasksFile));
            BufferedWriter tasksWriter = new BufferedWriter(new FileWriter(savedFloatingTasksFile));
            BufferedWriter eventsWriter = new BufferedWriter(new FileWriter(savedEventsFile));

            deadlineTasksWriter.write(deadlineTasksJSON);
            deadlineTasksWriter.close();

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
