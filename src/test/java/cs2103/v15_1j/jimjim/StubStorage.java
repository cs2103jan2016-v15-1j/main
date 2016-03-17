package cs2103.v15_1j.jimjim;

import java.util.List;
import cs2103.v15_1j.jimjim.model.TaskEvent;
import cs2103.v15_1j.jimjim.storage.Storage;

public class StubStorage implements Storage {
    
    private boolean willCauseError = false;
    
    public void setStorageError() {
        this.willCauseError = true;
    }

    @Override
    public void setSaveFiles(String savedTasksFileName, String savedEventsFileName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<TaskEvent> load() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean save(List<TaskEvent> list) {
        return !willCauseError;
    }

    
}