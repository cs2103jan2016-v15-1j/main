package cs2103.v15_1j.jimjim;
import java.util.ArrayList;
import java.util.List;
import cs2103.v15_1j.jimjim.model.TaskEvent;

public interface Searcher {
	public DataLists search(ArrayList<Filter> filters, DataLists masterLists);
}
