package cs2103.v15_1j.jimjim.controller;

public class Configuration {
    public String savePath;
    
    public Configuration() {
        // default configurations
        savePath = "save_data.json";
    }
    
    @Override
    public boolean equals(Object t) {
    	if (t == null || !(t instanceof Configuration)) {
    		return false;
    	}
    	Configuration other = (Configuration) t;
    	return this.savePath.equals(other.savePath);
    }
}
