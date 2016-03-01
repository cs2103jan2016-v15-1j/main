package cs2103.v15_1j.jimjim;
import java.util.List;

public interface Storage {
	public List<TaskEvent> load();
	public void save(List<TaskEvent> list);
}
