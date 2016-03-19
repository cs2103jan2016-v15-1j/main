package cs2103.v15_1j.jimjim.searcher;

import java.time.LocalDateTime;

import cs2103.v15_1j.jimjim.model.TaskEvent;

public class DateTimeFilter implements Filter {
    
    LocalDateTime start;
    LocalDateTime end;
    
    public DateTimeFilter(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
    
    public LocalDateTime getStart() {
        return start;
    }
    
    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public boolean check(TaskEvent taskEvent) {
        // TODO Auto-generated method stub
        return false;
    }

}
