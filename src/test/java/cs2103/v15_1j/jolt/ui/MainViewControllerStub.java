package cs2103.v15_1j.jolt.ui;

import java.util.ArrayList;
import java.util.Map;

import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.ui.MainViewController;

/* @@author A0139963N */
public class MainViewControllerStub extends MainViewController {

	boolean showSearch;
	boolean showOverdue;
	boolean showCompleted;
	String notification;
	String page;
	ArrayList<String> aliasListString;
	
	TodayPaneController tpCon;
	
	public MainViewControllerStub(){
		super(null, null, null, null);
		aliasListString = new ArrayList<String>();
	}
	
	public void setTodayPaneController(TodayPaneController tpCon){
		this.tpCon = tpCon;
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
	
	public void setShowCompleted(boolean showCompleted){
		this.showCompleted = showCompleted;
	}
	
	public boolean getShowCompleted(){
		return showCompleted;
	}
	
	public void setShowOverdue(boolean showOverdue){
		this.showOverdue = showOverdue;
	}
	
	public boolean getShowOverdue(){
		return showOverdue;
	}
	
	public void refreshData(){
		tpCon.refreshData();
	}
	
	public void focusCommandBar(){
		//Stub
	}
}
