package cs2103.v15_1j.jimjim.searcher;

/* @@author A0124995R */

import java.util.Arrays;
import java.util.List;

import cs2103.v15_1j.jimjim.model.TaskEvent;

public class KeywordFilter implements Filter {

	List<String> keywords;

	public KeywordFilter(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	@Override
	public boolean check(TaskEvent taskEvent) {
		if (keywords.size() == 1 && keywords.get(0).length() == 1) { // Single
																		// char
			char keyword = keywords.get(0).charAt(0);
			if (Character.toLowerCase(keyword) == Character.toLowerCase(taskEvent.getName().charAt(0))) {
				return true;
			}
		}
		List<String> wordsInName = Arrays.asList(taskEvent.getName().split(" "));
		for (String word : wordsInName) {
			for (String keyword : keywords) {
				if (word.equalsIgnoreCase(keyword)) {
					return true;
				}
			}
		}
		return false;
	}
}
