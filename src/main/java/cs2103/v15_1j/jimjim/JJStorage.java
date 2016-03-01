package cs2103.v15_1j.jimjim;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

class JJStorage implements Storage {
	private File saveFile;
	private List<TaskEvent> list;
	
	// Sets save file to be used for saving/loading
	public void setSaveFile(String fileName) {
		saveFile = new File(fileName);
	}

	@Override
	public List<TaskEvent> load() throws IOException {
		// Return list if it is already initialized
		if (list != null) {
			return list;
		} else {
			// Else we load it from memory
			try {
		        JAXBContext context = JAXBContext.newInstance(TaskEventWrapper.class);
		        Unmarshaller um = context.createUnmarshaller();
		        
		        // Reading XML from the file and unmarshalling.
		        TaskEventWrapper wrapper = (TaskEventWrapper) um.unmarshal(saveFile);
		        
		        // Initialize the list with the contents of the unmarshalled save file
		        list = new ArrayList<TaskEvent>(wrapper.getTaskEvents());
			} catch (Exception e) {
				// If file does not exist, we create it here
				saveFile.createNewFile();
				list = new ArrayList<TaskEvent>();
			}
			return list;
		}
	}

	@Override
	public boolean save(List<TaskEvent> list) {
		// Update cached version of list
		this.list = list;
		
	    try {
	        JAXBContext context = JAXBContext.newInstance(TaskEventWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        TaskEventWrapper wrapper = new TaskEventWrapper();
	        wrapper.setTaskEvents(list);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, saveFile);
	        
	        return true;
	    } catch (Exception e) {
	    	return false;
	    }
	}
}
