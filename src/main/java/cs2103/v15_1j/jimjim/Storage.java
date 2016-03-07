package cs2103.v15_1j.jimjim;
import java.io.IOException;
import java.util.List;
import cs2103.v15_1j.jimjim.model.TaskEvent;

public interface Storage {
	public void setSaveFiles(String savedTasksFileName, String savedEventsFileName);
	public List<TaskEvent> load();
	public boolean save(List<TaskEvent> list);
}
