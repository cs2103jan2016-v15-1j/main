package cs2103.v15_1j.jolt.uifeedback;

import java.util.ArrayList;
import java.util.Map;

import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.ui.MainViewController;

public class MainViewControllerStub extends MainViewController {

	boolean showSearch;
	String notification;
	String page;
	ArrayList<String> aliasListString;
	
	public MainViewControllerStub(){
		super(null, null, null, null);
		aliasListString = new ArrayList<String>();
	}
	
	public void showNotification(String msg){
		notification = msg;
	}
	
	public String getNotification(){
		return notification;
	}
	
	public boolean addEvent(Event event){
		if(event.getName().equals("Meeting Again")){
			return true;
		} else {
			return false;
		}
	}
	
	public void showAliases(Map<String, String> aliasList){
		for (String key : aliasList.keySet()) {
			aliasListString.add(key + " = " + aliasList.get(key));
		}
	}
	
	public ArrayList<String> getAliasList(){
		return aliasListString;
	}
	
	public void showHelp(String page){
		this.page = page;
	}
	
	public String getPage(){
		return page;
	}
	
	public void hideSearchResults(){
		showSearch = false;
	}
	
	public boolean getShowSearch(){
		return showSearch;
	}
}
