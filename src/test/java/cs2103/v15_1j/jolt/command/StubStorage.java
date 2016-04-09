package cs2103.v15_1j.jolt.command;

import cs2103.v15_1j.jolt.controller.Configuration;
import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.storage.Storage;

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


    /* @@author A0124995R */
    @Override
    public boolean saveConfig(Configuration config) {
    	return !willCauseError;
    }
    /* @@author */


    @Override
    public void setConfigFile(String configFileName) {
        // TODO Auto-generated method stub
        
    }

    
}