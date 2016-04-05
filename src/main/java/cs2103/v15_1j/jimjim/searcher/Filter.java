package cs2103.v15_1j.jimjim.searcher;

/* @@author A0124995R */
import cs2103.v15_1j.jimjim.model.TaskEvent;

public interface Filter {
	public boolean check(TaskEvent taskEvent);
}
