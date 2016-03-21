package cs2103.v15_1j.jimjim.searcher;

import java.util.ArrayList;
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
    	List<String> keywordsChecklist= new ArrayList<String>();
    	keywordsChecklist.addAll(keywords);
    	
    	List<String> wordsInName = Arrays.asList(taskEvent.getName().split(" "));
    	for (String keyword : keywordsChecklist) {
    		if (!wordsInName.contains(keyword)) {
    			return false;
    		}
    	}
    	return true;
    	
    }

}
