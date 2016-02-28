package cs2103.v15_1j.jimjim;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

class JJStorage implements Storage {
	private static class JJStorageHelper {
	    public static void write(List<TaskEvent> list, String filename) throws FileNotFoundException {
	        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
	        encoder.writeObject(list);
	        encoder.close();
	    }

	    public static List<TaskEvent> read(String filename) throws FileNotFoundException  {
	        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
	        List<TaskEvent> list = (List<TaskEvent>) decoder.readObject();
	        decoder.close();
	        return list;
	    }	
	}
	
	private String fileName;
	private List<TaskEvent> list;
	
	public JJStorage() throws FileNotFoundException  {
		fileName = "data.xml";
		list = JJStorageHelper.read(fileName); 
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
		try {
			list.add(taskEvent);
			JJStorageHelper.write(list, fileName);
		} catch (Exception e) {
			return false;
		}
		return true;
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
