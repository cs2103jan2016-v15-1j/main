package cs2103.v15_1j.jimjim.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import cs2103.v15_1j.jimjim.controller.Configuration;
import cs2103.v15_1j.jimjim.model.DataLists;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.lang.reflect.Type;
import java.nio.file.Files;

public class JJStorage implements Storage {
    private File saveFile;
    private Type dataListsType;
    private GsonBuilder builder;
    private Gson gson;

    public JJStorage() {
        dataListsType = new TypeToken<DataLists>() {
        }.getType();
        builder = new GsonBuilder().setPrettyPrinting();
        builder.registerTypeAdapter(ObjectProperty.class, new PropertyTypeAdapter());
        builder.registerTypeAdapter(StringProperty.class, new PropertyTypeAdapter());
        builder.registerTypeAdapter(IntegerProperty.class, new PropertyTypeAdapter());
        builder.registerTypeAdapter(BooleanProperty.class, new PropertyTypeAdapter());
        gson = builder.create();
    }

    // Sets save file to be used for saving/loading
    public void setSaveFile(String saveFileName) {
        saveFile = new File(saveFileName);
    }

    // Return save file
    public File getSaveFile() {
        return saveFile;
    }

    @Override
    public DataLists load() {
        BufferedReader reader = null;
        try {
            // Reads data from file
            reader = new BufferedReader(new FileReader(saveFile));
            // Converts read data back into Java types
            return gson.fromJson(reader, dataListsType);
        } catch (FileNotFoundException e) {
            return new DataLists();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean save(DataLists list) {
        String dataListsJSON = gson.toJson(list, dataListsType);

        return writeJSONToFile(dataListsJSON, saveFile);
    }

    private boolean writeJSONToFile(String jsonString, File file) {
        try {
            // Create new file if it doesn't exist
            if (Files.notExists(file.toPath())) {
                file.createNewFile();
            }
            // Write the JSON to saved file
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(file));

            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public Configuration loadConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean saveConfig(Configuration config) {
        // TODO Auto-generated method stub
        return false;
    }
}
