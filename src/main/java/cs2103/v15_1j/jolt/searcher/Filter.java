package cs2103.v15_1j.jolt.searcher;

import cs2103.v15_1j.jolt.model.TaskEvent;

public interface Filter {
	public boolean check(TaskEvent taskEvent);
}
