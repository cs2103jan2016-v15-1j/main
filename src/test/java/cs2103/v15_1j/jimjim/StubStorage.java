package cs2103.v15_1j.jimjim;

import cs2103.v15_1j.jimjim.controller.Configuration;
import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.storage.Storage;

public class StubStorage implements Storage {
    
    private boolean willCauseError = false;
    
    public void setStorageError() {
        this.willCauseError = true;
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


    @Override
    public void setSaveFile(String saveFileName) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public Configuration loadConfig() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean saveConfig(Configuration config) {
        // TODO Auto-generated method stub
        return false;
    }

    
}