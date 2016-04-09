package cs2103.v15_1j.jolt.storage;

import cs2103.v15_1j.jolt.controller.Configuration;
import cs2103.v15_1j.jolt.model.DataLists;

public interface Storage {
	public void setSaveFile(String saveFileName);
	public DataLists load();
	public boolean save(DataLists list);
	public void setConfigFile(String configFileName);
	public Configuration loadConfig();
	public boolean saveConfig(Configuration config);
}
