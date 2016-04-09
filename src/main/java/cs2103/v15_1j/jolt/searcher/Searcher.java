package cs2103.v15_1j.jolt.searcher;

import java.util.List;

import cs2103.v15_1j.jolt.model.DataLists;

public interface Searcher {
	public DataLists search(List<Filter> filters, DataLists masterLists);
}
