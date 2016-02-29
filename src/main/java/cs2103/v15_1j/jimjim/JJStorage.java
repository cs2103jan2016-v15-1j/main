package cs2103.v15_1j.jimjim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

class JJStorage implements Storage {
//	private static class JJStorageHelper {
//	    public static void write(List<TaskEvent> list, String filename) throws FileNotFoundException {
//	        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
//	        encoder.writeObject(list);
//	        encoder.close();
//	    }
//
//	    public static List<TaskEvent> read(String filename) throws FileNotFoundException  {
//	        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
//	        List<TaskEvent> list = (List<TaskEvent>) decoder.readObject();
//	        decoder.close();
//	        return list;
//	    }	
//	}
	
	private String fileName;
	private File saveFile;
	private List<TaskEvent> list;
	
	private void initializeList() {
		try {
//			JAXBContext context = new JAXBContext.newInstance(TaskEventWrapper.class);
	        JAXBContext context = JAXBContext.newInstance(TaskEventWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();
	        
	        // Reading XML from the file and unmarshalling.
	        TaskEventWrapper wrapper = (TaskEventWrapper) um.unmarshal(saveFile);
	        list.clear();
	        list.addAll(wrapper.getTaskEvents());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JJStorage() throws FileNotFoundException  {
		fileName = "data.xml";
		saveFile = new File(fileName);
		initializeList();
//		try {
//			list = JJStorageHelper.read(fileName);
//		} catch (FileNotFoundException e) {
//			list = new ArrayList<TaskEvent>();
//			JJStorageHelper.write(list, fileName);
//		}
	}
	
	
	public JJStorage(String fileName) throws FileNotFoundException {
		this.fileName = fileName;
		saveFile = new File(fileName);
		initializeList();
//		try {
//			list = JJStorageHelper.read(fileName);
//		} catch (FileNotFoundException e) {
//			list = new ArrayList<TaskEvent>();
//			JJStorageHelper.write(list, fileName);
//		}
	}
	
//	private void retrieveList() throws IOException {
//		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
//		String currentLine = null;
//		
//		while ((currentLine = bufferedReader.readLine()) != null) {
//			list.add(convertStringToTaskEvent(currentLine));
//		}
//		
//		bufferedReader.close();
//	}

	@Override
	public List<TaskEvent> getAll() {
		return list;
	}

	@Override
	public boolean create(TaskEvent taskEvent) {
		list.add(taskEvent);
//			JJStorageHelper.write(list, fileName);
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
