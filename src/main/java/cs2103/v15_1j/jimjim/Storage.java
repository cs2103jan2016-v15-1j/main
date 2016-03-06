package cs2103.v15_1j.jimjim;
import java.io.IOException;
import java.util.List;

public interface Storage {
	public void setSaveFile(String fileName);
	public List<TaskEvent> load();
	public boolean save(List<TaskEvent> list);
}
