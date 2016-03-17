package cs2103.v15_1j.jimjim.searcher;
import java.util.List;

import cs2103.v15_1j.jimjim.DataLists;
import cs2103.v15_1j.jimjim.Filter;

public interface Searcher {
	public DataLists search(List<Filter> filters, DataLists masterLists);
}
