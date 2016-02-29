package cs2103.v15_1j.jimjim;
import java.util.List;

public interface Storage {
	public List<TaskEvent> getAll();
	public boolean create(TaskEvent taskEvent);
	public TaskEvent read(int id);
	public boolean update(int id, TaskEvent taskEvent);
	public boolean delete(int id);
}
