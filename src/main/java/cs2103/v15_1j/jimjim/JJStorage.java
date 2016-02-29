package cs2103.v15_1j.jimjim;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

class JJStorage implements Storage {
	private String fileName;
	private File saveFile;
	private List<TaskEvent> list;
	
	public JJStorage() throws IOException  {
		fileName = "data.xml";
		saveFile = new File(fileName);
		initializeList();
	}
	
	
	public JJStorage(String fileName) throws IOException {
		this.fileName = fileName;
		saveFile = new File(fileName);
		initializeList();
	}
	
	private void initializeList() throws IOException {
		try {
	        JAXBContext context = JAXBContext.newInstance(TaskEventWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();
	        
	        // Reading XML from the file and unmarshalling.
	        TaskEventWrapper wrapper = (TaskEventWrapper) um.unmarshal(saveFile);
	        list.clear();
	        list.addAll(wrapper.getTaskEvents());
		} catch (Exception e) {
			saveFile.createNewFile();
			list = new ArrayList<TaskEvent>();
		}
	}

	@Override
	public List<TaskEvent> getAll() {
		return list;
	}

	@Override
	public boolean create(TaskEvent taskEvent) {
		list.add(taskEvent);
		return saveListToFile();
	}
	
	private boolean saveListToFile() {
	    try {
	        JAXBContext context = JAXBContext.newInstance(TaskEventWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        TaskEventWrapper wrapper = new TaskEventWrapper();
	        wrapper.setTaskEvents(list);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, saveFile);
	        
	        return true;
	    } catch (Exception e) { // catches ANY exception
	    	return false;
	    }
	}

	@Override
	public TaskEvent read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(int id, TaskEvent taskEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
