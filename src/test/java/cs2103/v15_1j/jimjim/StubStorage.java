package cs2103.v15_1j.jimjim;

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
    public DataLists load() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean save(DataLists list) {
        return !willCauseError;
    }

    
}