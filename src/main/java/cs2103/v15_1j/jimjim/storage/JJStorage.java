package cs2103.v15_1j.jimjim.storage;

/* @@author A0124995R */

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

public class JJStorage implements Storage {
	private File saveFile;
	private File configFile;
	private Type dataListsType;
	private Type configType;
	private GsonBuilder builder;
	private Gson gson;

	public JJStorage() {
		dataListsType = new TypeToken<DataLists>() {}.getType();
		configType = new TypeToken<Configuration>() {}.getType();
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
		} catch (Exception e) {
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
            if (!file.exists()) {
            	File parent = file.getParentFile();
            	if (parent != null && !parent.exists()) {
            		parent.mkdirs();
            	}
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
		BufferedReader reader = null;
		try {
			// Reads data from file
			reader = new BufferedReader(new FileReader(configFile));
			// Converts read data back into Java types
			return gson.fromJson(reader, configType);
		} catch (FileNotFoundException e) {
			Configuration newConfig = new Configuration();
			saveConfig(newConfig); // Generate new config file and save if it
									// doesn't exist
			return newConfig;
		} catch (Exception e) {
			return null;
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
	public boolean saveConfig(Configuration config) {
		String configJSON = gson.toJson(config, configType);

		return writeJSONToFile(configJSON, configFile);
	}

	@Override
	public void setConfigFile(String configFileName) {
		configFile = new File(configFileName);
	}

	public File getConfigFile() {
		return configFile;
	}
}
