package cs2103.v15_1j.jimjim.storage;

import cs2103.v15_1j.jimjim.DataLists;

public interface Storage {
	public void setSaveFiles(String savedTasksFileName, String savedDeadlineTasksFileName, String savedEventsFileName);
	public DataLists load();
	public boolean save(DataLists list);
}
