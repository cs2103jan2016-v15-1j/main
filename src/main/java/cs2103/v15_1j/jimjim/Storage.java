package cs2103.v15_1j.jimjim;
import java.io.IOException;
import java.util.List;

public interface Storage {
	public List<TaskEvent> load() throws IOException;
	public boolean save(List<TaskEvent> list);
}
