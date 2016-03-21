package cs2103.v15_1j.jimjim.searcher;

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
        // TODO Auto-generated method stub
        return false;
    }

}
