package cs2103.v15_1j.jimjim.searcher;

import java.time.LocalTime;

import cs2103.v15_1j.jimjim.model.TaskEvent;

public class TimeFilter implements Filter {
    
    LocalTime start;
    LocalTime end;
    
    public TimeFilter(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }
    
    public LocalTime getStart() {
        return start;
    }
    
    public LocalTime getEnd() {
        return end;
    }

    @Override
    public boolean check(TaskEvent taskEvent) {
        // TODO Auto-generated method stub
        return false;
    }

}
