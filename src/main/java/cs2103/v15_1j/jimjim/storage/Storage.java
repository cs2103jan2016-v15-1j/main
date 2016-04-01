package cs2103.v15_1j.jimjim.storage;

import cs2103.v15_1j.jimjim.controller.Configuration;
import cs2103.v15_1j.jimjim.model.DataLists;

public interface Storage {
	public void setSaveFile(String saveFileName);
	public DataLists load();
	public boolean save(DataLists list);
	public Configuration loadConfig();
	public boolean saveConfig(Configuration config);
}
